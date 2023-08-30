package com.learning_coordinator.matching.config.schedulers;

import com.learning_coordinator.matching.batch.ResourcePartitioner;
import com.learning_coordinator.matching.batch.SharedVectors;
//import com.learning_coordinator.matching.UserServiceFeignClient;
import com.learning_coordinator.matching.domain.MatchingInformation;
import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import com.learning_coordinator.matching.dto.UserServiceIdResponseDTO;
import com.learning_coordinator.matching.dto.UserServiceInformationResponseDTO;
import com.learning_coordinator.matching.repository.Implementations.UserInformationEmbeddingStoreRepositoryImpl_JSON;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Duration;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;

import static com.learning_coordinator.matching.config.schedulers.UpdateUserInformationDecider.NEXT;
import static com.learning_coordinator.matching.repository.Implementations.UserInformationEmbeddingStoreRepositoryImpl_JSON.lastBatchDate;

@Configuration
@EnableScheduling
@ComponentScan(basePackageClasses = SharedVectors.class)
public class BatchScheduler {
    private final Logger logger = LoggerFactory.getLogger(BatchScheduler.class);
    private final int BATCHRATE = 10000;
    private final Map<Object, ScheduledFuture<?>> scheduledTasks = new IdentityHashMap<>();
    private final String MATCHINGTABLENAME = "Matching";
    
    private AtomicBoolean enabled = new AtomicBoolean(true);
    private AtomicInteger batchRunCounter = new AtomicInteger(0);
    private static UserInformationEmbeddingStoreRepositoryImpl_JSON userInformationEmbeddingStoreRepo = new UserInformationEmbeddingStoreRepositoryImpl_JSON();
    // @Autowired
    // public UserServiceFeignClient client;

    @Scheduled(fixedRate = BATCHRATE, initialDelay = BATCHRATE)
    public void launchJob() throws Exception {
        Date date = new Date(System.currentTimeMillis());
        logger.debug("scheduler starts at " + date);
        if (enabled.get()) {
            SharedVectors.init();
            JobExecution jobExecution = getJobLauncher().run(MatchingJob(getJobRepository(), (PlatformTransactionManager) getmatchingTransactionManager()), new JobParametersBuilder().addDate("launchDate", date)
                    .toJobParameters());
            batchRunCounter.incrementAndGet();
            logger.debug("Batch job ends with status as " + jobExecution.getStatus());
            if(jobExecution.getStatus() == BatchStatus.COMPLETED)
                lastBatchDate = date;
        }
        logger.debug("scheduler ends ");
    }

    public void stop() {
        enabled.set(false);
    }

    public void start() {
        enabled.set(true);
    }

    @Bean
    TaskScheduler poolScheduler() {
        return new CustomTaskScheduler();
    }

    private class CustomTaskScheduler extends ThreadPoolTaskScheduler {

        private static final long serialVersionUID = -7142624085505040603L;

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period) {
            ScheduledFuture<?> future = super.scheduleAtFixedRate(task, period);

            ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task;
            scheduledTasks.put(runnable.getTarget(), future);

            return future;
        }
    }

    public void cancelFutureSchedulerTasks() {
        scheduledTasks.forEach((k, v) -> {
            if (k instanceof BatchScheduler) {
                v.cancel(false);
            }
        });
    }

    public AtomicInteger getBatchRunCounter() {
        return batchRunCounter;
    }


    @Bean(name = "MatchingJob")
    Job MatchingJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws org.springframework.batch.core.job.flow.FlowExecutionException {
        Flow matchingflow = new FlowBuilder<SimpleFlow>("matchingflow")
            .start(new UpdateUserInformationDecider())
            .on(NEXT)
            .to(idSlaveStep(jobRepository, transactionManager))
            .next(slaveStep(jobRepository, transactionManager))
            .next(matchingStep(jobRepository, (JdbcTransactionManager) transactionManager))
            .end();      
                
        
        return new JobBuilder("matchingJob", jobRepository)
            .start(matchingflow)
            .end()
            .build();
    }
    

/*
**************************** UserServiceResponse - ids Step ***************************************
 */

    // @Bean
    // Step idPartitionStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws UnexpectedInputException {
    //     return new StepBuilder("IdPartitionStep",jobRepository)
    //         .partitioner("idslaveStep", idPartitioner())
    //         .step(idSlaveStep(jobRepository, transactionManager))
    //         .taskExecutor(taskExecutor())
    //         .build();
    // }

    @Bean
    Step idSlaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws UnexpectedInputException {
        return new StepBuilder("idslaveStep", jobRepository)
            .<UserServiceIdResponseDTO, UserServiceInformationResponseDTO>chunk(1, transactionManager)
            .reader(userIdItemReader(null, null))
            .processor(idtoInfomationProcessor())
            .writer(idtoInfomationItemWriter())
            .faultTolerant()
            .retryLimit(3)
            .retry(JDBCConnectionException.class)
            .retry(UnexpectedInputException.class)
            .noRetry(NullPointerException.class)
            .build();
    }

    @Bean
    ResourcePartitioner idPartitioner() {
        ResourcePartitioner partitioner = new ResourcePartitioner(SharedVectors.updateIdVector.size());
        return partitioner;
    }

    @Bean
    @org.springframework.batch.core.configuration.annotation.StepScope
    ListItemReader<UserServiceIdResponseDTO> userIdItemReader(@Value("#{stepExecutionContext['startValue']}") Long startValue, @Value("#{stepExecutionContext['endValue']}") Long endValue) {
        ListItemReader<UserServiceIdResponseDTO> reader = new ListItemReader<>(SharedVectors.updateIdVector.subList(0, SharedVectors.updateIdVector.size()));
        return reader;
    }

    @Bean
    @org.springframework.batch.core.configuration.annotation.StepScope
    UserInformationItemReaderProcessor idtoInfomationProcessor() {
        return new UserInformationItemReaderProcessor();
    }

    @Bean
    @org.springframework.batch.core.configuration.annotation.StepScope
    IdtoInfomationItemWriter idtoInfomationItemWriter(){   
        return new IdtoInfomationItemWriter();
    }

/*
******************************** UpdateUserInformationStep **************************************
 */

    // @Bean(name = "PartitionerJob")
    // Job partitionerJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws UnexpectedInputException {
    //     updateIdList = client.getUpdateUserInformation(lastBatchDate);
    //     return new JobBuilder("partitionerJob", jobRepository)
    //       .start(partitionStep(jobRepository, transactionManager))
    //       .build();
    // }

    // public Job updateUserInformation(JobRepository jobRepository, PlatformTransactionManager transactionManager){
    // }

    // @Bean
    // Step partitionStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws UnexpectedInputException {
    //     return new StepBuilder("partitionStep", jobRepository)
    //         .partitioner("slaveStep", partitioner())
    //         .step(slaveStep(jobRepository, transactionManager))
    //         .taskExecutor(taskExecutor())
    //         .build();
    // }

    @Bean
    Step slaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws UnexpectedInputException {
        return new StepBuilder("slaveStep", jobRepository)
            .<UserServiceInformationResponseDTO, UserInformationEmbedding>chunk(1, transactionManager)
            .reader(userInformationItemReader(null, null))
            .processor(patchProcessor())
            .writer(userInformationItemWriter())
            .faultTolerant()
            .retryLimit(3)
            .retry(JDBCConnectionException.class)
            .retry(UnexpectedInputException.class)
            .build();
    }

    @Bean
    ResourcePartitioner partitioner() {
        ResourcePartitioner partitioner = new ResourcePartitioner(SharedVectors.updateInformationVector.size());
        return partitioner;
    }

    @Bean
    @org.springframework.batch.core.configuration.annotation.StepScope
    ListItemReader<UserServiceInformationResponseDTO> userInformationItemReader(@Value("#{stepExecutionContext['startValue']}") Long startValue, @Value("#{stepExecutionContext['endValue']}") Long endValue) {
        ListItemReader<UserServiceInformationResponseDTO> reader = new ListItemReader<>(SharedVectors.updateInformationVector.subList(0, SharedVectors.updateInformationVector.size()));
        return reader;
    }

    @Bean
    @org.springframework.batch.core.configuration.annotation.StepScope
    UserResponseProcessor patchProcessor() {
        return new UserResponseProcessor();
    }

    @Bean
    @org.springframework.batch.core.configuration.annotation.StepScope
    UserInformationItemWriter userInformationItemWriter(){   
        return new UserInformationItemWriter(userInformationEmbeddingStoreRepo);
    }


/*
****************************************** MatchingServiceStep ****************************************
 */

    // @Bean
    // Step matchingPartitionStep(@Qualifier("matchingJobrepository") JobRepository jobRepository, @Qualifier("matchingTransactionManager") PlatformTransactionManager transactionManager) throws UnexpectedInputException {
    //     return new StepBuilder("matchingPartitionStep", jobRepository)
    //         .partitioner("matchingStep", matchingPartitioner())
    //         .step(matchingStep(jobRepository, transactionManager))
    //         .taskExecutor(taskExecutor())
    //         .build();
    // }

    @Bean
    Step matchingStep(JobRepository jobRepository, JdbcTransactionManager transactionManager) throws UnexpectedInputException {
        return new StepBuilder("matchingStep", jobRepository)
            .<UserInformationEmbedding, MatchingInformation>chunk(1, transactionManager)
            .reader(updatedLearnerInformationEmbeddingItemReader(null, null))
            .processor(matchingProcessor())
            .writer(matchingInformationItemWriter())
            .faultTolerant()
            .retryLimit(3)
            .retry(JDBCConnectionException.class)
            .retry(UnexpectedInputException.class)
            .build();
    }

    @Bean
    ResourcePartitioner matchingPartitioner() {
        ResourcePartitioner partitioner = new ResourcePartitioner(SharedVectors.updateInformationVector.size());
        return partitioner;
    }

    @Bean
    @org.springframework.batch.core.configuration.annotation.StepScope
    ListItemReader<UserInformationEmbedding> updatedLearnerInformationEmbeddingItemReader(@Value("#{stepExecutionContext['startValue']}") Long startValue, @Value("#{stepExecutionContext['endValue']}") Long endValue) {
        ListItemReader<UserInformationEmbedding> reader = new ListItemReader<>(SharedVectors.updateLearnerInformationEmbeddingVector.subList(0, SharedVectors.updateLearnerInformationEmbeddingVector.size()));
        return reader;
    }

    @Bean
    @org.springframework.batch.core.configuration.annotation.StepScope
    JdbcBatchItemWriter<MatchingInformation> matchingInformationItemWriter()
    {
        JdbcBatchItemWriter<MatchingInformation> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource());
        itemWriter.setSql("INSERT INTO " +  MATCHINGTABLENAME + " (CoordinatorId, LearnerId) " +
                            "VALUES (:CoordinatorId, :LearnerId)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();
        //Update LastBatchDate
        return itemWriter;
    }

    @Bean
    @org.springframework.batch.core.configuration.annotation.StepScope
    MatchingProcessor matchingProcessor() {
        return new MatchingProcessor(userInformationEmbeddingStoreRepo);
    }
/*
*****************************************Bean Overriding**************************************************
*/
    @Bean
    TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean("dataSource")
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/MATCHINGINFORMATIONDB");
        dataSource.setUsername("matching");
        dataSource.setPassword("0000");
        return dataSource;
    }

    // @Bean("TransactionManager")
    // PlatformTransactionManager getTransactionManager() {
    //     return new ResourcelessTransactionManager();
    // }

    // @Bean("matchingTransactionManager")

    @Bean("transactionManager")
    PlatformTransactionManager getmatchingTransactionManager() {
        DataSourceTransactionManager transactionManager = new JdbcTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean("jobRepository")
    JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource());
        factory.setDatabaseType("MYSQL");
        factory.setTransactionManager(getmatchingTransactionManager());
        // JobRepositoryFactoryBean's methods Throws Generic Exception,
        // it would have been better to have a specific one
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean("jobLauncher")
    JobLauncher getJobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        // TaskExecutorJobLauncher's methods Throws Generic Exception,
        // it would have been better to have a specific one
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.setTaskExecutor(taskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    StepScope stepScope() {
        StepScope stepScope = new StepScope();
        stepScope.setAutoProxy(true);
        return stepScope;
    }
}