package com.heaven7.java.base.util;

/**
 * @author heaven7
 * @since 1.1.7
 */
public class SparseFactory {

    /**
     * create a sparse array.
     * @param initialCapacity the initial capacity
     * @param <E> the value type
     * @return the sparse array delegate.
     */
    public static <E> SparseArrayDelegate<E> newSparseArray(int initialCapacity){
        return new SparseArray<>(initialCapacity);
    }
}
