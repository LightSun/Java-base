package com.heaven7.java.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * indicate the member is deprecated.
 * Created by heaven7 on 2016/12/7.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, 
	ElementType.CONSTRUCTOR,ElementType.ANNOTATION_TYPE})
public @interface Deprecated {
    /**
     * the info of deprecated.
     * @return a string array.
     */
    String[] value();
}