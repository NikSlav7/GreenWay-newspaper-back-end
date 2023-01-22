package com.example.jobsnewspaper.daos;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.example.jobsnewspaper.amazon.AmazonData;
import com.example.jobsnewspaper.converters.TimestampConverter;
import com.example.jobsnewspaper.domains.EmailMessage;
import com.example.jobsnewspaper.domains.MessageStatus;
import com.example.jobsnewspaper.emailservices.EmailSender;
import com.example.jobsnewspaper.emailservices.EmailService;
import com.example.jobsnewspaper.exceptions.WrongEmailDetailsException;
import com.example.jobsnewspaper.time.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Attr;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class EmailMessagesDao {

    private final AmazonDynamoDB amazonDynamoDB;

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public EmailMessagesDao(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    public EmailMessage saveEmailMessage(EmailMessage message){
        dynamoDBMapper.save(message);
        return message;
    }

    public void changeEmailStatus(EmailMessage emailMessage, MessageStatus messageStatus){
        int oldStatus = emailMessage.getMessageStatus();
        emailMessage.setMessageStatus(messageStatus.getInd());
        saveEmailMessage(emailMessage);
        deleteItem(oldStatus, emailMessage.getWhenToSend());
    }



    public List<EmailMessage> getDayMessages(Long time, int dayOffSet, int neededStatus){

        Map<String, AttributeValue> attributeValues = new HashMap<>();

        boolean sent = neededStatus == MessageStatus.NOT_SENT.getInd() ? false : true;

        int dayNullOffset = dayOffSet == 0 ? EmailSender.SENDING_INTERVAL*2 : 0;

        long closeInterval = sent ? time - (long) dayOffSet*24*60*60*1000+dayNullOffset : time + (long) dayOffSet*24*60*60*1000-dayNullOffset ;
        long wideInterval = sent ? time - (long) dayOffSet *24*60*60*1000-24*60*60*1000 : time + (long) dayOffSet*24*60*60*1000+24*60*60*1000;


        attributeValues.put(":currentTimeTopInterval", new AttributeValue().withN(String.valueOf(Math.max(closeInterval, wideInterval))));
        attributeValues.put(":currentTimeBottomInterval", new AttributeValue().withN(String.valueOf(Math.min(closeInterval, wideInterval))));
        attributeValues.put(":statusToGet", new AttributeValue().withN(String.valueOf(neededStatus)));
        DynamoDBQueryExpression<EmailMessage> request = new DynamoDBQueryExpression<EmailMessage>();
        request.withExpressionAttributeValues(attributeValues);
        request.withKeyConditionExpression("messageStatus = :statusToGet AND whenToSend BETWEEN :currentTimeBottomInterval AND :currentTimeTopInterval");
        request.withLimit(5);
        List<EmailMessage> messages = dynamoDBMapper.query(EmailMessage.class, request);

        return messages;
    }

    public void replaceMessage(EmailMessage messageToReplace, EmailMessage newMessage){
        deleteItem(messageToReplace.getMessageStatus(), messageToReplace.getWhenToSend());
        saveEmailMessage(newMessage);
    }

    public void removeMessage(EmailMessage emailMessage){
        deleteItem(emailMessage.getMessageStatus(), emailMessage.getWhenToSend());
    }

    private void deleteItem(int messageStatus, LocalDateTime whenToPost){
        Table table = new DynamoDB(amazonDynamoDB).getTable("EmailMessages");

        DeleteItemRequest itemRequest = new DeleteItemRequest();

        itemRequest.setTableName("EmailMessages");
        Map<String, AttributeValue> keys = new HashMap<>();
        keys.put("messageStatus", new AttributeValue().withN(String.valueOf(messageStatus)));
        keys.put("whenToSend", new AttributeValue().withN(String.valueOf(new TimestampConverter().convert(whenToPost))));
        itemRequest.setKey(keys);

        amazonDynamoDB.deleteItem(itemRequest);
    }

    public EmailMessage getEmailMessage(String whenToSend, int messageStatus) throws WrongEmailDetailsException {
        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#whenToSend", "whenToSend");
        expressionAttributeNames.put("#status", "messageStatus");
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":timeToPost", new AttributeValue().withN(String.valueOf(whenToSend)));
        expressionAttributeValues.put(":statusToGet", new AttributeValue().withN(String.valueOf(messageStatus)));
        DynamoDBQueryExpression<EmailMessage> queryRequest = new DynamoDBQueryExpression<EmailMessage>()
                .withKeyConditionExpression("#status = :statusToGet AND #whenToSend = :timeToPost")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withExpressionAttributeNames(expressionAttributeNames);

        List<EmailMessage> emailMessages = dynamoDBMapper.query(EmailMessage.class, queryRequest);

        if (emailMessages.size() == 0) throw new WrongEmailDetailsException("Email with such details doesn't exist");
        return emailMessages.get(0);
    }



    public List<EmailMessage> getAllMessagesToSendNow(){
        Map<String, String> expressionAttributeNames = new HashMap<>();
        long curTime = TimeManager.getUTCTime();
        expressionAttributeNames.put("#whenToSend", "whenToSend");
        expressionAttributeNames.put("#status", "messageStatus");
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":currentTime", new AttributeValue().withN(String.valueOf(curTime)));
        expressionAttributeValues.put(":currentTimeWithoutTwoIntervals", new AttributeValue().withN(String.valueOf(curTime-EmailSender.SENDING_INTERVAL)));
        expressionAttributeValues.put(":statusToGet", new AttributeValue().withN(String.valueOf(MessageStatus.NOT_SENT.getInd())));
        DynamoDBQueryExpression<EmailMessage> queryRequest = new DynamoDBQueryExpression<EmailMessage>()
                .withKeyConditionExpression("#status = :statusToGet AND #whenToSend BETWEEN :currentTimeWithoutTwoIntervals AND :currentTime")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withExpressionAttributeNames(expressionAttributeNames);

        List<EmailMessage> emailMessages = dynamoDBMapper.query(EmailMessage.class, queryRequest);

        return emailMessages;
    }
}
