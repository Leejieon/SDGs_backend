package com.learning_coordinator.matching.batch;

import java.util.Collections;
import java.util.Date;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.data.domain.Sort;

import com.learning_coordinator.matching.domain.UserInformation;
import com.learning_coordinator.matching.repository.UserInformationRepository;
import com.learning_coordinator.matching.utility.ObjectJSONFileUtil;

public class DBReader_UserInformation{
    
    private UserInformationRepository user_InformationRepository;

    public RepositoryItemReader<UserInformation> read(){
        Date lastbatchdate = ObjectJSONFileUtil.readLastBatchDateJSONFileOrCreate();
        return new RepositoryItemReaderBuilder<UserInformation>()
                .repository(user_InformationRepository)
                .methodName("findByModifiedDateBeforeOrderByCreatedDateDesc")
                .arguments(lastbatchdate)
                .sorts(Collections.singletonMap("ModifiedDate", Sort.Direction.ASC))
                .pageSize(1)
                .saveState(true)
                .build();
    }    
}
