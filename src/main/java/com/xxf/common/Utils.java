package com.xxf.common;

import com.xxf.entity.CafeException;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Method;

public class Utils {

    private Utils() {

    }

    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            throw new CafeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "getFieldValueByName fail : " + e.getMessage());
        }
    }

}
