package com.heaven7.java.base.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(T t){
		Object array = Array.newInstance(t.getClass(), 1);
		Array.set(array, 0, t);
		return (T[]) array;
	}
	/**
	 * make the all object to a list.
	 * @param <T> the object type
	 * @param t1 the first object
	 * @param t2 the second object
	 * @return the list
	 * @since 1.0.6
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(T t1, T t2){
		Object array = Array.newInstance(t1.getClass(), 2);
		Array.set(array, 0, t1);
		Array.set(array, 1, t2);
		return (T[]) array;
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
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(T t1, T t2, T t3){
		Object array = Array.newInstance(t1.getClass(), 3);
		Array.set(array, 0, t1);
		Array.set(array, 1, t2);
		Array.set(array, 2, t3);
		return (T[]) array;
	}
	/**
	 * convert the list to array.
	 * @param <T> the element type
	 * @param list the list
	 * @return the array.
	 * @since 1.0.6
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> list){
		if(list == null || list.isEmpty()){
			return null;
		}
		final int size = list.size();
		Object array = Array.newInstance(list.get(0).getClass(), size);
		for (int i = size - 1 ; i >=0 ; i --){
			Array.set(array, i, list.get(i));
		}
		return (T[]) array;
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
