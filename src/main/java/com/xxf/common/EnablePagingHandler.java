package com.xxf.common;

import com.github.pagehelper.PageHelper;
import com.xxf.entity.CafeException;
import com.xxf.entity.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class EnablePagingHandler {

    @Pointcut("@annotation(com.xxf.common.EnablePaging)")
    public void serviceAspect() {

    }

    @Around(value = "serviceAspect()")
    public Result doAround(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        Class clazz = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        Map<String, Object> nameAndArgs = this.getFieldsName(clazz, methodName, args);
        Integer pageNum = (Integer) nameAndArgs.get("pageNum");
        Integer pageSize = (Integer) nameAndArgs.get("pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        try {
            PageHelper.startPage(pageNum, pageSize);
            Object returnValue = point.proceed(args);
            return (Result) returnValue;
        } finally {
            if (PageHelper.getLocalPage() != null) {
                PageHelper.clearPage();
            }
        }
    }

    /**
     * 通过反射获取参数列表
     *
     * @throws Exception
     */
    private Map<String, Object> getFieldsName(Class cls, String methodName, Object[] args) throws Throwable {
        Map<String, Object> map = new HashMap<>();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (methodName.equalsIgnoreCase(method.getName())) {
                String[] names = discoverer.getParameterNames(method);
                if (names == null || names.length != args.length) {
                    continue;
                }
                for (int i = 0; i < names.length; i++) {
                    map.put(names[i], args[i]);
                }
                break;
            }
        }
        if (map.isEmpty()) {
            throw new CafeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown Error");
        }
        return map;
    }

}
