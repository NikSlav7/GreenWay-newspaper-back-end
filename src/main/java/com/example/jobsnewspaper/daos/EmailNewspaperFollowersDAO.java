package com.example.jobsnewspaper.daos;


import ch.qos.logback.core.spi.ScanException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.example.jobsnewspaper.amazon.AmazonData;
import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import com.example.jobsnewspaper.repositories.EmailNewspaperFollowersRepo;
import com.example.jobsnewspaper.repositories.FollowersRepo;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.*;
import org.springframework.stereotype.Service;

@Repository
@ComponentScan(basePackages = {"com.example.jobsnewspaper.repositories"})
public class EmailNewspaperFollowersDAO {

    private final AmazonDynamoDB amazonDynamoDB;

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public EmailNewspaperFollowersDAO(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }


    public EmailNewspaperFollower saveEmailNewspaperFollower(EmailNewspaperFollower emailNewspaperFollower){
        dynamoDBMapper.save(emailNewspaperFollower);
        return emailNewspaperFollower;
    }

    public List<EmailNewspaperFollower> getAllEmailNewspaperFollowers(){
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.scan(EmailNewspaperFollower.class, scanExpression);
    }

    public boolean checkIfEmailAlreadyRegistered(String email){
        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.setTableName(AmazonData.FOLLOWERS_TABLE_NAME);
        Map<String, AttributeValue> attributeValueMap = new HashMap<>();
        attributeValueMap.put("followerEmail", new AttributeValue().withS(email));
        getItemRequest.setKey(attributeValueMap);

       GetItemResult getItemResult = amazonDynamoDB.getItem(getItemRequest);

        return getItemResult.getItem() != null;
    }


}
