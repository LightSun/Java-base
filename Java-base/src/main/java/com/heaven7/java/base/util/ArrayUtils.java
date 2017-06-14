package com.heaven7.java.base.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * the common tool of array.
 * @author heaven7
 * @since 1.0.5
 */
public final class ArrayUtils {

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
