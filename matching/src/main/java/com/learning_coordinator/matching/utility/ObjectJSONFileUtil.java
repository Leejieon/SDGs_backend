package com.learning_coordinator.matching.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import com.learning_coordinator.matching.domain.UserInformationEmbeddingStore;

import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class ObjectJSONFileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectJSONFileUtil.class);

    public static Date readLastBatchDateJSONFileOrCreate()
    /*
     * read JSON file has information about last batch date
     * if not exists, create new JSON file
     */
    {
        try {
            ClassPathResource resource = new ClassPathResource("lastbatchdate.json");
            Gson gson = new Gson();
            if(resource.exists()){
                byte[] byteData = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String data = new String(byteData, StandardCharsets.UTF_8);
                JSONObject object = (JSONObject)new JSONParser().parse(data);
                Date date = gson.fromJson(object.toJSONString(), Date.class); 
                return date;
            }
            else{
                Date now = new Date(System.currentTimeMillis());
                String json = gson.toJson(now);
                JSONObject object = (JSONObject)new JSONParser().parse(json);
                byte[] byteData = object.toJSONString().getBytes();

                FileOutputStream fos = new FileOutputStream(resource.getFile());
                fos.write(byteData);
                fos.close();
                return now;
            }
        } catch (IOException | ParseException e) {
            LOGGER.error("readLastBatchDateJSONFileOrCreate <- Error Occured", e);
            return null;
        }
    }

    public static void updateLastBatchDate(){
        try {
            ClassPathResource resource = new ClassPathResource("lastbatchdate.json");
            Gson gson = new Gson();
            Date now = new Date(System.currentTimeMillis());
            String json = gson.toJson(now);
            JSONObject object = (JSONObject)new JSONParser().parse(json);
            byte[] byteData = object.toJSONString().getBytes();
    
            FileOutputStream fos = new FileOutputStream(resource.getFile());
            fos.write(byteData);
            fos.close();
        } catch (IOException | ParseException e) {
            LOGGER.error("updateLastBatchDate <- Error Occured", e);
        }
    }


    public static UserInformationEmbeddingStore readUserInformationEmbeddingStoreJSONFileOrCreate()
    /*
     * read JSON file has information about last batch date
     * if not exists, create new JSON file
     */
    {
        try {
            ClassPathResource resource = new ClassPathResource("UserInformationEmbeddingStore.json");
            Gson gson = new Gson();
            if(resource.exists()){
                byte[] byteData = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String data = new String(byteData, StandardCharsets.UTF_8);
                JSONObject object = (JSONObject)new JSONParser().parse(data);
                UserInformationEmbeddingStore userInformationEmbeddingStore = gson.fromJson(object.toJSONString(), UserInformationEmbeddingStore.class); 
                return userInformationEmbeddingStore;
            }
            else{
                UserInformationEmbeddingStore lastbatchEmbeddingStore;
                lastbatchEmbeddingStore = new UserInformationEmbeddingStore();
                lastbatchEmbeddingStore.setId(1);
                lastbatchEmbeddingStore.setLastbatchdate(new Date(Long.MIN_VALUE));
                lastbatchEmbeddingStore.setEmbeddingStore(new InMemoryEmbeddingStore<UserInformationEmbedding>());
                String json = gson.toJson(lastbatchEmbeddingStore);
                JSONObject object = (JSONObject)new JSONParser().parse(json);
                byte[] byteData = object.toJSONString().getBytes();

                FileOutputStream fos = new FileOutputStream(resource.getFile());
                fos.write(byteData);
                fos.close();
                return lastbatchEmbeddingStore;
            }
        } catch (IOException | ParseException e) {
            LOGGER.error("readUserInformationEmbeddingStoreJSONFileOrCreate <- Error Occured", e);
            return null;
        }
    }

    public static void updateUserInformationEmbeddingStore(UserInformationEmbeddingStore lastbatchEmbeddingStore){
        try {
            ClassPathResource resource = new ClassPathResource("UserInformationEmbeddingStore.json");
            Gson gson = new Gson();
            String json = gson.toJson(lastbatchEmbeddingStore);
            JSONObject object = (JSONObject)new JSONParser().parse(json);
            byte[] byteData = object.toJSONString().getBytes();
    
            FileOutputStream fos = new FileOutputStream(resource.getFile());
            fos.write(byteData);
            fos.close();
        } catch (IOException | ParseException e) {
            LOGGER.error("updatelastbatchEmbeddingStore <- Error Occured", e);
        }
    }

}
