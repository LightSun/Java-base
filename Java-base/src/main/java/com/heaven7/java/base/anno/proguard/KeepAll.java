package com.heaven7.java.base.anno.proguard;


import java.lang.annotation.*;


/**
 * indicate the whole class will not be proguard.
 * <code>
 *     <pre>
 *         -keep @KeepAll class * {*;}
 *     </pre>
 * </code>
 * @author heaven7
 * @since 1.1.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface KeepAll {
}
