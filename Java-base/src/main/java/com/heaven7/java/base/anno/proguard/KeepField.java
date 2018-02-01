package com.heaven7.java.base.anno.proguard;


import java.lang.annotation.*;


/**
 * indicate the field will not be proguard.
 * <code>
 *     <pre>
 *         -keepclassmembers class * {
                 {@literal }@KeepField <fields>;
           }
 *     </pre>
 * </code>
 * @author heaven7
 * @since 1.1.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface KeepField {
}
