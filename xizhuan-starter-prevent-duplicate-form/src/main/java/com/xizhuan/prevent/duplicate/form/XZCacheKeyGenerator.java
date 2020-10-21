package com.xizhuan.prevent.duplicate.form;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @ProjectName: xizhuan-starter
 * @Package: com.xizhuan.prevent.duplicate.form
 * @ClassName: XZCacheKeyGenerator
 * @Author: lyl
 * @Description: key生成类
 * @Date: 2020/10/21 13:07
 */
public interface XZCacheKeyGenerator {

    /**
     * 获取AOP参数 生成制定缓存key
     */
    String getLockKey(ProceedingJoinPoint pjp);

}
