package com.xxf.common;

import com.xxf.entity.WantedVO;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
        wantedVO.setBrand(1);
        wantedVO.setEndTime(new Date());

        String[] values = StringUtils.split("1", ",");
        Object result = getWantedVOValueByName(wantedVO, "brand", String.class);
        Assert.assertTrue(ArrayUtils.contains(values, result));
        Assert.assertEquals(Utils.getFieldValueByName("brand", wantedVO), 1);
    }

    private Object getWantedVOValueByName(WantedVO wantedVO, String name, Class cls) {
        return ConvertUtils.convert(Utils.getFieldValueByName(name, wantedVO), cls);
    }

}
