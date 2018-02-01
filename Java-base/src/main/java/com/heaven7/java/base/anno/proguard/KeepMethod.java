package com.heaven7.java.base.anno.proguard;


import java.lang.annotation.*;


/**
 * indicate the field will not be proguard.
 * <code>
 *     <pre>
 *         -keepclassmembers class * {
                 {@literal }@KeepMethod <methods>;
           }
 *     </pre>
 * </code>
 * @author heaven7
 * @since 1.1.1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface KeepMethod {
}
