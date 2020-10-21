package com.xizhuan.prevent.duplicate.form;

import com.xizhuan.prevent.duplicate.form.annotation.XZPreventDuplicateForm;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.lang.reflect.Method;

/**
 * @ProjectName: xizhuan-starter
 * @Package: com.xizhuan.prevent.duplicate.form
 * @ClassName: XZLockMethodInterceptor
 * @Author: lyl
 * @Description: 拦截器
 * @Date: 2020/10/21 13:23
 */

@Aspect
@Configuration
public class XZLockMethodInterceptor {

    @Autowired
    public XZLockMethodInterceptor(StringRedisTemplate stringRedisTemplate, XZCacheKeyGenerator keyGenerator){
        this.stringRedisTemplate = stringRedisTemplate;
        this.keyGenerator = keyGenerator;
    }

    private final StringRedisTemplate stringRedisTemplate;
    private final XZCacheKeyGenerator keyGenerator;

    @Around("execution(public * *(..)) && @annotation(com.xizhuan.prevent.duplicate.form.annotation.XZPreventDuplicateForm)")
    public Object interceptor(ProceedingJoinPoint pjp){
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        XZPreventDuplicateForm lock = method.getAnnotation(XZPreventDuplicateForm.class);


        final String lockKey = keyGenerator.getLockKey(pjp);
        try {
            // 采用原生 API 来实现分布式锁
            final Boolean success = stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(lockKey.getBytes(), new byte[0], Expiration.from(lock.expire(), lock.timeUnit()), RedisStringCommands.SetOption.SET_IF_ABSENT));
            if (!success) {
                // TODO 按理来说 我们应该抛出一个自定义的 CacheLockException 异常;这里偷下懒
                throw new RuntimeException("请勿重复请求");
            }
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("系统异常");
            }
        } finally {
            stringRedisTemplate.delete(lockKey);
        }
    }

}
