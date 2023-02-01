package com.example.jobsnewspaper.managers;

import com.example.jobsnewspaper.domains.EmailMessageWithStringInterests;
import com.example.jobsnewspaper.domains.EmailNewspaperFollowerWithStringInterests;

public class UpdateFollowerRequest {

    EmailNewspaperFollowerWithStringInterests oldFollower, newFollower;


    public UpdateFollowerRequest(EmailNewspaperFollowerWithStringInterests oldFollower, EmailNewspaperFollowerWithStringInterests newFollower) {
        this.oldFollower = oldFollower;
        this.newFollower = newFollower;
    }

    public EmailNewspaperFollowerWithStringInterests getOldFollower() {
        return oldFollower;
    }

    public void setOldFollower(EmailNewspaperFollowerWithStringInterests oldFollower) {
        this.oldFollower = oldFollower;
    }

    public EmailNewspaperFollowerWithStringInterests getNewFollower() {
        return newFollower;
    }

    public void setNewFollower(EmailNewspaperFollowerWithStringInterests newFollower) {
        this.newFollower = newFollower;
    }
}
