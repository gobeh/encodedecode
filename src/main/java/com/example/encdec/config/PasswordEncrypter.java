package com.example.encdec.config;

import com.example.encdec.dao.PasswordEncryptionMethodDao;
import com.example.encdec.entity.system.PasswordEncryptionMethod;
import com.example.encdec.services.AesEncrypter;
import com.example.encdec.services.Common;
import com.example.encdec.services.DesEncrypter;
import com.example.encdec.services.RinjEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncrypter implements PasswordEncoder {
    private Boolean isNew; /* Default is using the old pass method */

    @Autowired
    private static DesEncrypter desEncrypter;

    @Autowired
    private static AesEncrypter aesEncrypter;

    @Autowired
    private PasswordEncryptionMethodDao passwordEncryptionMethodDao;

    /* return only number and alphabet char for the encrypted text */
    @Autowired
    private static RinjEncrypter rinjEncrypter;

    public static String DES = "DES";
    public static String AES = "AES";
    public static String RINJ = "DES";


    public PasswordEncrypter() {
        this.isNew = false;
    }

    public PasswordEncrypter(Boolean isNew) {
        this.isNew = isNew;
    }

    public String encrypt(String str) {
        return encryptByOldMethod(str);
    }

    public String decrypt(String str) {
        return decryptByOldMethod(str);
    }

    private Boolean isUsedNewMethod() {
        PasswordEncryptionMethod pem = passwordEncryptionMethodDao.findFirstByAktifTrueOrderByIdAsc();

        if (pem == null)
            return false;
        return true;
    }

    public String encryptByNewMethod(String str) {
        /* Take data from DB */
        /* Check Method and Key */
        /* Encrypt with those method and key */

        /* EXAMPLE */
        aesEncrypter = new AesEncrypter(Common.AES_PASS_PHRASE);
        return aesEncrypter.encrypt(str);

        // return "";
    }

    public String decryptByNewMethod(String str) {
        /* Take data from DB */
        /* Check Method and Key */
        /* Decrypt with those method and key */

        /* EXAMPLE */
        aesEncrypter = new AesEncrypter(Common.AES_PASS_PHRASE);
        return aesEncrypter.decrypt(str);

        // return "";
    }

    public String encryptByOldMethod(String str) {
        desEncrypter = new DesEncrypter(Common.DES_PASS_PHRASE);
        return desEncrypter.encrypt(str);
    }

    public String decryptByOldMethod(String str) {
        desEncrypter = new DesEncrypter(Common.DES_PASS_PHRASE);
        return desEncrypter.decrypt(str);
    }

    public static Boolean syaratKunci(String metode, String kunci) {
        if (metode.equals(AES) && kunci.length() != 16)
            return false;
        if ((metode.equals(DES) || metode.equals(RINJ)) && kunci.length() <= 0)
            return false;

        return true;
    }

    @Override
    public String encode(CharSequence str) {
        return encryptByOldMethod(str.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword.toString().equals(decrypt(encodedPassword)))
            return true;

        return false;
    }

}
