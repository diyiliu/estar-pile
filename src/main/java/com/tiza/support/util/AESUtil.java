package com.tiza.support.util;
//package com.gofun.common.power.telaidian;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.netty.handler.codec.base64.Base64Decoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/*******************************************************************************
 * AES加解密算法
 * 2016.07.22
 * @author 王学明
 * aes 128位 cbc 算法
 * HTML的&lt; &gt;&amp;&quot;&copy;&nbsp;分别是<，>，&，"，©;空格的转义字符
 */

public class AESUtil {

    // 加密
    public static String Encrypt(String sSrc, String sKey, String ivStr) throws GeneralSecurityException {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度1234567890123456 
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        // sSrc= escapeChar(sSrc);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());

        String str = new BASE64Encoder().encode(encrypted);
        str = str.replaceAll("\r\n", "");
        str = str.replaceAll("\n", "");

        return str;//new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey, String ivStr) throws GeneralSecurityException {
        // 判断Key是否正确
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }

        byte[] raw = sKey.getBytes(Charset.forName("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = Base64.getDecoder().decode(sSrc);//先用base64解密
        byte[] original = cipher.doFinal(encrypted1);

        return new String(original, Charset.forName("UTF-8"));
    }


    /*
     * escapeChar 字符转换 
     * 加密前分别把<，>，&，"，© 的转义字符 转换成 &lt; &gt;&amp;&quot;&copy;
     * 
     
    private static String  escapeChar(String beforeEncryptString ){
    	String escapeStr=beforeEncryptString;
    	escapeStr=escapeStr.replaceAll("<", "&lt;");
    	escapeStr=escapeStr.replaceAll(">", "&gt;");
    	escapeStr=escapeStr.replaceAll("&", "&amp;");
    	escapeStr=escapeStr.replaceAll("\"", "&quot;");
    	escapeStr=escapeStr.replaceAll("©", "&copy;");
    	escapeStr=escapeStr.replaceAll(" ", "&nbsp;");
    	
		return escapeStr;
    	
    }
    * */
    /*
     * unEscapeChar 反向字符转换 
     * 解密后分别把&lt; &gt;&amp;&quot;&copy; 的转义字符 转换成  <，>，&，"，©
     *  
    
    private static String  unEscapeChar(String beforeDecryptString ){
    	String unEscapeStr=beforeDecryptString;
    	unEscapeStr=unEscapeStr.replaceAll( "&lt;","<");
    	unEscapeStr=unEscapeStr.replaceAll( "&gt;",">");
    	unEscapeStr=unEscapeStr.replaceAll( "&amp;","&");
    	unEscapeStr=unEscapeStr.replaceAll( "&quot;","\"");
    	unEscapeStr=unEscapeStr.replaceAll( "&copy;","©");
    	unEscapeStr=unEscapeStr.replaceAll( "&nbsp;"," ");
    	
		return unEscapeStr;
    	
    }
 * */
    public static void main(String[] args) throws Exception {
        /*
         * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
         * 此处使用AES-128-CBC加密模式，key需要为16位。
         */
        String cKey = "1234567890abcdef";
        // 需要加密的字串
        String cSrc = "1234567890'[]:";
        String ivStr = "1234567890abcdef";
        cSrc = "{\"OperatorID\": \"123456789\",\"OperatorSecret\": \"1234567890abcdef\"}";
        // cSrc="示例：{\"total\":1,\"stationStatusInfo\":{\"operationID\":\"123456789\",\"stationID\":\"111111111111111\",\"connectorStatusInfos\":{\"connectorID\":1,\"equipmentID\":\"10000000000000000000001\",\"status\":4,\"currentA\":0,\"currentB\":0,\"currentC\":0,\"voltageA\":0,\"voltageB\":0,\"voltageC\":0,\"soc\":10,}";
        System.out.println(cSrc);
        // 加密
        long lStart = System.currentTimeMillis();
        // String enString = AESUtil.Encrypt(cSrc, cKey,ivStr);
        String enString = "uxeKP0ezR5yL8xSg4/ZCDh/N91/u86NXFxd2DrwZVW8zCPYcpl59Twz/yQZ3RaO4rDDrGmkvQignmNEJ+k4PG+WIMHC0wTobFh0VgHrOuY0wlfE19edZQlwuMv/6n+lgpsX0UbKGotjLSoJJUwDcOk458BwW49Pj3TBQmmIW/uAnOgrIQYUbWB0AKFlY602MZ7E4PEAvwaQbfNGYCfYHqBdf4RH61kK0l+qZouFGYr34RxC6jhk1HM5MIydDaHrb";
        System.out.println("加密后的字串是：" + enString);
        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");
        // 解密
        lStart = System.currentTimeMillis();
        //AESUtil.Decrypt("DHVWF+8xRIfU7nUCNQdLaGF15VaMZWtNcwaqeumUPe/ok9zgSkR0pbOJUmYYQs7ZFMN7GhLB1ywEN3kb1gH4z+Mc2Z4rQe8Xa42LrmkDRvwwosmVMuR+mbLFCG+Xf5unkRO6JJx1PiTAxAB6oyWqUmbOKskK81LqpWBU5fKnBZwXo3jv2hnKItwCODYw+B+Pg+0IzZ5ye5cKcwz99NO5//H2gU0scZhn+rl8Jcktbm42TVklnxdzG/aw200H2z9ugpB1q2X0sGAi55SQH3DbLpWb5oQE5vy0As7lje4e+4dE8vbLIR0dMw8/lA9cBPYRO2WOkH6SFwFUyi+IishP8j+mzEcfoyAOIUSh5G/5VYqlYu1zlVUsYCHWu7MTE1Gr55SicHZQxl5KHgmgFBw8OQl8DerA++D8vswR8eiRNcXR2MQmNXYarCmZ7Kuc6mRSbrSk2cImOZAywVIo6MpBSu/u0BINysS3S7Ni1LB6zqAu3Ly0yNbbxzz+ZpHjmAM+MMsx4/K6LtG1rhiW8iE3bbGOLJqu9njLFVLQtKXrVsUnF4b1FWuIADG3FBCXqcdyTTTj5vNwI2xxFm/zq5lvJUKUlcFPvYSwBQFsjKHZnl8=", cKey);
        String DeString = AESUtil.Decrypt(enString, cKey, ivStr);
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
    }
}