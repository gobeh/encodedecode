package com.example.encdec.services;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is use to encrypt and decrypt URL that is to be send to subscriber
 * it's used by delivery module when resend process.
 * 
 * @author DEVELOPMENT TEAM PUSTIPANDA
 */
public class AesEncrypter {
	Cipher ecipher;
	Cipher dcipher;

	// Iteration count
	int iterationCount = 19;

	/**
	 * constructor to create instance with Secretkey type
	 * 
	 * @param key
	 */
	public AesEncrypter(SecretKey key) {
		try {
			ecipher = Cipher.getInstance("AES");
			dcipher = Cipher.getInstance("AES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (javax.crypto.NoSuchPaddingException e) {
		} catch (java.security.NoSuchAlgorithmException e) {
		} catch (java.security.InvalidKeyException e) {
		}
	}

	/**
	 * constructor to create instance with String type
	 * 
	 * @param passPhrase
	 *            (the String input key must have length = 16)
	 */
	public AesEncrypter(String passPhrase) {
		try {
			// Create the key
			byte[] keyValue = passPhrase.getBytes("UTF8");
			Key key = new SecretKeySpec(keyValue, "AES");
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (javax.crypto.NoSuchPaddingException e) {
		} catch (java.security.NoSuchAlgorithmException e) {
		} catch (java.security.InvalidKeyException e) {
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * method to encrypt string
	 * 
	 * @param str
	 * @return base64-encoded and encrypted string on success
	 */
	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return new String(Base64.encodeBase64(enc));
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * method to decrypt string
	 * 
	 * @param str
	 * @return decrypted string on success
	 */
	public String decrypt(String str) {
		try {
			// Decode base64 to get bytes
			byte[] dec = Base64.decodeBase64(str.getBytes());

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (Exception e) {
		}
		return "";
	}

	public static void main(String[] main) {
		AesEncrypter encrypter = new AesEncrypter("123hJk2738HeysuI");
		String plaintext = "hello";
		String enkrip;
		String dekrip;
		System.out.println("Plaintext : " + plaintext);
		enkrip = encrypter.encrypt(plaintext);
		System.out.println("Encrypttext : " + enkrip);
		dekrip = encrypter.decrypt(enkrip);
		System.out.println("Decrypttext : " + dekrip);

	}
}
