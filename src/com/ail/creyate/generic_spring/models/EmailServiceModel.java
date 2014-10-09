package com.ail.creyate.generic_spring.models;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceModel extends BasicModel {
	
	 	public JavaMailSender getJavaMailService() {
	        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

	        javaMailSender.setHost("smtp.gmail.com");
	        javaMailSender.setPort(465);

	        javaMailSender.setJavaMailProperties(getMailProperties());

	        return javaMailSender;
	    }

	    private Properties getMailProperties() {
	        Properties properties = new Properties();
	        properties.setProperty("mail.transport.protocol", "smtp");
	        properties.setProperty("mail.smtp.auth", "true");
	        properties.setProperty("mail.smtp.starttls.enable", "false");
	        properties.setProperty("mail.debug", "false");
	        
            properties.put("mail.smtp.ssl.enable", "true"); 
	        return properties;
	    }
}
