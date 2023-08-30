package com.learning_coordinator.matching.config.schedulers;

import java.util.Vector;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.lang.Nullable;

import com.learning_coordinator.matching.batch.SharedVectors;
import com.learning_coordinator.matching.domain.Role;
import com.learning_coordinator.matching.dto.UserServiceIdResponseDTO;

//import static com.learning_coordinator.matching.config.schedulers.BatchScheduler.client;
// import static com.learning_coordinator.matching.repository.Implementations.UserInformationEmbeddingStoreRepositoryImpl_JSON.lastBatchDate;


public class UpdateUserInformationDecider implements JobExecutionDecider {
    private static final int UPDATENUMBER = 1;
    public static final String NEXT = "NEXT";
    public static final String SKIP = "SKIP";

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, @Nullable StepExecution stepExecution) {
        int numberofUpdate = 2;//client.getNumberOfUpdate(lastbatchDate);
        if(numberofUpdate >= UPDATENUMBER){
            //Vector<UserServiceIdResponseDTO> updateVector = new Vector<>(client.getUpdateUserInformation(lastbatchDate));
            Vector<UserServiceIdResponseDTO> updateVector = new Vector<>();
            updateVector.add(new UserServiceIdResponseDTO(Role.LEARNER, (long) 123)); 
            updateVector.add(new UserServiceIdResponseDTO(Role.COORDINATOR, (long) 124));
            SharedVectors.updateIdVector = updateVector;
            
            return new FlowExecutionStatus(NEXT);
        }
        else{
            return new FlowExecutionStatus(SKIP);
        }
    }
}
