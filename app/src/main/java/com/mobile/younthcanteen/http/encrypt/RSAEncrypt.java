/**
 * Copyright (c) 2016,TravelSky. 
 * All Rights Reserved.
 * TravelSky CONFIDENTIAL
 * 
 * Project Name:demo
 * Package Name:rsaEncrypt1
 * File Name:RSAEncrypt.java
 * Date:2016年12月2日 上午11:28:09
 * 
 */
package com.mobile.younthcanteen.http.encrypt;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


 /**
 * ClassName: RSAEncrypt <br/>
 * Description: RSA加密解密算法 <br/>
 * Date: 2016年12月2日 上午11:28:09 <br/>
 * <br/>
 * 
 * @author wx(xuwangs@isoftstone.com)
 * 
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息<br/>
 * 
 */

public class RSAEncrypt {
     private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyGKhtJYp8SRiJ6Jc6/8ZghKi0RM+8p1JLxSC6s8f5m+yCSMmWT0cd9jpdUz11lc3OS9F3QYZfiEG1jfcs0MXBcd/w+QDGAFkMRsoKNbgdKkpzC8MILAEOwLILjGXLfCt8nqZn26IpHyQ7FwQyavirPOGjNhqT7JzqNSF0NI+SzQIDAQAB";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /** 
     * 字节数据转字符串专用集合 
     */  
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',  
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
  
    /** 
     * 随机生成密钥对 
     */  
    public static Map<String,String> genKeyPair() {  
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = null;  
        try {  
            keyPairGen = KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        // 初始化密钥对生成器，密钥大小为96-1024位  
        keyPairGen.initialize(1024,new SecureRandom());  
        // 生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        // 得到私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        Map<String,String>  map = new HashMap<String, String>();
        map.put("privateKey",privateKey.toString());
        System.out.println("得到私钥 :");
        System.out.println(privateKey);
        // 得到公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        map.put("publicKey",publicKey.toString());
        System.out.println("得到公钥 :");
        System.out.println(publicKey);
        return map;
    }

    /** 
     * 从文件中输入流中加载公钥 
     *  
     * @throws Exception
     *             加载公钥时产生的异常 
     */  
    public static String loadPublicKeyByFile(String path) throws Exception {  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(path  
                    + "/publicKey.keystore"));  
            String readLine = null;  
            StringBuilder sb = new StringBuilder();  
            while ((readLine = br.readLine()) != null) {  
                sb.append(readLine);  
            }  
            br.close();  
            return sb.toString();  
        } catch (IOException e) {  
            throw new Exception("公钥数据流读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥输入流为空");  
        }  
    }  
  
    /** 
     * 从字符串中加载公钥 
     *  
     * @param publicKeyStr 
     *            公钥数据字符串 
     * @throws Exception 
     *             加载公钥时产生的异常 
     */  
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为空");  
        }  
    }  
  
    /**
     * 公钥加密过程
     *
     * @param plainTextData
     *            明文数据
     * @return
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt(byte[] plainTextData)  {
        Cipher cipher = null;
        try {
            // 这里不能直接使用RSA。必须使用RSA/ECB/PKCS1Padding。否则服务器无法解密
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, loadPublicKeyByStr(PUCLIC_KEY));
            int inputLen = plainTextData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(plainTextData, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return Base64Utils.encode(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }

     /**
      * 公钥解密过程
      *
      * @param data
      *            密文数据
      * @return 明文
      * @throws Exception
      *             解密过程中的异常信息
      */
     public static String decrypt(String data){
         byte[] cipherData = Base64Utils.decode(data);
         Cipher cipher = null;
         try {
             // 这里不能直接使用RSA。必须使用RSA/ECB/PKCS1Padding。否则服务器无法解密
             cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
             cipher.init(Cipher.DECRYPT_MODE, loadPublicKeyByStr(PUCLIC_KEY));
             int inputLen = cipherData.length;
             ByteArrayOutputStream out = new ByteArrayOutputStream();
             int offSet = 0;
             byte[] cache;
             int i = 0;
             // 对数据分段解密
             while (inputLen - offSet > 0) {
                 if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                     cache = cipher.doFinal(cipherData, offSet, MAX_DECRYPT_BLOCK);
                 } else {
                     cache = cipher.doFinal(cipherData, offSet, inputLen - offSet);
                 }
                 out.write(cache, 0, cache.length);
                 i++;
                 offSet = i * MAX_DECRYPT_BLOCK;
             }
             byte[] decryptedData = out.toByteArray();
             out.close();
             return new String(decryptedData);
         } catch (Exception e) {
             return null;
         }
     }

     /**
      * 从文件中加载私钥
      *
      * @return 是否成功
      * @throws Exception
      */
     public static String loadPrivateKeyByFile(String path) throws Exception {
         try {
             BufferedReader br = new BufferedReader(new FileReader(path
                     + "/privateKey.keystore"));
             String readLine = null;
             StringBuilder sb = new StringBuilder();
             while ((readLine = br.readLine()) != null) {
                 sb.append(readLine);
             }
             br.close();
             return sb.toString();
         } catch (IOException e) {
             throw new Exception("私钥数据读取错误");
         } catch (NullPointerException e) {
             throw new Exception("私钥输入流为空");
         }
     }

     public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
             throws Exception {
         try {
             byte[] buffer = Base64Utils.decode(privateKeyStr);
             PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
             return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
         } catch (NoSuchAlgorithmException e) {
             throw new Exception("无此算法");
         } catch (InvalidKeySpecException e) {
             throw new Exception("私钥非法");
         } catch (NullPointerException e) {
             throw new Exception("私钥数据为空");
         }
     }

    /**
     * 私钥加密过程
     *
     * @param privateKey
     *            私钥
     * @param plainTextData
     *            明文数据
     * @return
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//            byte[] output = cipher.doFinal(plainTextData);
            int inputLen = plainTextData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(plainTextData, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();


            return encryptedData;
//            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey
     *            私钥
     * @param cipherData
     *            密文数据
     * @return 明文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);


//            byte[] output = cipher.doFinal(cipherData);
            int inputLen = cipherData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(cipherData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(cipherData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();

            return decryptedData;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /** 
     * 字节数据转十六进制字符串 
     *  
     * @param data 
     *            输入数据 
     * @return 十六进制内容 
     */  
    public static String byteArrayToString(byte[] data) {  
        StringBuilder stringBuilder = new StringBuilder();  
        for (int i = 0; i < data.length; i++) {  
            // 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移  
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);  
            // 取出字节的低四位 作为索引得到相应的十六进制标识符  
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);  
            if (i < data.length - 1) {  
                stringBuilder.append(' ');  
            }  
        }  
        return stringBuilder.toString();  
    }  
}  