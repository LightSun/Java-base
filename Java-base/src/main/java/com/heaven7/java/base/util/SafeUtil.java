package com.heaven7.java.base.util;

import java.util.concurrent.atomic.AtomicReference;

/**
 * safe tool class about thread.
 * @author heaven7
 * @since 1.0.7
 */
public class SafeUtil {

	
	/**
	 * get the value of AtomicReference and update it.
	 * @param <V> the value type
	 * @param ar the AtomicReference
	 * @param operator the SafeOperator
	 * @return the previous value
	 */
	public static <V> V getAndUpdate(AtomicReference<V> ar, SafeOperator<V> operator) {
		V prev, next;
		do {
			prev = ar.get();
			next = operator.apply(prev);
		} while (!ar.compareAndSet(prev, next));
		return prev;
	}

	/**
	 * the safe operator
	 * @author heaven7
	 *
	 * @param <T> the type
	 * @since 1.0.7
	 */
	public interface SafeOperator<T> {
		/**
		 * apply the operation 
		 * @param pre the previous object.
		 * @return the result. often is new object.
		 */
		T apply(T pre);
	}
}
