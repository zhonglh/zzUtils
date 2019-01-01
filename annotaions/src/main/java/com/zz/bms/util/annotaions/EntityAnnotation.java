package com.zz.bms.util.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体注解类
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityAnnotation {

    //资源 , 如用户的 资源为 sys.user
    public String resource() default "" ;

    //实体名称 , 如用户
    public String value() default "";

}
