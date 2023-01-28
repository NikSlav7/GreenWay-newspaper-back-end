package com.example.jobsnewspaper.domains;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.example.jobsnewspaper.converters.InterestsToStringConverter;

import java.util.List;
import java.util.stream.Collectors;

public class EmailNewspaperFollowerWithStringInterests {
    private String followerEmail;



    private List<String> interests;


    public EmailNewspaperFollowerWithStringInterests(){

    }


    public EmailNewspaperFollowerWithStringInterests(String followerEmail,  List<String> interests) {
        this.followerEmail = followerEmail;
        this.interests = interests;

    }

    public EmailNewspaperFollowerWithStringInterests fromFollower(EmailNewspaperFollower emailNewspaperFollower){

        this.followerEmail = emailNewspaperFollower.getFollowerEmail();
        this.interests = emailNewspaperFollower.getInterests().stream().
                map(interest -> Interest.interestToString(interest)).collect(Collectors.toList());

        return this;
    }




    public String getFollowerEmail() {
        return followerEmail;
    }

    public void setFollowerEmail(String followerEmail) {
        this.followerEmail = followerEmail;
    }



    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
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
