package com.fibi.data;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fibi.service.VerificationTokenService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	
    @Autowired
    private VerificationTokenService tokenService;
    @Autowired
    private MessageSource messages;
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(user, token);
         
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/users/confirmUser.html?token=" + token;
        String message = "Hey "+user.getFirstName() + ",\n\n Thank you for registering with FIBI. Click the below link to confirm your registration";
         
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        //email.setText(message + "\n\n" + "http://fibi.cfapps.io" + confirmationUrl + "\n\nRegards,\nFIBI Team");
        email.setText(message + "\n\n" + "http://localhost:9090" + confirmationUrl + "\n\nRegards,\nFIBI Team");
        mailSender.send(email);
    }
}