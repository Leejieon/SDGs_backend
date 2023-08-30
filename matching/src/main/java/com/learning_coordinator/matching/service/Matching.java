package com.learning_coordinator.matching.service;

import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import com.learning_coordinator.matching.dto.MatchingInformationDTO;
import com.learning_coordinator.matching.repository.Implementations.LearnerMatchingHistoryRepositoryImpl;
import com.learning_coordinator.matching.repository.Implementations.MatchingInformationRepositoryImpl;
import com.learning_coordinator.matching.repository.Implementations.UserInformationEmbeddingStoreRepositoryImpl_JSON;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import dev.langchain4j.store.embedding.EmbeddingMatch;

@Service
public class Matching {
    private static final Logger LOGGER = Logger.getLogger(Matching.class.getName());
    private List<MatchingInformationDTO> newMatchingList; 
    private MatchingInformationRepositoryImpl matchingRepo;
    private UserInformationEmbeddingStoreRepositoryImpl_JSON userInformationEmbeddingStoreRepo;
    private LearnerMatchingHistoryRepositoryImpl learnerMatchingHistoryRepo;

     /*
     * Matching API - student - teacher...
     */
    public Matching(UserInformationEmbeddingStoreRepositoryImpl_JSON userInformationEmbeddingStoreRepo) { // Learner Standard
        this.userInformationEmbeddingStoreRepo = userInformationEmbeddingStoreRepo;
        matchingRepo = new MatchingInformationRepositoryImpl();
        learnerMatchingHistoryRepo = new LearnerMatchingHistoryRepositoryImpl(); 
    }

    public List<MatchingInformationDTO> matchingTargetList(List<UserInformationEmbedding> matchingTargetInformations){
        System.out.println("______________Matching Service__________");
        int counter = 0;
        int maxResult = 10;
        for (UserInformationEmbedding targetInformation : matchingTargetInformations) {
            Long learnerId = targetInformation.getUserId();
            List<EmbeddingMatch<UserInformationEmbedding>> matchingEmbeddingList = userInformationEmbeddingStoreRepo.getEmbeddingStore().findRelevant(targetInformation.getEmbedding(), maxResult); 
            
            List<Long> currentMatchingList = matchingRepo.getMatchingOppenentIdsList();

            Set<Long> currentUserMatchingHistory = learnerMatchingHistoryRepo.getMatchingHistory(learnerId);
            counter++;
            for (EmbeddingMatch<UserInformationEmbedding> matchingEmbedding : matchingEmbeddingList) {    
                Long coordinatorId = matchingEmbedding.embedded().getUserId();
                if( !(currentUserMatchingHistory.contains(coordinatorId)) && !(currentMatchingList.contains(coordinatorId)) ){
                    newMatchingList.add(new MatchingInformationDTO(coordinatorId, learnerId));
                    LOGGER.log(Level.INFO, "Matching Success - Compatablility : " + matchingEmbedding.score()*100 +"%" );
                    break;
                }
            }
            if(newMatchingList.size() != counter){
                LOGGER.log(Level.INFO,"Matching Failed...");
            }
        }
        return newMatchingList;
    }

    public MatchingInformationDTO matchingTarget(UserInformationEmbedding matchingTargetInformation){
        System.out.println("_________________________Matching Service______________________");
        int maxResult = 10;
        Long learnerId = matchingTargetInformation.getUserId();
        MatchingInformationDTO newMatching;
        List<EmbeddingMatch<UserInformationEmbedding>> matchingEmbeddingList = userInformationEmbeddingStoreRepo.getEmbeddingStore().findRelevant(matchingTargetInformation.getEmbedding(), maxResult); 
        
        List<Long> currentMatchingList = matchingRepo.getMatchingOppenentIdsList();

        Set<Long> currentUserMatchingHistory = learnerMatchingHistoryRepo.getMatchingHistory(learnerId);
        if(matchingEmbeddingList != null){
            for (EmbeddingMatch<UserInformationEmbedding> matchingEmbedding : matchingEmbeddingList) {    
                System.out.println("__________________Matching with User Id "+ matchingEmbedding.embeddingId() + "________________________");
                Long coordinatorId = Long.parseLong(matchingEmbedding.embeddingId());
                if( !(currentUserMatchingHistory.contains(coordinatorId)) && !(currentMatchingList.contains(coordinatorId)) ){
                    newMatching = new MatchingInformationDTO(coordinatorId, learnerId);
                    LOGGER.log(Level.INFO, "Matching Success - Compatablility : " + matchingEmbedding.score()*100 +"%" );
                    return newMatching;
                }
            }
        }
        LOGGER.log(Level.INFO,"Matching Failed...");    
        return null;
    }
}
