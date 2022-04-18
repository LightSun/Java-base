package com.heaven7.java.base.util;

/**
 * indicate this object can be dispose.
 * @author heaven7 
 * @since 1.0.2
 */
@Deprecated
@com.heaven7.java.base.anno.Deprecated("use Disposable instead. latter will delete this class")
public interface Disposeable {
    /**
     * dispose something. often used to release the resource at last. eg: when destroy.
     */
    void  dispose();
}
