package com.fibi.data;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;
 
    @Id
    private String id;
     
    private String otp;
    private User user;
    private Date expiryDate;
 
    public PasswordResetToken() {
        super();
    }
    public PasswordResetToken(String otp, User user) {
        super();
        this.otp = otp;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
     
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public static int getExpiration() {
		return EXPIRATION;
	}
	
	private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }     
}