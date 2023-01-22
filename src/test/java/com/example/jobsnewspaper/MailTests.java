package com.example.jobsnewspaper;


import com.example.jobsnewspaper.daos.EmailMessagesDao;
import com.example.jobsnewspaper.daos.EmailNewspaperFollowersDAO;
import com.example.jobsnewspaper.emailTemplates.EmailTemplates;
import com.example.jobsnewspaper.emailTemplates.insidecontent.InsideContentN1;
import com.example.jobsnewspaper.emailTemplates.mailtemplates.Template;
import com.example.jobsnewspaper.emailTemplates.TemplatesDataInserter;
import com.example.jobsnewspaper.emailservices.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest
public class MailTests {

    @Autowired
    EmailService emailService;

    @Autowired
    EmailNewspaperFollowersDAO followersDAO;

    @Autowired
    EmailMessagesDao emailMessagesDao;

    @Autowired
    EmailTemplates emailTemplates;

    @Autowired
    TemplatesDataInserter templatesDataInserter;

    @Test
    void sendEmail() throws MessagingException {
    }

    @Test
    void sendMultipleMails() throws MessagingException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Template template = emailTemplates.getRandomTemplate();
        template.insertInsideContent(0, new InsideContentN1().insertHeaderAndText("Healthy food", "FKJFKSDJFKSDJK"));
        template.insertInsideContent(1, null);
        template.insertInsideContent(2, new InsideContentN1().insertHeaderAndText("Healthy food", "FKJFKSDJFKSDJK"));
        emailService.sendMultipleMailMessages("Test",template.getTemplateContent(), followersDAO.getAllEmailNewspaperFollowers());
    }

    @Test
    void sendEmailsToEveryone() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        emailService.sendEmailsToEverybody("Testing", followersDAO.getAllEmailNewspaperFollowers(), emailMessagesDao.getAllMessagesToSendNow().get(0));
    }
}
