package com.example.jobsnewspaper.domains;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.jobsnewspaper.converters.EmailBodyConverter;
import com.example.jobsnewspaper.converters.TimestampConverter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@DynamoDBTable(tableName = "EmailMessages")
public class EmailMessageWithStringInterests {

    private int messageStatus;

    private LocalDateTime whenToSend;

    private String messageHeader;

    private String messageSubHeader;



    private String messageId;

    private Map<String, String> body;




    public EmailMessageWithStringInterests() {
    }

    public EmailMessageWithStringInterests(String messageId, String messageHeader, String messageSubHeader, int messageStatus, Map<String, String> body, LocalDateTime whenToSend) {
        this.messageId = messageId;
        this.messageHeader = messageHeader;
        this.messageSubHeader = messageSubHeader;
        this.messageStatus = messageStatus;
        this.body = body;
        this.whenToSend = whenToSend;
    }

    public EmailMessageWithStringInterests fromEmailMessage(EmailMessage emailMessage){
        this.messageStatus = emailMessage.getMessageStatus();
        this.messageHeader = emailMessage.getMessageHeader();
        this.messageSubHeader = emailMessage.getMessageSubHeader();
        this.whenToSend = emailMessage.getWhenToSend();

        Map<String, String> interestNames = new HashMap<>();
        for (Map.Entry<Interest, String> entry : emailMessage.getBody().entrySet()){
            interestNames.put(Interest.interestToString(entry.getKey()), entry.getValue());
        }
        this.body = interestNames;
        return this;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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


    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public LocalDateTime getWhenToSend() {
        return whenToSend;
    }

    public void setWhenToSend(LocalDateTime whenToSend) {
        this.whenToSend = whenToSend;
    }
}
