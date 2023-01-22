package com.example.jobsnewspaper;

import com.example.jobsnewspaper.amazon.AmazonSetup;
import com.example.jobsnewspaper.emailservices.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.util.Properties;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class JobsNewspaperApplication {

	private static ApplicationContext applicationContext;

	public static void main(String[] args) throws MessagingException {
		Properties systemProps = System.getProperties();
		systemProps.put("javax.net.ssl.keyStorePassword","changeit");
		systemProps.put("javax.net.ssl.keyStore","C:\\Program Files\\Java\\jdk-17.0.3\\lib\\security\\cacerts");
		systemProps.put("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk-17.0.3\\lib\\security\\cacerts");
		systemProps.put("javax.net.ssl.trustStorePassword","changeit");
		System.setProperties(systemProps);
		applicationContext = SpringApplication.run(JobsNewspaperApplication.class, args);
		AmazonSetup amazonSetup = applicationContext.getBean(AmazonSetup.class);
		System.out.println(AmazonSetup.class);
		amazonSetup.setupDynamoDB();

	}

}
