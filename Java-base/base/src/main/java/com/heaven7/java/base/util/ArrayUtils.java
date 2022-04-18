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
	public static int[] toIntArray(Collection<Integer> coll){
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
	 * convert the collection to byte array.
	 * @param coll the byte collection
	 * @return the byte array.
	 * @since 1.2.0
	 */
	public static byte[] toByteArray(Collection<Byte> coll){
		if(coll == null || coll.isEmpty()){
			return null;
		}
		final byte[] arr = new byte[coll.size()];
		final Iterator<Byte> it = coll.iterator();
		int i = 0 ;
		for (; it.hasNext();){
			arr[i++] = it.next();
		}
		return arr;
	}
	/**
	 * convert the collection to short array.
	 * @param coll the short collection
	 * @return the short array.
	 * @since 1.2.0
	 */
	public static short[] toShortArray(Collection<Short> coll){
		if(coll == null || coll.isEmpty()){
			return null;
		}
		final short[] arr = new short[coll.size()];
		final Iterator<Short> it = coll.iterator();
		int i = 0 ;
		for (; it.hasNext();){
			arr[i++] = it.next();
		}
		return arr;
	}
	/**
	 * convert the collection to long array.
	 * @param coll the long collection
	 * @return the long array.
	 * @since 1.2.0
	 */
	public static long[] toLongArray(Collection<Long> coll){
		if(coll == null || coll.isEmpty()){
			return null;
		}
		final long[] arr = new long[coll.size()];
		final Iterator<Long> it = coll.iterator();
		int i = 0 ;
		for (; it.hasNext();){
			arr[i++] = it.next();
		}
		return arr;
	}
	/**
	 * convert the collection to float array.
	 * @param coll the float collection
	 * @return the float array.
	 * @since 1.2.0
	 */
	public static float[] toFloatArray(Collection<Float> coll){
		if(coll == null || coll.isEmpty()){
			return null;
		}
		final float[] arr = new float[coll.size()];
		final Iterator<Float> it = coll.iterator();
		int i = 0 ;
		for (; it.hasNext();){
			arr[i++] = it.next();
		}
		return arr;
	}
	/**
	 * convert the collection to double array.
	 * @param coll the double collection
	 * @return the double array.
	 * @since 1.2.0
	 */
	public static double[] toDoubleArray(Collection<Double> coll){
		if(coll == null || coll.isEmpty()){
			return null;
		}
		final double[] arr = new double[coll.size()];
		final Iterator<Double> it = coll.iterator();
		int i = 0 ;
		for (; it.hasNext();){
			arr[i++] = it.next();
		}
		return arr;
	}
	/**
	 * convert the collection to boolean array.
	 * @param coll the boolean collection
	 * @return the boolean array.
	 * @since 1.2.0
	 */
	public static boolean[] toBooleanArray(Collection<Boolean> coll){
		if(coll == null || coll.isEmpty()){
			return null;
		}
		final boolean[] arr = new boolean[coll.size()];
		final Iterator<Boolean> it = coll.iterator();
		int i = 0 ;
		for (; it.hasNext();){
			arr[i++] = it.next();
		}
		return arr;
	}
	/**
	 * convert the collection to boolean array.
	 * @param coll the boolean collection
	 * @return the char array.
	 * @since 1.2.0
	 */
	public static char[] toCharArray(Collection<?> coll){
		if(coll == null || coll.isEmpty()){
			return null;
		}
		final char[] arr = new char[coll.size()];
		final Iterator<?> it = coll.iterator();
		int i = 0 ;
		for (; it.hasNext();){
			String s = it.next().toString();
			try {
				arr[i] = (char)Integer.parseInt(s);
			}catch (NumberFormatException e){
				if(s.length() > 1){
					throw new UnsupportedOperationException(s);
				}
				arr[i] = s.charAt(0);
			}
			i++;
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
