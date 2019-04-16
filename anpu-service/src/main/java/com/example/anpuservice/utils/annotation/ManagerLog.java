package com.example.anpuservice.utils.annotation;

import java.lang.annotation.*;

/**
 * FileName: com.iyingke.riskbackstage.utils.annotation.ManagerLog.java
 * Author: Administrator
 * Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ManagerLog {

    //参数名称
    String paramName() default "";

    //操作说明
    String operation() default "";

    //操作类型
    String operateType() default "";
}
