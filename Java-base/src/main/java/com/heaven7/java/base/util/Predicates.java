package com.heaven7.java.base.util;

import java.util.Collection;
import java.util.SortedMap;
/**
 * some useful methods of predicate.
 * @author heaven7
 *
 */
public final class Predicates {
	
	private Predicates(){}
	
	public static boolean equals(Object a, Object b) {
		return (a == b) || (a != null && a.equals(b));
	}
	public static boolean isTrue(Object value) {
		return value != null && value instanceof Boolean && (Boolean)value;
	}
	public static <E> boolean isEmpty(Collection<E> list) {
		return list == null || list.size() == 0;
	}

	/**
	 *  indicate the map is empty or not.
	 * @param map the map
	 * @return is empty or not
	 * @since 1.1.3.5
	 */
	public static boolean isEmpty(java.util.Map<?, ?> map) {
		return map == null || map.size() == 0;
	}
	public static boolean isEmpty(int[] arr) {
		return arr == null || arr.length == 0;
	}
	public static <T> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}
	public static <K,V> boolean isSortedMap(java.util.Map<K, V> map){
		return map instanceof SortedMap;
	}

	/**
	 * indicate the string is empty or not.
	 * @param str the string
	 * @return true if is empty.
	 * @since 1.1.3
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
}
