package com.xiaohai.common.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {
        String data = "PtjZwPbaBrYA6nBbikW0Mw==";
        String keyStr = "WEIWAI_KEY_12345";

        byte[] keyBytes = keyStr.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        byte[] ivBytes = keyBytes;
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] encryptedData = Base64.getDecoder().decode(data);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        // Remove PKCS7 padding manually
        int padLength = decryptedData[decryptedData.length - 1];
        byte[] resultBytes = new byte[decryptedData.length - padLength];
        System.arraycopy(decryptedData, 0, resultBytes, 0, resultBytes.length);

        String result = new String(resultBytes, "UTF-8");
        System.out.println("Result: " + result);
    }
}
