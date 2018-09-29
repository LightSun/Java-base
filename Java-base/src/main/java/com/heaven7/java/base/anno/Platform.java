package com.heaven7.java.base.anno;

import com.heaven7.java.base.util.CoreConstants;
import com.heaven7.java.base.util.Platforms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the platform.
 * @author heaven7
 * @since 1.1.3
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD , ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Platform {

    /**
     * indicate the support platforms. default is all PC platform(windows, mac, linux).
     * @return the support platforms
     */
    byte[] value() default { Platforms.WINDOWS, Platforms.MAC, Platforms.LINUX };

    /**
     * indicate the possibility of used platform
     * @return the possibility. default is {@linkplain CoreConstants#ALWAYS}.
     * @since 1.1.3.5
     */
    byte possibility() default CoreConstants.ALWAYS;
}
