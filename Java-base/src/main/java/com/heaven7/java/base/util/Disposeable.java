package com.heaven7.java.base.util;

/**
 * indicate this object can be dispose.
 * @author heaven7 
 * @since 1.0.2
 */
public interface Disposeable {
    /**
     * dispose something. often used to release the resource at last. eg: when destroy.
     */
    void  dispose();
}
