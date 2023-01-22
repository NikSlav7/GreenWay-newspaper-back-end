package com.example.jobsnewspaper.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.example.jobsnewspaper.domains.MessageStatus;

public class MessageStatusConverter implements DynamoDBTypeConverter<MessageStatus, Integer> {
    @Override
    public MessageStatus convert(Integer integer) {
        return MessageStatus.values()[integer];
    }

    @Override
    public Integer unconvert(MessageStatus messageStatus) {
        return messageStatus.getInd();
    }
}
