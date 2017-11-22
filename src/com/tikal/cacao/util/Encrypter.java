package com.tikal.cacao.util;

public interface Encrypter {
	
	String doOperation(String message, String secretKey, Operation operation);

	String encrypt(String message, String secretKey);
	
	String decrypt(String encryptedText, String secretKey);
}
