package com.xxf.common;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void decryptTest() {
        String appId = "wx4f4bc4dec97d474b";
        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        String result = WXCore.decrypt(appId, encryptedData, sessionKey, iv);
        String expectResult = "{\"openId\":\"oGZUI0egBJY1zhBYw2KhdUfwVJJE\",\"nickName\":\"Band\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"Guangzhou\",\"province\":\"Guangdong\",\"country\":\"CN\",\"avatarUrl\":\"http://wx.qlogo.cn/mmopen/vi_32/aSKcBBPpibyKNicHNTMM0qJVh8Kjgiak2AHWr8MHM4WgMEm7GFhsf8OYrySdbvAMvTsw3mo8ibKicsnfN5pRjl1p8HQ/0\",\"unionId\":\"ocMvos6NjeKLIBqg5Mr9QjxrP1FA\",\"watermark\":{\"timestamp\":1477314187,\"appid\":\"wx4f4bc4dec97d474b\"}}";
        Assert.assertEquals(result, expectResult);
    }

}
