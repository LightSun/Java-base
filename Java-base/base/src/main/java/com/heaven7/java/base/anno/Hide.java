package com.heaven7.java.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this indicate the class/field/ method is hide for out user.out user should not call it.
 * or else may cause problem.
 * Created by heaven7 on 2016/3/18.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD , ElementType.FIELD ,ElementType.TYPE})
public @interface Hide {

}