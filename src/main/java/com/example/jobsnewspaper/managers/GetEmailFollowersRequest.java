package com.example.jobsnewspaper.managers;
import java.util.*;

public class GetEmailFollowersRequest {


    private String prefix;

    private List<String> interests;

    public GetEmailFollowersRequest(String prefix, List<String> interests) {
        this.prefix = prefix;
        this.interests = interests;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
