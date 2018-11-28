package com.xxf.common;

import com.xxf.entity.WantedVO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class UtilsTest {

    @Test
    public void testGetFieldValueByName() {
        WantedVO wantedVO = new WantedVO();
        wantedVO.setId(0);
        wantedVO.setNickname("test");
        wantedVO.setAddress("test");
        wantedVO.setBrand(2);
        wantedVO.setEndTime(new Date());
        Assert.assertEquals(Utils.getFieldValueByName("brand", wantedVO), 2);
    }

}
