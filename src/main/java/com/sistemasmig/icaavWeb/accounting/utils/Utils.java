package com.sistemasmig.icaavWeb.accounting.utils;

import javax.crypto.SecretKey;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	
	public static void copyPropertiesIgnoringAndNull(Object source, Object target, String... fieldsToIgnore) {
	    Set<String> ignoreSet = new HashSet<>(Arrays.asList(fieldsToIgnore));

	    for (PropertyDescriptor propertyDescriptor : PropertyUtils.getPropertyDescriptors(source)) {
	        String propertyName = propertyDescriptor.getName();
	        if (!ignoreSet.contains(propertyName)) {
	            try {
	                Object value = PropertyUtils.getProperty(source, propertyName);
	                if (value != null) {
	                    BeanUtils.copyProperty(target, propertyName, value);
	                }
	            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
}
