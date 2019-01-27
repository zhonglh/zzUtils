package com.zz.bms.util.configs.annotaions;

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

    //该实体的业务名称列， 如果是组合， 多个列用 ,; 分割
    public String businessName() default "";


    //该实体的业务唯一识别列, 可以有多个， 如果是组合， 用 ,; 分割
    //如用户实体 ， 可以是登录名  身份证号码， 手机号 , {"login_name","idNumber","phone"}
    //如账户册， 是通过 账户册类型+编号来识别  {"account_type_id,account_codde"}
    public String[] businessKey() default {};

}
