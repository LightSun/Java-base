package com.heaven7.java.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * indicate some api need android implements.
 * @author heaven7
 * @since 1.1.3
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface NeedAndroidImpl {

    /** the class name of android platform to impl */
    String value();

}
