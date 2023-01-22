package com.example.jobsnewspaper.emailservices;

import com.example.jobsnewspaper.daos.EmailMessagesDao;
import com.example.jobsnewspaper.daos.EmailNewspaperFollowersDAO;
import com.example.jobsnewspaper.domains.EmailMessage;
import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import com.example.jobsnewspaper.domains.MessageStatus;
import com.example.jobsnewspaper.emailTemplates.EmailTemplates;
import com.example.jobsnewspaper.emailTemplates.mailtemplates.Template;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class EmailSender {


    public static final int SENDING_INTERVAL = 600000;

    private final EmailMessagesDao emailMessagesDao;

    private final EmailService emailService;



    private final EmailNewspaperFollowersDAO emailNewspaperFollowersDAO;

    public EmailSender(EmailMessagesDao emailMessagesDao, EmailService emailService, EmailNewspaperFollowersDAO emailNewspaperFollowersDAO) {
        this.emailMessagesDao = emailMessagesDao;
        this.emailService = emailService;
        this.emailNewspaperFollowersDAO = emailNewspaperFollowersDAO;
    }


    @Scheduled(fixedDelay = SENDING_INTERVAL)
    public void sendRegularEmails() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        System.out.println("checking");
        List<EmailMessage> emailMessages = emailMessagesDao.getAllMessagesToSendNow();
        if (emailMessages.size() == 0) return;
        List<EmailNewspaperFollower> followers = emailNewspaperFollowersDAO.getAllEmailNewspaperFollowers();

        for (EmailMessage message : emailMessages){
            emailService.sendEmailsToEverybody(message.getMessageHeader(), followers, message);
        }

    }


}
