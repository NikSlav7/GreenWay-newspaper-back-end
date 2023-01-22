package com.example.jobsnewspaper.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.example.jobsnewspaper.domains.Interest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

public class InterestsToStringConverter implements DynamoDBTypeConverter<String, List<Interest>> {
    @Override
    public String convert(List<Interest> interests) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(interests);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Interest> unconvert(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<String> interestNames =  objectMapper.readValue(s, new TypeReference<>() {
            });
            return interestNames.stream().map(name -> Interest.interestFromString(name)).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
