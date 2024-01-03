package com.xiaohai.common.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author wangchenghai
 * @date 2024/01/03 11:50:49
 */
public class AESUtil {
    private static final String AES_ALGORITHM = "AES";
    private static final String RSA_ALGORITHM = "RSA";
    private static final String CHARSET = "UTF-8";

    public static String encrypt(String input, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHARSET), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(input.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedInput, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHARSET), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedInput);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, CHARSET);
    }

    public static String encryptRSA(String input, String publicKey) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        byte[] encryptedBytes = cipher.doFinal(input.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

//    public static void main(String[] args) {
//        try {
//            String aesKey = "WFHmea3DpZyYY55R"; // 16字节的AES密钥
//            String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCK5+1BIi2F3b6Qr4svao2KK7key1/IZGHpLNtT7CBfxmHftJqULDpR0D/oq86xz5EVJ36JHdKxF4Fs/wi5FYlQWcowIr8BBI+LxTomHRM6VtqyjQapot2zPYyqBlzH+NTFqtC5fp+A5VsvQ9CoXPCUI6GPmCyX/md12FXkBbTgewIDAQAB"; // 公钥字符串
//            String inputText = "fLGyHx4ZOkUul+uDYjf70OKqIWtgm0fuQlu1qwMDXa/INoxSSl9F1SapC2qIxokKmYlPtw17mNJ7ifcrX1ReZ8TsN0L+yopVpgR/FRHF3gM0q+hfQMfCRuwLWQ55W9snUWMdjHUvZ0yXLm3bqpjdFvmaJwPt/kdn1EWuA2BonQ8eMY+61ScBDx31jz0xCVXgHjlfuTBS9Ue7e21+9KXZhO7V+/qBpOLXvQ9IMaU6//rHPxX+RUuXohWl7tA+Wjrz";
//
//            // 使用AES加密
//            String encryptedText = encrypt(inputText, aesKey);
//            System.out.println("Encrypted Text (AES): " + encryptedText);
//
//            // 使用RSA加密
////            String encryptedTextRSA = encryptRSA(inputText, rsaPublicKey);
////            System.out.println("Encrypted Text (RSA): " + encryptedTextRSA);
//
//            // 解密
//            String decryptedText = decrypt(inputText, aesKey);
//            System.out.println("Decrypted Text (AES): " + decryptedText);
//
////            String decryptedTextRSA = decrypt(encryptedTextRSA, aesKey); // 注意：使用AES密钥解密
////            System.out.println("Decrypted Text (RSA): " + decryptedTextRSA);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//        public static void main(String[] args) throws Exception {
//            // 公钥字符串
//            String publicKeyString = "YOUR_PUBLIC_KEY_HERE"; // 你的公钥字符串
//
//            // 要加密的明文
//            String plaintext = "Hello, RSA Encryption!";
//
//            // 将公钥字符串转换为 PublicKey 对象
//            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PublicKey publicKey = keyFactory.generatePublic(keySpec);
//
//            // 使用公钥进行加密
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
//
//            // 将加密后的数据转换为 Base64 字符串
//            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
//
//            System.out.println("Encrypted Text: " + encryptedText);
//        }
    public static void main(String[] args) throws Exception {
        // 密文
        String encryptedText = "oUVeSz2+QyOGGeKvcY98AlJSjQ5An4RemySIJCcHCQYDPicIG/7mhgifAmLsBQ9Z67YZ6pYUxWdgkJ8x+Y2Y2iA5dU9lea0y7LFsyWH1+14gIqQjJ+hgWrYYjievlU56/nyJex3YqwTq8q9gFdmlR4mf+5p2vOKkmCzeaFDGNJDYesVWNXtklcLKhc3k/7M6yYCZC4Nq7b1FW7LC9PWDkKEVij3zhvp5uIGQj1JQFn/b/ttuOHoZ9KeFLWcDwPmvaZVdZ4UShf11NUjbTSYaCYaX7tTg2JRobAfwKL4oOcxu6EtSaEKqbf50KXTZPd8uWlx8wq0n5IkGRndpWIzNCUv2xLVOxIAc5Vcux6np+mUZMHMuOVSkQYYQdWcD6Qh8eFka71c6XPJ5XgmA8VOEnToyAQxAERRnF+8cBUCUa3c="; // 替换为加密后的字符串

        String a="dSmW3JXAtkacZhJ7";

        // 密钥
        String key = a; // 16字节的AES密钥

        // 初始化向量
        String iv = a; // 16字节的初始化向量

        // 将密文和密钥转换为字节数组
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] keyBytes = key.getBytes();
        byte[] ivBytes = iv.getBytes();

        // 创建AES密钥和初始化向量
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        // 使用AES解密
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        // 将解密后的字节数组转换为字符串
        String decryptedText = new String(decryptedBytes, "UTF-8");

        // 将解密后的字节数组转换为明文
//        String decryptedText = new String(decryptedBytes);

        System.out.println("Decrypted Text: " + decryptedText);
    }

}
