package com.fibi.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fibi.data.User;
import com.fibi.events.OnPasswordResetEvent;
import com.fibi.service.PasswordResetTokenService;
import com.fibi.util.SimpleOTPGenerator;

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
        String message = "Hey "+user.getFirstName() + ",\n\n Here is a one-time verification code to reset your password. "
        		+ "This code ensures that only you can access your account."
        		+ "\n\n Your Verification Code: "+otp;
         
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\n\nRegards,\nFIBI Team");
        mailSender.send(email);
    }
}