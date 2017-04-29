package com.heaven7.java.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this indicate the field or method is not safe in sub thread. you should care about it.
 * Created by heaven7 on 2016/3/18.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD , ElementType.FIELD, ElementType.TYPE})
public @interface ThreadNotSafe {
}