package com.example.jobsnewspaper.daos;


import ch.qos.logback.core.spi.ScanException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.example.jobsnewspaper.amazon.AmazonData;
import com.example.jobsnewspaper.domains.EmailMessage;
import com.example.jobsnewspaper.domains.EmailMessageWithStringInterests;
import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import com.example.jobsnewspaper.exceptions.NoFollowersFoundException;
import com.example.jobsnewspaper.repositories.EmailNewspaperFollowersRepo;
import com.example.jobsnewspaper.repositories.FollowersRepo;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.*;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;

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

    public void removeEmailNewspaperFollower(String email){

        DeleteItemRequest deleteItemRequest = new DeleteItemRequest();
        deleteItemRequest.setTableName(AmazonData.FOLLOWERS_TABLE_NAME);
        Map<String, AttributeValue> attributeValueMap = new HashMap<>();

        attributeValueMap.put("followerEmail", new AttributeValue().withS(email));
        deleteItemRequest.setKey(attributeValueMap);

        amazonDynamoDB.deleteItem(deleteItemRequest);
    }
    public EmailNewspaperFollower getFollower(String followerEmail) throws NoFollowersFoundException {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":followerEmail", new AttributeValue().withS(String.valueOf(followerEmail)));
        DynamoDBQueryExpression<EmailNewspaperFollower> queryRequest = new DynamoDBQueryExpression<EmailNewspaperFollower>()
                .withKeyConditionExpression("followerEmail = :followerEmail")
                .withExpressionAttributeValues(expressionAttributeValues);

        List<EmailNewspaperFollower> emailFollowers = dynamoDBMapper.query(EmailNewspaperFollower.class, queryRequest);
        if (emailFollowers.size() == 0) throw new NoFollowersFoundException("No followers were found");

        return emailFollowers.get(0);
    }
    public List<EmailNewspaperFollower> getFollowers(String emailPrefix) throws NoFollowersFoundException {

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":prefix", new AttributeValue().withS(emailPrefix));

        Map<String, String> attributeNames = new HashMap<>();
        attributeNames.put("#followerEmail", "followerEmail");
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withExpressionAttributeValues(expressionAttributeValues)
                .withFilterExpression("begins_with(followerEmail, :prefix)");
        scanExpression.setLimit(10);

        List<EmailNewspaperFollower> emailNewspaperFollowers = dynamoDBMapper.scan(EmailNewspaperFollower.class, scanExpression);

        if (emailNewspaperFollowers.size() == 0) throw new NoFollowersFoundException("No followers were found");

        return emailNewspaperFollowers;
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
