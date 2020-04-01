package com.example.encdec.services;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RinjEncrypter {
	private static final Logger LOGGER = LoggerFactory.getLogger(RinjEncrypter.class);

	Cipher ecipher;
	// Cipher dcipher;

	private String iv = "fedcba9876543210";
	private String key = null;
	private SecretKeySpec keySpec = null;
	private IvParameterSpec ivSpec = null;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public RinjEncrypter(String key) {
		try {
			this.key = key;

			// Make sure the key length should be 16
			int len = this.key.length();
			if (len < 16) {
				int addSpaces = 16 - len;
				for (int i = 0; i < addSpaces; i++) {
					this.key = this.key + " ";
				}
			} else {
				this.key = this.key.substring(0, 16);
			}
			this.keySpec = new SecretKeySpec(this.key.getBytes(), "AES");
			this.ivSpec = new IvParameterSpec(iv.getBytes());
			this.ecipher = Cipher.getInstance("AES/CBC/NoPadding");
		} catch (javax.crypto.NoSuchPaddingException e) {
		} catch (java.security.NoSuchAlgorithmException e) {
		}
	}

	/**
	 * Bytes to Hexa conversion
	 * 
	 * @param data
	 * @return
	 */
	public String bytesToHex(byte[] data) {
		if (data == null) {
			return null;
		} else {
			int len = data.length;
			String str = "";
			for (int i = 0; i < len; i++) {
				if ((data[i] & 0xFF) < 16)
					str = str + "0"
							+ Integer.toHexString(data[i] & 0xFF);
				else
					str = str + Integer.toHexString(data[i] & 0xFF);
			}
			return str;
		}
	}

	/**
	 * Hexa to Bytes conversion
	 * 
	 * @param str
	 * @return
	 */
	public byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(
						str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	/**
	 * Encrpt the goven string
	 * 
	 * @param plainData
	 * @throws Exception
	 */
	public String encrypt(String plainData) throws Exception {

		// Make sure the plainData length should be multiple with 16
		int len = plainData.length();
		int q = len / 16;
		int addSpaces = ((q + 1) * 16) - len;
		for (int i = 0; i < addSpaces; i++) {
			plainData = plainData + " ";
		}

		this.ecipher.init(Cipher.ENCRYPT_MODE, this.keySpec, this.ivSpec);
		byte[] encrypted = ecipher.doFinal(plainData.getBytes());

		return bytesToHex(encrypted);
	}

	/**
	 * Decrypt the given excrypted string
	 * 
	 * @param encrData
	 * @throws Exception
	 */
	public String decrypt(String encrData) throws Exception {
		this.ecipher.init(Cipher.DECRYPT_MODE, this.keySpec, this.ivSpec);
		byte[] outText = this.ecipher.doFinal(hexToBytes(encrData));

		String decrData = new String(outText).trim();
		return decrData;
	}

	public static void main(String[] args) throws Exception {
		RinjEncrypter c = new RinjEncrypter("AIS_UIN");
		String encrStr = c.encrypt("puskom");
		System.out.println("Str : puskom");
		System.out.println("Encrypted Str :" + encrStr);
		String decrStr = c.decrypt("33565ecc56de4da0096b2690e6c7dd10");
		System.out.println("Decrypted Str :" + decrStr);
	}
}
