package com.fibi.data;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fibi.service.PasswordResetTokenService;

@Component
public class PasswordResetListener implements ApplicationListener<OnPasswordResetEvent> {
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void onApplicationEvent(OnPasswordResetEvent event) {
        this.resetPassword(event);
    }
 
    private void resetPassword(OnPasswordResetEvent event) {
        User user = event.getUser();
		String otp = SimpleOTPGenerator.random(4);

		passwordResetTokenService.createPasswordResetToken(user, otp);
         
        String recipientAddress = user.getEmail();
        String subject = otp + " - FIBI verification code for password change";
        //String confirmationUrl = event.getAppUrl() + "/users/confirmUser.html?token=" + token;
        String message = "Hey "+user.getFirstName() + ",\n\n Here is a one-time verification code to reset your password. "
        		+ "This code ensures that only you can access your account."
        		+ "\n\n Your Verification Code: "+otp;
         
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        //email.setText(message + "\n\n" + "http://fibi.cfapps.io" + confirmationUrl + "\n\nRegards,\nFIBI Team");
        email.setText(message + "\n\nRegards,\nFIBI Team");
        mailSender.send(email);
    }
}