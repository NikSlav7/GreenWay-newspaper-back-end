package com.example.jobsnewspaper.emailservices;

import com.example.jobsnewspaper.domains.EmailMessage;
import com.example.jobsnewspaper.domains.EmailMessageWithStringInterests;
import com.example.jobsnewspaper.domains.TestMessage;

import java.util.*;

public class TestEmailSendRequest {

    private TestMessage message;

    private List<String> emails;

    public TestEmailSendRequest(TestMessage message, List<String> emails) {
        this.message = message;
        this.emails = emails;
    }

    public TestMessage getMessage() {
        return message;
    }

    public void setMessage(TestMessage message) {
        this.message = message;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
