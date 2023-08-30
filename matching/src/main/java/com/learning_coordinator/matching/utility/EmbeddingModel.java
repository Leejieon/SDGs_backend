package com.learning_coordinator.matching.utility;

import com.learning_coordinator.matching.domain.Role;
import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import com.learning_coordinator.matching.dto.CoordinatorResponseDTO;
import com.learning_coordinator.matching.dto.LearnerResponseDTO;
import com.learning_coordinator.matching.dto.UserInformationDTO;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.inprocess.InProcessEmbeddingModel;
import net.suuft.libretranslate.Language;
import net.suuft.libretranslate.Translator;

import static dev.langchain4j.model.inprocess.InProcessEmbeddingModelType.ALL_MINILM_L6_V2;

public class EmbeddingModel {
    
    private static InProcessEmbeddingModel embeddingModel = new InProcessEmbeddingModel(ALL_MINILM_L6_V2);
    
    public static UserInformationEmbedding makeUser_InformationEmbeddingDTO(UserInformationDTO user_Information) {
         /*
        * InprocessEmbeddingModel for Parrallel
        */
        //Translator.setUrlApi(); //Install LibreTranslate on server
        TextSegment segment = TextSegment.from(Translator.translate(Language.KOREAN,Language.ENGLISH,user_Information.toString()));
        Embedding embedding = embeddingModel.embed(segment);
        return new UserInformationEmbedding(user_Information.getId(), Role.COORDINATOR, embedding);
    }

    public static UserInformationEmbedding makeUser_InformationEmbeddingDTO(CoordinatorResponseDTO user_Information) {
         /*
        * InprocessEmbeddingModel for Parrallel
        */
        //Translator.setUrlApi(); //Install LibreTranslate on server
        TextSegment segment = TextSegment.from(Translator.translate(Language.KOREAN,Language.ENGLISH,user_Information.toString()));
        Embedding embedding = embeddingModel.embed(segment);
        return new UserInformationEmbedding(user_Information.getUserId(), Role.COORDINATOR ,embedding);
    }

    public static UserInformationEmbedding makeUser_InformationEmbeddingDTO(LearnerResponseDTO user_Information) {
         /*
        * InprocessEmbeddingModel for Parrallel
        */
        //Translator.setUrlApi(); //Install LibreTranslate on server
        TextSegment segment = TextSegment.from(Translator.translate(Language.KOREAN,Language.ENGLISH,user_Information.toString()));
        Embedding embedding = embeddingModel.embed(segment);
        return new UserInformationEmbedding(user_Information.getUserId(), Role.LEARNER , embedding);
    }
    
}
