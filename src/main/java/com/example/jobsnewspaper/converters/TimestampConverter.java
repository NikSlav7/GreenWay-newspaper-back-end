package com.example.jobsnewspaper.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class TimestampConverter implements DynamoDBTypeConverter<Long, LocalDateTime> {
    @Override
    public LocalDateTime unconvert(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.systemDefault());
    }

    @Override
    public Long convert(LocalDateTime timestamp) {
        return Timestamp.valueOf(timestamp).getTime();
    }
}
