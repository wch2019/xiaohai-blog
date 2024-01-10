package com.xiaohai.common.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author wangchenghai
 * @date 2024/01/03 11:50:49
 */
public class AESUtil {
    private static final String phoneKey = "WEIWAI_KEY_12345";
    private static final String publicKeyStr ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCK5+1BIi2F3b6Qr4svao2KK7key1/IZGHpLNtT7CBfxmHftJqULDpR0D/oq86xz5EVJ36JHdKxF4Fs/wi5FYlQWcowIr8BBI+LxTomHRM6VtqyjQapot2zPYyqBlzH+NTFqtC5fp+A5VsvQ9CoXPCUI6GPmCyX/md12FXkBbTgewIDAQAB";


    /**
     * 使用AES/CBC/NoPadding进行解密，同时移除PKCS7填充。
     *
     * @param data   要解密的Base64编码的加密数据。
     * @param keyStr 加密密钥作为字符串。
     * @return 解密后的字符串。
     * @throws Exception 如果解密失败。
     */
    public static String decrypt(String data, String keyStr) throws Exception {
        // 将密钥字符串转换为字节数组
        byte[] keyBytes = keyStr.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        // 使用密钥作为初始化向量 (IV)
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

        // 初始化用于解密的密码器
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // 解码Base64编码的加密数据
        byte[] encryptedData = Base64.getDecoder().decode(data);

        // 执行解密
        byte[] decryptedData = cipher.doFinal(encryptedData);

        // 手动移除PKCS7填充
        int padLength = decryptedData[decryptedData.length - 1];
        byte[] resultBytes = new byte[decryptedData.length - padLength];
        System.arraycopy(decryptedData, 0, resultBytes, 0, resultBytes.length);

        // 将解密后的字节数组转换为字符串
        return new String(resultBytes, "UTF-8");
    }

    /**
     * 使用AES/CBC/NoPadding进行加密，同时添加PKCS7填充。
     *
     * @param plainText 要加密的明文字符串。
     * @param keyStr    加密密钥作为字符串。
     * @return Base64编码的加密数据。
     * @throws Exception 如果加密失败。
     */
    public static String encrypt(String plainText, String keyStr) throws Exception {
        // 将密钥字符串转换为字节数组
        byte[] keyBytes = keyStr.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        // 使用密钥作为初始化向量 (IV)
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

        // 初始化用于加密的密码器
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        // 将明文字符串转换为字节数组，并进行PKCS7填充
        byte[] paddedPlainText = addPKCS7Padding(plainText.getBytes("UTF-8"));

        // 执行加密
        byte[] encryptedData = cipher.doFinal(paddedPlainText);

        // 将加密数据进行Base64编码
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * 添加PKCS7填充到字节数组。
     *
     * @param input 要填充的字节数组。
     * @return 添加了PKCS7填充的字节数组。
     */
    private static byte[] addPKCS7Padding(byte[] input) {
        int paddingLength = 16 - (input.length % 16);
        byte[] paddedInput = new byte[input.length + paddingLength];
        System.arraycopy(input, 0, paddedInput, 0, input.length);
        for (int i = input.length; i < paddedInput.length; i++) {
            paddedInput[i] = (byte) paddingLength;
        }
        return paddedInput;
    }

    /**
     * 生成指定长度的随机字符串。
     *
     * @param length 随机字符串的长度。
     * @return 生成的随机字符串。
     */
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
        int charactersLength = characters.length();
        StringBuilder randomString = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(charactersLength);
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public static void main(String[] args) throws Exception {
        String phoneKey = "WEIWAI_KEY_12345";
        String publicKeyStr ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCK5+1BIi2F3b6Qr4svao2KK7key1/IZGHpLNtT7CBfxmHftJqULDpR0D/oq86xz5EVJ36JHdKxF4Fs/wi5FYlQWcowIr8BBI+LxTomHRM6VtqyjQapot2zPYyqBlzH+NTFqtC5fp+A5VsvQ9CoXPCUI6GPmCyX/md12FXkBbTgewIDAQAB";
        String privateKeyStr ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIrn7UEiLYXdvpCviy9qjYoruR7LX8hkYeks21PsIF/GYd+0mpQsOlHQP+irzrHPkRUnfokd0rEXgWz/CLkViVBZyjAivwEEj4vFOiYdEzpW2rKNBqmi3bM9jKoGXMf41MWq0Ll+n4DlWy9D0Khc8JQjoY+YLJf+Z3XYVeQFtOB7AgMBAAECgYAtaiSs3fLKSLSMc4hrKUg7e44b3lxjlUZZyTuwVYxEJAVN8at0dH1g0FiExt9eTUHfC7mYNPL0yX2cWEnO0ZbB+tN/racWJ8r7Ybc+yVDpZdN9k7Yv+ziF9QMClX8wpQ0kBDtQj0lJB+trYHGSetbhrCPEKZNvEf5qmJ3S6v8+4QJBAO5g4Ak+d6Q6re721CKLAUdmFg0FVH3iO4N1Eg7L2qq8bSiX8FoIrGwDomVX2kAS5IOflt7gx7sECdiruwTlzEsCQQCVLJ0EHmnre59OoIFL0JrCMSyxR5mKDW9GC8tV3us5KmK+RzV532I0iuvRWqaDgMONo5s00I4RmN49np/Y9j6RAkEArDGkhGjwadzNefopAQ7ijJ3svdkk0I5UqUAvHCsbuQvF2nXu9EqhqqDxdT2B9ZjwIzhr9nHvNWPUbQSYYxBQgwI/G/hBeIQ7V+vV8uglVhHG8qJFvvet0jSyqG+NsIwbjpxj50lyvWclArUJgjd9ASsjf2strrlr6eC/ys3zKrsBAkEAknOfjk0JPwnMDtKYkkRBPFi24IyAfH+TdxUDxhowhgLIdJoNvd7XQplMgwH+v+4jGyvZALT6sJtvX/FLt42zKA==";

        String data = "{\"method\": \"collection_query_relation_method\",\"app_key\": null,\"session\": \"79aeaa73199540469f0b158edc27ce95\",\"v\": \"1.0\",\"partner_id\": null,\"timestamp\": \"2024-01-04 17:15:05\",\"data\": {\"currentPage\": 1,\"pageSize\": 15,\"loanNo\": \"2998009693063571\",\"tabFlag\": \"sys\",\"serialno\": \"card\",\"tabName\": \"sys\",\"caseId\": \"card2998009693063571\"}}";

        String keyStr = generateRandomString(16);
        System.out.println("加密key: " +keyStr);
        //         data = "EbYoEU9Wzs2IS0wasuGd0cscyJDuu+Lw4C/zJws43rwtp3OxjYl968+o/y6AF4pKstYokiVWcXI7LI99Hj3ra45FxxHBVPyZIG8DJft1X2l9ADoJ9fXd/kxxAoSsbwF2+L/j1CGicnGNUy1I36JCRJhQMhlUEVj6TP4PdBk+KwUSSGpZrr653/GRSwtznRAp0gpmsrVtS06+pA/3tU2w989bsqeaVOoiH2VQpyxbj0U7O27kfnU7g6FtiRkC4bpr8PlG+NFkc85xYjnEniNotO7vYblr+BW+//IxJNagW3dUKT08dmER05CfFvSz5QJptQb+4tB3phEdsx+sY1irF4Nqth4Ivrk/dR+BTRGR6rlvUjYv722m9w95DVYFYIo2ccQqIb0W4V6u1/GVrSTSz6K/2OTDwurc0BTFvcnhPv6bbEKjxrdNH/Mqemv3vV/LGmuD8l1QxJ8QC/PkcI4DvubibszFoGFipVpDmQcEsXSvqw508qDtqE+XDOIRx/Hmo291kDwtG8UGCRLkQQRGeRpcUpvC4XBhdgkKl5ZIuKmSvb7PlIEwd/3XswFY6EJEB4CrvurwT8rdIOUPpYfxnGkdRUn96MAsCsVyDjoeoL0lOvemjfia2RcI9vlaLe3Kx92QX4uasCdSHOF5wkxzupw86Uu3Sisgn/dyDUOdjPZz1d0/W3RBWv18JkjtNzc8sgoWd3c1kRcX+vvGEH1ZNHpzqhPJJ+5ye8TIJL1v4EVrrYPXteT0DjE3nEhDfE3YniOAynwKkmm1hcmTwYs+HfJpMxVN6EsdmCqW+LLMFTR9OquU/gz81FE+hlAZRUmoMggkP62BrtDNn/PTo13SbrJyfwaxhFV5cLt/hszRKg87bYw8XPGeJFpLejGinPGBoEHdV0+92UJnxAWNHJTMsA7n3AcH921aifwneesOLNP9Ew1AWvJcak3+5PJBJ4qliaGp6+2UN2g5KFhNlrOUo9r6ayOlGd59Aa8MWg6sx3ZAWuYSNMahZcUfarNQaDDaQL29Qy8WzjWB75oB8FoZmTb54+VmDc5+cSxaTCC5lysBYLg7d9zKWp8iNCfGoWQ6NEhRCJ5KR9oxAvgpnuhWpkq5906knWDNL9G2hQc5cDQTLNtmAZ7dE9LkyK8sV7LOFG3rvBaKGExD+BY+iAXpcGEVCdmfbM01MzZ0igzsYEklPRrhbOmEM6eJIhwza1P1ZKNCULE+9oqI47h++/4IsjhvQNWZML2A6LsMijHwW/unRRx+xyy1mHQ3dVcX0QF1qsEJCkMjpN1LiIqJXYWJfGLTyDHZM5GNjTbL5SN2+DgIp5OOtmDx0zeWfGZkdqXq2ppwpuThb+FjidS3c45sd6LQtZVxVKZgZEXLj/L72KTNJWmyKLCMWNL+g+JWgO0VE/PEsY4w/xB/PUU6mLxQqsHlUQSrMgzRAR8LszvpsmzZ6DtBKSs5zOkUj9RlYa6SDDuvj3GcL9BTOHMYLF/u5NpgJ+HCDcF4dDPn30B4hjyM/hK+skBFwzaTyt7C78V119HtWcAnAJk8XW/xZg4sYrZGXnUBqbq4ThuMLfkhVmDDzy9/yLK36auBfYDxr2w8w6L9SVc+aZcnbn+tPf7Atm1TrsMHkK1k/Y1aGWG7XKsoQMfWhVjgvbJq4hSs0DO0c8CEuhyL/Pb9aOFQzfZo+F3TNJD2OIEw5qGpVIhosGbQ9VzYQuWSbt6tmkfBrC05s2u5UJbBOzbrPThaoUC0M9EGJAx5XXnp3EDiIAjHbYzA6Fx3KmGGQOlhbhIr04LsJ/0hr/v92suhPsGo4Jm5hvTwcxn30q6o0UkBXNY8zpIstTPu7PqMDZMs9cUC1vacRBWJwjb4IV9fBrUAzz80bqK2XyHIBaJ0uUla9VbSSNsglyLKhTkXEw9UEX+vvztU2JOD1C1drCpbzN2imLT4sdf0w/8/aXNcPIDdDV89YBN88doBxza8U0/USutdI2RRp2vrQZVu6YH7ioPemoDiybLDcO9KWP4ObcuSDE1VERSsHz9hZAEwxt2UIckOiTnxq1z2lnMP5Usi7h3Uh1VQjhMHtW3U/7/QYSPCkpC1htfLv169noBAAQLslac5KTy4D3KlWtRhXD0szNwS2WyL8NHbn/zJWk6MGANy/agnxcKES6kqzuP22HW26+Q4mqn7kVlOmBrb1ZNOmUrcsLx8/n7aovNFTpFf9fGCP1d2+w15319YihUed359R6H5V+A/qNWy5RW+X7x1X07F0ECw/ahhNihFcjcpnUKGxjjgSNf7GZ5FC9oLisfg9tazz0I3BOHXGZOag4xBQXG8uqllhgnwwsBzaj01qT3jzuJquZ5Czc22GmGx6fFFDbCFHb95RK16pHeB+p7yo25N1apUlJ90U+XVYsEz69Kq07PHmn4WiJj1WIVHNvGNnvxlN6Rl08nuLQAk+SGWZoRQ/OtXa3YYEoplK1N5cMLZSmB/euDvRILiNn9VusTCk6Y1XSO1gwU7nyv1itTya/giTvYxTPzw6FCvY8vEP9iqY8u3G6fWUumqmbTMJcAG86SGdxGo6LdzrkmLhtxf/bfC3GXt1y+bGdhmantd1L4UD4tKLoBUHgCei8lnaKYp5qdwewZnQjkPpkL1+WGY6YErRuicyGAcBlxYdUqwsXn9qZv+eweHdqgzUzRFe0Pgn5CeuvinN3O/8IgHWm/3MCPKc8K3vRGumF3De3+JztmpZz1JXRkxil1NzKvvaHrmfqw2HORDj+4NuwapIdO/YCiQaeu2lnlLXs0v9remmss/2cRwlQo=";
        //         keyStr = "6SsPMeAJkHQEnMjD";
        //        String data = "oUVeSz2+QyOGGeKvcY98AlJSjQ5An4RemySIJCcHCQYDPicIG/7mhgifAmLsBQ9Z67YZ6pYUxWdgkJ8x+Y2Y2iA5dU9lea0y7LFsyWH1+14gIqQjJ+hgWrYYjievlU56/nyJex3YqwTq8q9gFdmlR4mf+5p2vOKkmCzeaFDGNJDYesVWNXtklcLKhc3k/7M6yYCZC4Nq7b1FW7LC9PWDkKEVij3zhvp5uIGQj1JQFn/b/ttuOHoZ9KeFLWcDwPmvaZVdZ4UShf11NUjbTSYaCYaX7tTg2JRobAfwKL4oOcxu6EtSaEKqbf50KXTZPd8uWlx8wq0n5IkGRndpWIzNCUv2xLVOxIAc5Vcux6np+mUZMHMuOVSkQYYQdWcD6Qh8eFka71c6XPJ5XgmA8VOEnToyAQxAERRnF+8cBUCUa3c="; // 替换为加密后的字符串
        //
        //        String keyStr="dSmW3JXAtkacZhJ7";
        // 使用公钥加密
        String encryptedText = RSAUtil.encrypt(keyStr, publicKeyStr);
        System.out.println("key加密结果: " + encryptedText);
        // 使用私钥解密
        String decrypt = RSAUtil.decrypt(encryptedText, privateKeyStr);
        System.out.println("key解密结果: " + decrypt);
        String encryptedData  = encrypt(data, keyStr);
        System.out.println("加密结果: " + encryptedData );
        String a="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";
        String result = decrypt(a,keyStr);
        System.out.println("解密结果: " + result);
    }

}
