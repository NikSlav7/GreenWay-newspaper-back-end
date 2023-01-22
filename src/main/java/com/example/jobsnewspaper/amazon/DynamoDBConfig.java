package com.example.jobsnewspaper.amazon;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.util.StringUtils;
import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.jobsnewspaper.amazon")
public class DynamoDBConfig {


    @Value("${amazon.dynamodb.endpoint}")
    String dynamoDBEndpoint;


    @Value("${amazon.aws.accesskey}")
    String amazonAccessKey;

    @Value("${amazon.aws.secretkey}")
    String amazonSecretKey;

    private DynamoDBMapper dynamoDBMapper;

    @Bean
    public AmazonDynamoDB amazonDynamoDB(){
        AWSCredentials credentials = awsCredentials();
        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(credentials);
        if (!StringUtils.isNullOrEmpty(dynamoDBEndpoint)) amazonDynamoDB.setEndpoint(dynamoDBEndpoint);


        return amazonDynamoDB;
    }



    public AWSCredentials awsCredentials(){
        return new BasicAWSCredentials(amazonAccessKey,amazonSecretKey);
    }



}
