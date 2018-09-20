package com.heaven7.java.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * indicate the member depend on something.
 * @author heaven7
 * @since 1.1.4
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD , ElementType.FIELD ,ElementType.TYPE})
public @interface DependOn {

    /** depend on somethings. */
    String[] value();
}
