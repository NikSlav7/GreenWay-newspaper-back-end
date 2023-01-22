package com.example.jobsnewspaper.amazon;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.jobsnewspaper.domains.EmailMessage;
import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.PaintEvent;


@Component
public class AmazonSetup {





    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    private DynamoDBMapper dynamoDBMapper;


    public void setupDynamoDB(){
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        DeleteTableRequest deleteTableRequest = dynamoDBMapper.generateDeleteTableRequest(EmailNewspaperFollower.class);
        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(EmailNewspaperFollower.class);
        createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(2L, 2L));
        try {
            amazonDynamoDB.createTable(createTableRequest);
        } catch (Exception e){
            e.printStackTrace();
        }

        CreateTableRequest createTableRequestEmailMessage = dynamoDBMapper.generateCreateTableRequest(EmailMessage.class);
        createTableRequestEmailMessage.setProvisionedThroughput(new ProvisionedThroughput(2L, 2L));
        amazonDynamoDB.createTable(createTableRequestEmailMessage);
    }
}
