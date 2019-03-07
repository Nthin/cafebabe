package com.xxf.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxf.entity.CafeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

@Slf4j
public class WXCore {

    private static final String WATERMARK = "watermark";
    private static final String APPID = "appid";

    private static ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    /**
     * 解密数据
     *
     * @return
     * @throws Exception
     */
    public static String decrypt(String appId, String encryptedData, String sessionKey, String iv) {
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(WxPKCS7Encoder.decode(resultByte));
                JsonNode root = mapper.readTree(result);
                String decryptAppId = root.get(WATERMARK).get(APPID).asText();
                if (!appId.equals(decryptAppId)) {
                    throw new CafeException("decrypt fail with appId = " + appId);
                }
                return result;
            }
        } catch (IOException e) {
            throw new CafeException(e);
        }
        return "";
    }
}
