package com.xxf.common;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;

@Slf4j
public class AES {

    public static boolean initialized = false;

    /**
     * AES解密
     *
     * @param content
     * @param keyByte
     * @param ivByte
     * @return
     * @throws InvalidAlgorithmParameterException
     */
    public byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key sKeySpec = new SecretKeySpec(keyByte, "AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
            return cipher.doFinal(content);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    /**
     * 生成iv
     *
     * @param iv
     * @return
     * @throws Exception
     */
    public static AlgorithmParameters generateIV(byte[] iv) throws NoSuchAlgorithmException, InvalidParameterSpecException {
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
}