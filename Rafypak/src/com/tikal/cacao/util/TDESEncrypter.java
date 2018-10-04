package com.tikal.cacao.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class TDESEncrypter implements Encrypter {

	public static final String SK = "sktest";
	
	@Override
	public String encrypt(String message, String secretKey) {
		if (message == null) return null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] plainTextBytes = message.getBytes("utf-8");
		    byte[] buf = cipher.doFinal(plainTextBytes);
		    byte [] base64Bytes = Base64.encodeBase64(buf);
		    String base64EncryptedString = new String(base64Bytes);
		    
		    return base64EncryptedString;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String decrypt(String encryptedText, String secretKey) {
		try {
			if (encryptedText != null) {
				byte[]message = Base64.decodeBase64(encryptedText.getBytes("utf-8"));
			
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
				byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
				SecretKey key = new SecretKeySpec(keyBytes, "DESede");
				
				Cipher decipher = Cipher.getInstance("DESede");
				decipher.init(Cipher.DECRYPT_MODE, key);
				
				byte[] plainText = decipher.doFinal(message);
				
				return new String(plainText, "UTF-8");
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
			return encryptedText;
		} catch (BadPaddingException e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
			return encryptedText;
		}
		return null;
	}

	@Override
	public String doOperation(String message, String secretKey, Operation operation) {
		switch (operation) {
			case ENCRYPT:
				return encrypt(message, secretKey);
			case DECRYPT:
				return decrypt(message, secretKey);
			default:
				break;
		}
		return null;
	}

}
