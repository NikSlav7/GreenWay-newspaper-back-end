package com.example.jobsnewspaper.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class BooleanToStringConverter implements DynamoDBTypeConverter<Boolean, String> {
    @Override
    public Boolean convert(String s) {
        return Boolean.valueOf(s);
    }

    @Override
    public String unconvert(Boolean aBoolean) {
        return aBoolean.toString();
    }
}
