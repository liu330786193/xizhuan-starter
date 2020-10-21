package com.xizhuan.prevent.duplicate.form.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: xizhuan-starter
 * @Package: com.xizhuan.prevent.duplicate.form.annotation
 * @ClassName: XZPreventDuplicateForm
 * @Author: lyl
 * @Description:
 * @Date: 2020/10/21 11:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface XZPreventDuplicateForm {

    /**
     * 过期时间
     */
    int expire() default 5;

    /**
     * 超时时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
