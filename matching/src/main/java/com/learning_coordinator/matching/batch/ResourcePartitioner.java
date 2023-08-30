package com.learning_coordinator.matching.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class ResourcePartitioner implements Partitioner {
    private int size;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) throws NullPointerException{
        //Integer size = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + table, Integer.class);
        int targetSize = size / gridSize + 1;
        Map<String, ExecutionContext> result = new HashMap<>();
        int number = 0;
        int start = 1;
        int end = start + targetSize - 1;

        while (start <= size){
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if(end >= size){
                end = size;
            }

            value.putInt("startValue", start);
            value.putInt("endValue", end);
            start += targetSize;
            end += targetSize;

            number++;
        }
        
        return result;
    }

    public ResourcePartitioner(int size) {
        this.size = size;
    }
}
