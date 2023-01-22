package com.example.jobsnewspaper.emailservices;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailServicesConfig {

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.zoho.eu");
        mailSender.setPort(465);

        mailSender.setUsername("nikslav07@zohomail.eu");
        mailSender.setPassword("Kolbasa1289");
        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.smtp.host", "smtp.zoho.eu");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // If ssl is your concern, uncomment this line
        properties.setProperty("mail.smtp.port", "465");//HTTP - 587 , HTTPS - 465
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.zoho.eu");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");

        return mailSender;


    }
}
