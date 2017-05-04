package com.lele.manager.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static String ENCRYPT_TOKEN="d49d3b1da345884e"; //替换成和客户端一样的16位长度
    private static String  IV_KEY = "d49d3b1da345884e"; //替换成和客户端一样的16位长度
    
    public static byte[] key = null;
    public static byte[] iv = null;
    static {
        try {
            key = ENCRYPT_TOKEN.getBytes("UTF-8");
            iv = IV_KEY.getBytes("UTF-8");
        } catch (Exception e) {
            System.out.println("dont't init the AES KEY");
        }
    }
    /*
     * 将字符串AES加密
     * 加密顺序如下：
     * 先AES加密
     * 然后在base64编码
     * 然后去掉base64编码中的空格。
     */
    public static String AESEncrypt(String src) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(AES.key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(AES.iv));
            byte[] encrypted = cipher.doFinal(src.getBytes("UTF-8"));
            String srcEncode =  Base64.encode(encrypted);
            srcEncode = srcEncode.replaceAll("\\s", ""); //\s   A whitespace character: [ \t\n\x0B\f\r]
            //srcEncode = URLEncoder.encode(srcEncode,"UTF-8");
            return srcEncode;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
     * 将字符串AES解密
     * 解密顺序如下：
     * 先AES解密
     * 转换成Strng 返回
     */
    public static String AESDecrypt(String src) {
        try {
            byte[] srcDecode = Base64.decode(src);
            SecretKeySpec skeySpec = new SecretKeySpec(AES.key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(AES.iv));
            byte[] dencrypted = cipher.doFinal(srcDecode);
            return new String(dencrypted,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
     public static void main(String[] argo) {
         // function unit test code
         String key = "123123";
         String enKey = AESEncrypt(key);
         System.out.println(enKey);
         /*
         try {
            System.out.println("encode Key: " + key + " encode is:" + URLEncoder.encode(enKey, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
         System.out.println("enKey: " + enKey + " decode is:" + AESDecrypt(enKey));*/
    	 String str ="Qot+98b7PsTS4jmtnisdyHwC1oX3d0o43ueSTB00NX1mk9KpEDAiwZMCIBb79YOIhqxpMoHD6ySEeWPSI5WzRQUsATE2mg10YW4xlp+Jx6GlJnTpx4UgDFmH/M9/o/YVD1SDb5/Wxl9IUFpvAiONKvwU6BL096tVkOuBXc54Sc3YmPRZQrBXzEG8v4id0Vr/zObaV5W8WtFseLQtCg9kREEFvk40Qi2Yz1Rliv6Khd6y5E1Ba4XlQuU8LF0AJW/VPfCdLTGgG7vS9OHubLqzIZkquixocH4iatYn7epaQBaaBS1vl97YL3rDYqbYndijRGNvrCSBtXv/FP1WvmMIlw+Jzyy8zKgmiKVgIAWxya7rAqqHHkdPdwTwYwuG326nXNUnJvL3kBQokJr+Ub0A9FzaHNioso1sBon7CAzao42lFanUe0RO74KsZP1pFv54oi5XEz6cFK447bUdtDdFwowtxqCkMhshkHQ4oUe5iQMAlOS7PRpgVUY8rXsu51OXGWlTHbhZBs1z9/n0Co8PTfKXMsg1ZtOxPiClHR1V8JNiurfokJVf34XLK6Hnu5NdXy7Kqu5adt6fBPj+iE8NZ98Jw+NDCTFcoBiaxZBwNiO53GBM/inoMnDlSAQfp1mx7LaqkXaYK3JePo+LuVTCzxqCRinSQHt5FxrhrVfVpNrtu7oaIRrKMoynoWm4PhLhTpQ452JnLtwWpTYGREN7Temvp8yCn4aMtASf9yYAtaDepyNTARfGn8wae/BfOE5qaBj/5zCvKWZg2y+cTuo5pgn5Ik5Ma+5uBlC5xJZQieGahqKd2gk77kGtNG7cnjioQ05D/BU02NDwuywAd6DIW+EusbawSunLoYflPWT/KSKW7QPY4MOep/EYqcBRMjpjOhurnO9L+JvHmsRtcGDeZs15QXhex4gb80AGiY5MPb0WU9xNyIoMWm61sufL2zRX";
    	 System.out.println(AES.AESDecrypt(str));
     }
}
