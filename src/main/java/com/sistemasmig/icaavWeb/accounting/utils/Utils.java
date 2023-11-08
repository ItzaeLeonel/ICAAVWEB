package com.sistemasmig.icaavWeb.accounting.utils;

import javax.crypto.SecretKey;

import com.google.common.base.Predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {
	
	public static String generateSecretKey(){
		
		try {
			String algorithm = "HMACSHA256"; 
			int keySize = 256; 
			
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(keySize);
			SecretKey secretKey = keyGenerator.generateKey();

			
			String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
			
			return base64Key;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
