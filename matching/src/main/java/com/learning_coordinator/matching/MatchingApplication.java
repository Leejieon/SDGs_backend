package com.learning_coordinator.matching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.learning_coordinator.matching.config.schedulers.BatchScheduler;

@SpringBootApplication
//@EnableEurekaClient
public class MatchingApplication{

	private static final Logger LOGGER = LoggerFactory.getLogger(MatchingApplication.class);
	public static void main(String[] args) {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BatchScheduler.class);
        context.refresh();

		final JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		final Job job = (Job) context.getBean("MatchingJob");

		SpringApplication.run(MatchingApplication.class, args);

		LOGGER.info("Starting the batch job");
        try {
            final JobExecution execution = jobLauncher.run(job, new JobParameters());
            LOGGER.info("Job Status : {}", execution.getStatus());
        } catch (final Exception e) {
            e.printStackTrace();
            LOGGER.error("Job failed {}", e.getMessage());
        }
	}
}