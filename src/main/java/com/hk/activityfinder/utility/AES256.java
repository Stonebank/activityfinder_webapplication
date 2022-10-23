package com.hk.activityfinder.utility;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AES256 {

    private static final String SECRET_KEY = String.valueOf(generateRandomKey(((int) (Math.random() * 128))));
    private static final String SALT = "a12bc34de56fg";

    private static final byte[] IV = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    private static final IvParameterSpec ivSpec = new IvParameterSpec(IV);

    public static String encrypt(String password) {
        try {
            var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            var spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            var temp = factory.generateSecret(spec);
            var secretKey = new SecretKeySpec(temp.getEncoded(), "AES");
            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            //TODO error message
            return null;
        }
    }

    public static String decrypt(String password) {
        try {
            var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            var spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            var tmp = factory.generateSecret(spec);
            var secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            var cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
        } catch (Exception e) {
            //TODO: Log error message
            return null;
        }
    }

    private static SecretKeySpec generateRandomKey(int keySize) {
        var randomKeyBytes = new byte[keySize / 8];
        var secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomKeyBytes);
        return new SecretKeySpec(randomKeyBytes, "AES");
    }

}
