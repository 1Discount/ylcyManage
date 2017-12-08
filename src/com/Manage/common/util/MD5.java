package com.Manage.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;


public class MD5{

	 /** 
     * 生成md5编码字符串. 
     * @param str 源字符串 
     * @param charset 编码方式 
     * @return 
     */ 
	public static String MD5ToString(String str) {  
		String charset = "utf-8";
        if (str == null)  
            return null;  
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
                'a', 'b', 'c', 'd', 'e', 'f' };  
          
        MessageDigest md5MessageDigest = null;  
        byte[] md5Bytes = null;  
        char md5Chars[] = null;  
        byte[] strBytes = null;  
        try {  
            strBytes = str.getBytes(charset);  
            md5MessageDigest = MessageDigest.getInstance("MD5");  
            md5MessageDigest.update(strBytes);  
            md5Bytes = md5MessageDigest.digest();  
            int j = md5Bytes.length;  
            md5Chars = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte md5Byte = md5Bytes[i];  
                md5Chars[k++] = hexDigits[md5Byte >>> 4 & 0xf];  
                md5Chars[k++] = hexDigits[md5Byte & 0xf];  
            }  
            return new String(md5Chars);  
        } catch (NoSuchAlgorithmException e) {  
            return null;  
        } catch (UnsupportedEncodingException e) {  
            return null;  
        } finally {  
            md5MessageDigest = null;  
            strBytes = null;  
            md5Bytes = null;  
        }  
    }
	
	

	/**
	 * 自定义密钥secretKey
	 * @return
	 */
	public static String secretKey(){
		return "LuBRk9CdgMMiJmZtHGcrQjZjXoew4ahe";
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String pass =  new MD5().MD5ToString("12345678");
		System.out.println(pass);
	/*	String md5 = DigestUtils.md5Hex("123456789");
		String md6 = DigestUtils.md5Hex("123456789");
		System.out.println(md6);
		 MessageDigest md = MessageDigest.getInstance("SHA");//SHA 或者 MD5
		 BASE64Encoder  base = new BASE64Encoder ();
		 String pwdAfter = base.encode(md.digest("123456".getBytes()));
		 System.out.println(pwdAfter);*/
		//25f9e794323b453885f5181f1b624d0b
		//System.out.println(convertMD5(convertMD5("25f9e794323b453885f5181f1b624d0b")));
	}
}

