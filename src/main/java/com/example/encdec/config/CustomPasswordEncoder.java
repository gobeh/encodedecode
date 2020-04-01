package com.example.encdec.config;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class CustomPasswordEncoder implements PasswordEncoder {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[]{'T', 'E', 'S', 'T', 'T', 'E', 'S', 'T', 'T', 'E', 'S', 'T', 'T', 'E', 'S', 'T'}; //16 bytes

    public CustomPasswordEncoder() {
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String encodedPwd = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(rawPassword.toString().getBytes());
            encodedPwd = Base64.getEncoder().encodeToString(encVal);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return encodedPwd;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword.toString().equals(decrypt(encodedPassword)))
            return true;

        return false;
    }

    public String decrypt(String encryptedData) {
        String decodedPWD = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            decodedPWD = new String(decValue);

        } catch (Exception e) {

        }
        return decodedPWD;
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() {
        SecretKeySpec key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }
}