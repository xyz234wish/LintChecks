package com.wish.lint.module.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * @description: 限制调用注解
 * @Author: lihongzhi
 * @CreateDate: 2020/6/11 2:45 PM
 */
@Documented
@Retention(CLASS)
@Target({METHOD, TYPE})
public @interface CallerClass {
    Class[] value();
}