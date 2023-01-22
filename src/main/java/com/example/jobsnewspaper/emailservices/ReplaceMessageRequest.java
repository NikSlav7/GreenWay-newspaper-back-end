package com.example.jobsnewspaper.emailservices;

import com.example.jobsnewspaper.domains.EmailMessage;
import com.example.jobsnewspaper.domains.EmailMessageWithStringInterests;

import java.util.*;


public class ReplaceMessageRequest {


    private List<EmailMessageWithStringInterests> messageList;

    public ReplaceMessageRequest(){

    }

    public ReplaceMessageRequest(List<EmailMessageWithStringInterests> messageList) {
        this.messageList = messageList;
    }

    public List<EmailMessageWithStringInterests> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<EmailMessageWithStringInterests> messageList) {
        this.messageList = messageList;
    }
}
