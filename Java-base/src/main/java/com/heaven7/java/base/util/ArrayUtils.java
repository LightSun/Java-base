package com.heaven7.java.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * the common tool of array/list.
 * @author heaven7
 * @since 1.0.5
 */
public final class ArrayUtils {

	/**
	 * make the object to a list.
	 * @param <T> the object type
	 * @param t the object
	 * @return the list
	 * @since 1.0.6
	 */
	public static <T> List<T> toList(T t){
		return Arrays.asList(t);
	}
	/**
	 * make the all object to a list.
	 * @param <T> the object type
	 * @param t1 the first object
	 * @param t2 the second object
	 * @return the list
	 * @since 1.0.6
	 */
	public static <T> List<T> toList(T t1, T t2){
		return Arrays.asList(t1, t2);
	}
	/**
	 * make the all object to a list.
	 * @param <T> the object type
	 * @param t1 the first object
	 * @param t2 the second object
	 * @param t3 the third object
	 * @return the list
	 * @since 1.0.6
	 */
	public static <T> List<T> toList(T t1, T t2, T t3){
		return Arrays.asList(t1, t2, t3);
	}
	/**
	 * convert the collection to int array.
	 * @param coll the integer collection
	 * @return the int array.
	 */
	public static int[] toArray(Collection<Integer> coll){
		if(coll == null || coll.isEmpty()){
			return null;
		}
		final int[] arr = new int[coll.size()];
		final Iterator<Integer> it = coll.iterator();
		int i = 0 ;
		for (; it.hasNext();){
			arr[i++] = it.next();
		}
		return arr;
	}
	/**
	 * convert the int array to a list.
	 * @param arr the int array
	 * @return the result list.
	 */
	public static List<Integer> toList(int[] arr){
		return toList(arr, null);
	}
	/**
	 * convert the int array to a list.
	 * @param arr the int array
	 * @param out the out list, can be null
	 * @return the result list.
	 */
	public static List<Integer> toList(int[] arr, List<Integer> out){
		if(out == null){
			out = new ArrayList<>();
		}
		for(int size = arr.length, i= 0 ; i<size ; i++ ){
			out.add(arr[i]);
		}
		return out;
	}
}
