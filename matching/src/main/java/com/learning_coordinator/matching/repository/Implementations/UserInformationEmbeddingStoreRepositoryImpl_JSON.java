package com.learning_coordinator.matching.repository.Implementations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import com.google.gson.Gson;
import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import com.learning_coordinator.matching.domain.UserInformationEmbeddingStore;
import com.learning_coordinator.matching.repository.UserInformationEmbeddingStoreRepository;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

@Repository
public class UserInformationEmbeddingStoreRepositoryImpl_JSON implements UserInformationEmbeddingStoreRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInformationEmbeddingStoreRepositoryImpl_JSON.class.getName());
    private static UserInformationEmbeddingStore lastBatchUserInformationEmbeddingStore;
    public static Date lastBatchDate;
    private static final String FILEPATH = "src/main/resources/UserInformationEmbeddingStore.json";

    public UserInformationEmbeddingStoreRepositoryImpl_JSON(){
        try {
            ClassPathResource resource = new ClassPathResource(FILEPATH);
            Gson gson = new Gson();
            if(resource.exists()){
                byte[] byteData = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String data = new String(byteData, StandardCharsets.UTF_8);
                JSONObject object = (JSONObject)new JSONParser().parse(data);
                lastBatchUserInformationEmbeddingStore = gson.fromJson(object.toJSONString(), UserInformationEmbeddingStore.class);
            }
            else{
                lastBatchUserInformationEmbeddingStore = new UserInformationEmbeddingStore();
                lastBatchUserInformationEmbeddingStore.setId(1);
                lastBatchUserInformationEmbeddingStore.setLastbatchdate(new Date(Long.MIN_VALUE));
                lastBatchUserInformationEmbeddingStore.setEmbeddingStore(new InMemoryEmbeddingStore<UserInformationEmbedding>());
                String json = gson.toJson(lastBatchUserInformationEmbeddingStore);
                JSONObject object = (JSONObject)new JSONParser().parse(json);
                byte[] byteData = object.toJSONString().getBytes();
                FileOutputStream fos = new FileOutputStream(FILEPATH);
                fos.write(byteData);
                fos.close();
            }
        } catch (IOException | ParseException e) {
            LOGGER.error("readUserInformationEmbeddingStoreJSONFileOrCreate <- Error Occured", e);
        } finally {
            LOGGER.info("Initialization Success - UserInformationEmbeddingStore.json");
            LOGGER.info("-------------LastBatchDate : " + lastBatchUserInformationEmbeddingStore.getLastbatchdate().toString() + " -------------");  
        }
    }

    @Override
    public void updateEmbeddingStoreObject(UserInformationEmbedding update) {
        InMemoryEmbeddingStore<UserInformationEmbedding> updateEmbeddingStore = lastBatchUserInformationEmbeddingStore.getEmbeddingStore();
        updateEmbeddingStore.add(update.getUserId().toString(), update.getEmbedding());
        lastBatchUserInformationEmbeddingStore.setEmbeddingStore(updateEmbeddingStore);
        try {
            ClassPathResource resource = new ClassPathResource(FILEPATH);
            Gson gson = new Gson();
            String json = gson.toJson(lastBatchUserInformationEmbeddingStore);
            JSONObject object = (JSONObject)new JSONParser().parse(json);
            byte[] byteData = object.toJSONString().getBytes();
    
            FileOutputStream fos = new FileOutputStream(resource.getPath());
            fos.write(byteData);
            fos.close();
        } catch (IOException | ParseException e) {
            LOGGER.error("updatelastbatchEmbeddingStore <- Error Occured", e);
        }
    }

    @Override
    public InMemoryEmbeddingStore<UserInformationEmbedding> getEmbeddingStore() {
        return lastBatchUserInformationEmbeddingStore.getEmbeddingStore();
    }

    @Override
    public void setLastBatchDate(Date lastBatchDate){
        lastBatchUserInformationEmbeddingStore.setLastbatchdate(lastBatchDate);
        try {
            ClassPathResource resource = new ClassPathResource(FILEPATH);
            Gson gson = new Gson();
            String json = gson.toJson(lastBatchUserInformationEmbeddingStore);
            JSONObject object = (JSONObject)new JSONParser().parse(json);
            byte[] byteData = object.toJSONString().getBytes();
    
            FileOutputStream fos = new FileOutputStream(resource.getPath());
            fos.write(byteData);
            fos.close();
        } catch (IOException | ParseException e) {
            LOGGER.error("setLastBatchDate <- Error Occured", e);
        } finally {
            LOGGER.info("-------------LastBatchDate : " + lastBatchUserInformationEmbeddingStore.getLastbatchdate().toString() + " -------------");
        }
    }
}
