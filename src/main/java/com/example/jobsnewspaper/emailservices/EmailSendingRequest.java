package com.example.jobsnewspaper.emailservices;

import java.util.Map;

public class EmailSendingRequest {

    private Map<String, String> body;

    private String whenToPost;

    private String header, subHeader;

    public EmailSendingRequest(String header, String subHeader, Map<String, String> body, String whenToPost) {
        this.header = header;
        this.subHeader = subHeader;
        this.body = body;
        this.whenToPost = whenToPost;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public String getWhenToPost() {
        return whenToPost;
    }

    public void setWhenToPost(String whenToPost) {
        this.whenToPost = whenToPost;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }
}
