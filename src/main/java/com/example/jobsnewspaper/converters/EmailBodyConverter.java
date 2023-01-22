package com.example.jobsnewspaper.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.example.jobsnewspaper.domains.Interest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class EmailBodyConverter implements DynamoDBTypeConverter<String, Map<Interest, String>> {
    @Override
    public Map<Interest, String> unconvert(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<Interest, String>> reference = new TypeReference<Map<Interest, String>>(){};
        try {
            return objectMapper.readValue(s, reference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convert(Map<Interest, String> interestStringMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(interestStringMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
