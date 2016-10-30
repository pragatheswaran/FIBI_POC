package com.fibi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
/**
 * 
 * {@link FibiCoreUtils} provides utility methods for generate password hash
 *
 */
public class FibiCoreUtils {

	private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int SALT_BYTE_SIZE = 24;
    private static final int HASH_BYTE_SIZE = 24;
    private static final int PBKDF2_ITERATIONS = 1000;
/**
 * 	
 * @param text used to get MessageDigest by using SHA-1 Algorithm. 
 * @return
 * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException
 */
	public static String getSHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[20];
		md.update(text.getBytes(), 0, text.length());
		sha1hash = md.digest();
		return (byteToHexString(sha1hash));
	}	
	
	/**
	 * 
	 * @param bytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getSHA1(byte[] bytes)throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				byte[] sha1hash = new byte[20];
				md.update(bytes, 0, bytes.length);
				sha1hash = md.digest();
				return (FibiCoreUtils.byteToHexString(sha1hash));
	} 
	/**
	 * This methos used to convert byte array to hex string
	 * @param bytes
	 * @return
	 */
	private static String byteToHexString(byte []bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++){
		  int v = bytes[i] & 0xff;
		  if (v < 16) {
		    sb.append('0');
		  }
		  sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}
	/**
	 * 
	 * This method used to generate hash for password using salt
	 * @param password 
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
    public static String createHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	 byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
    	 String generatedHash  = ""+PBKDF2_ITERATIONS ;
    	 if(salt!=null){
    		 generatedHash = generatedHash + ":" + byteToHexString(salt);
    	 }
    	 generatedHash = generatedHash + ":" + byteToHexString(hash);
    	 generatedHash = generatedHash.trim();
         return generatedHash;
    }
    /**
     * This method used to generate salt 
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] generateSalt() throws NoSuchAlgorithmException {
    	SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);  
        return salt;
    }
    
   /**
    * 
    * @param password
    * @param salt
    * @param iterations
    * @param bytes
    * @return
    * @throws NoSuchAlgorithmException
    * @throws InvalidKeySpecException
    */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)  throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = null;
        if(salt!=null){
        	spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        }else{
        	spec = new PBEKeySpec(password);
        }
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded(); 
    }
}
