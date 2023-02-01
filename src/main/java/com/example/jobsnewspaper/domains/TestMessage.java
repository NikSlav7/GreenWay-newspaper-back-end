package com.example.jobsnewspaper.domains;
import java.util.*;
public class TestMessage {

    private Map<String, String> interests;

    private String header, subheader;


    public TestMessage(Map<String, String> interests, String header, String subheader) {
        this.interests = interests;
        this.header = header;
        this.subheader = subheader;
    }

    public Map<String, String> getInterests() {
        return interests;
    }

    public void setInterests(Map<String, String> interests) {
        this.interests = interests;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubheader() {
        return subheader;
    }

    public void setSubheader(String subheader) {
        this.subheader = subheader;
    }
}
