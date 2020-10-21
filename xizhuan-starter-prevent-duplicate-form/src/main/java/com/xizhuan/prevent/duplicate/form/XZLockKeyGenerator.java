package com.xizhuan.prevent.duplicate.form;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @ProjectName: xizhuan-starter
 * @Package: com.xizhuan.prevent.duplicate.form
 * @ClassName: XZLockKeyGenerator
 * @Author: lyl
 * @Description: 实现类
 * @Date: 2020/10/21 13:09
 */
@Component
public class XZLockKeyGenerator implements XZCacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String tempToken = sra.getRequest().getHeader("token");
        String token = StringUtils.isEmpty(tempToken) ? request.getHeader("Authorization") : tempToken;
        if (StringUtils.isEmpty(token)){
            throw new RuntimeException("非法请求");
        }

        StringBuilder builder = new StringBuilder("stater:prevent:duplicate:form:").append(method.getName()).append(":").append(token);
        return builder.toString();
    }
}
