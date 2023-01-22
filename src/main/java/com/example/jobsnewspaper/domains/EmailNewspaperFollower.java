package com.example.jobsnewspaper.domains;

import java.util.*;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.example.jobsnewspaper.converters.InterestsToStringConverter;
import jakarta.validation.constraints.Email;
import lombok.ToString;

@DynamoDBTable(tableName = "EmailNewspaperFollowers")
public class EmailNewspaperFollower{



    @DynamoDBHashKey
    private String followerEmail;



    @DynamoDBTypeConverted(converter = InterestsToStringConverter.class)
    private List<Interest> interests;


    public EmailNewspaperFollower(){

    }


    public EmailNewspaperFollower(String followerEmail, List<Interest> interests) {
        this.followerEmail = followerEmail;
        this.interests = interests;

    }

    public EmailNewspaperFollower emailNewspaperFollowerFromRegistrationRequest(RegistrationRequest request){
        return new EmailNewspaperFollower(request.getEmail(), request.getAllInterest());
    }



    public String getFollowerEmail() {
        return followerEmail;
    }

    public void setFollowerEmail(String followerEmail) {
        this.followerEmail = followerEmail;
    }



    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    @Override
    public String toString() {
        return "EmailNewspaperFollower{" +
                ", followerEmail='" + followerEmail + '\'' +
                ", interests=" + interests +
                '}';
    }
}
