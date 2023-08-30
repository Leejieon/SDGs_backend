package com.learning_coordinator.matching.config.schedulers;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;

import com.learning_coordinator.matching.batch.SharedVectors;
import com.learning_coordinator.matching.dto.UserServiceInformationResponseDTO;

public class IdtoInfomationItemWriter implements ItemWriter<UserServiceInformationResponseDTO> {

	@Override
	public void write(@NonNull Chunk<? extends UserServiceInformationResponseDTO> chunk) throws Exception {
		for (UserServiceInformationResponseDTO userResponse : chunk) {
			SharedVectors.updateInformationVector.add(userResponse);
        }
	}
}
