package com.xiaohai.common.utils.encrypt;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    public static String encrypt(String input, String publicKeyStr) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedInput, String privateKeyStr) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedInput);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            // 生成密钥对，获取公钥和私钥字符串
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

//            String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String publicKeyStr ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCK5+1BIi2F3b6Qr4svao2KK7key1/IZGHpLNtT7CBfxmHftJqULDpR0D/oq86xz5EVJ36JHdKxF4Fs/wi5FYlQWcowIr8BBI+LxTomHRM6VtqyjQapot2zPYyqBlzH+NTFqtC5fp+A5VsvQ9CoXPCUI6GPmCyX/md12FXkBbTgewIDAQAB";
//            String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            String privateKeyStr = "JS5PdQyeJ62Y4WGz";

            String inputText = """
                    JS5PdQyeJ62Y4WGz
                    """;

            // 使用公钥加密
            String encryptedText = encrypt(inputText, publicKeyStr);
            System.out.println("Encrypted Text: " + encryptedText);

            // 使用私钥解密
            String decryptedText = decrypt(encryptedText, privateKeyStr);
            System.out.println("Decrypted Text: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
