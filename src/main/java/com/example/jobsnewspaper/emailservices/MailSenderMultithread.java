package com.example.jobsnewspaper.emailservices;

import com.example.jobsnewspaper.daos.EmailNewspaperFollowersDAO;
import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.*;
import java.util.Properties;

public class MailSenderMultithread implements Runnable{

    @Value("${spring.mail.username}")
    private String sendingEmail = ""

    private String password = "";

    private JavaMailSender javaMailSender;

    private String subject, content;

    private List<EmailNewspaperFollower> followerList;

    public MailSenderMultithread(String subject, String content, List<EmailNewspaperFollower> followerList, JavaMailSender javaMailSender){
        this.subject = subject;
        this.content = content;
        this.followerList = followerList;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void run() {
        try {
            sendMailMessage(subject, content, followerList);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMailMessage(String subject, String content, List<EmailNewspaperFollower> followerList) throws MessagingException {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.zoho.eu");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.zoho.eu");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendingEmail, password);
            }
        });
        Transport transport = session.getTransport("smtp");
        transport.connect(sendingEmail, password);
        long testTime = System.currentTimeMillis();
        followerList.stream().forEach(follower -> {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
                mimeMessageHelper.setFrom(sendingEmail);
                mimeMessageHelper.setTo(follower.getFollowerEmail());
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(content, true);
                transport.sendMessage(mimeMessage, new Address[]{new InternetAddress(follower.getFollowerEmail())});
            } catch (MessagingException e){

            }
        });
        System.out.println((System.currentTimeMillis()-testTime) / followerList.size());

//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//        mimeMessageHelper.setFrom(sendingEmail);
//        mimeMessageHelper.setTo(receiversEmail);
//        mimeMessageHelper.setSubject(subject);
//        mimeMessageHelper.setText(content, true);
//
//        javaMailSender.send(mimeMessage);
    }
}
