package com.example.jobsnewspaper.emailservices;

import com.example.jobsnewspaper.domains.EmailMessage;
import java.util.*;

public class TestEmailSendRequest {

    private EmailMessage message;

    private List<String> emails;

    public TestEmailSendRequest(EmailMessage message, List<String> emails) {
        this.message = message;
        this.emails = emails;
    }

    public EmailMessage getMessage() {
        return message;
    }

    public void setMessage(EmailMessage message) {
        this.message = message;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
