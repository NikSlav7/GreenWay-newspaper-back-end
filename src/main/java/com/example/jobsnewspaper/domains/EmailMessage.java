package com.example.jobsnewspaper.domains;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.example.jobsnewspaper.converters.*;
import com.example.jobsnewspaper.exceptions.WrongEmailDetailsException;
import jakarta.validation.constraints.Email;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@DynamoDBTable(tableName = "EmailMessages")
public class EmailMessage{

    @DynamoDBHashKey
    private int messageStatus;

    @DynamoDBRangeKey
    @DynamoDBTypeConverted(converter = TimestampConverter.class)
    private LocalDateTime whenToSend;

    @DynamoDBAttribute
    private String messageHeader;

    @DynamoDBAttribute
    private String messageSubHeader;



    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = EmailBodyConverter.class)
    private Map<Interest, String> body;




    public EmailMessage() {
    }

    public EmailMessage(String messageHeader, String messageSubHeader, int messageStatus, Map<Interest, String> body, LocalDateTime whenToSend) {
        this.messageHeader = messageHeader;
        this.messageSubHeader = messageSubHeader;
        this.messageStatus = messageStatus;
        this.body = body;
        this.whenToSend = whenToSend;
    }
    public EmailMessage (EmailMessage emailMessage){
        setMessageStatus(emailMessage.getMessageStatus());
        setMessageHeader(emailMessage.getMessageHeader());
        setMessageSubHeader(emailMessage.getMessageSubHeader());
        setWhenToSend(emailMessage.getWhenToSend());
        setBody(emailMessage.getBody());
    }



    public String getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(String messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getMessageSubHeader() {
        return messageSubHeader;
    }

    public void setMessageSubHeader(String messageSubHeader) {
        this.messageSubHeader = messageSubHeader;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }


    public Map<Interest, String> getBody() {
        return body;
    }

    public void setBody(Map<Interest, String> body) {
        this.body = body;
    }

    public LocalDateTime getWhenToSend() {
        return whenToSend;
    }

    public void setWhenToSend(LocalDateTime whenToSend) {
        this.whenToSend = whenToSend;
    }

    public EmailMessage fromMessageWithStringInterests(EmailMessageWithStringInterests message) throws WrongEmailDetailsException {
        setMessageStatus(message.getMessageStatus());
        setMessageHeader(message.getMessageHeader());
        setMessageSubHeader(message.getMessageSubHeader());
        setWhenToSend(message.getWhenToSend());

        setBody(Interest.interestMapFromStringMap(message.getBody()));

        return this;
    }
}
