package com.heaven7.java.base.util;

import com.heaven7.java.base.anno.Nullable;

import java.util.Comparator;
import java.util.List;

/**
 * the unity class of search. such as binary search.
 * 
 * @author heaven7
 * @since 1.0.5
 */
public final class SearchUtils {

	/**
	 * binary search the key in the array. return the index of it. if index < 0,
	 * means the element is not in the array, and the right insert position =
	 * -(index +1 ) . if index >=0 . right
	 * 
	 * @param <T>
	 *            the type to compare
	 * @param list
	 *            the list
	 * @param key
	 *            the key to search
	 * @param comparator
	 *            the comparator, null for comparable.
	 * @return the index. if index < 0, means the element is not in the array,
	 *         and the right insert position = -(index +1 ) . if index >=0 .
	 *         right. if list.size() == 0 . return -1. 
	 */
	public static <T> int binarySearch(List<T> list, T key, @Nullable Comparator<? super T> comparator) {
		return binarySearch(list, 0, list.size(), key, comparator);
	}

	/**
	 * binary search the key in the array. return the index of it. if index < 0,
	 * means the element is not in the array, and the right insert position =
	 * -(index +1 ) . if index >=0 . right
	 * 
	 * @param <T>
	 *            the type to compare
	 * @param list
	 *            the sorted list (sort ascending)
	 * @param start
	 *            the start index of list
	 * @param len
	 *            the length of search in list.
	 * @param key
	 *            the key to search
	 * @param comparator
	 *            the comparator, null for comparable.
	 * @return the index. if index < 0, means the element is not in the array,
	 *         and the right insert position = -(index +1 ) . if index >=0 .
	 *         right
	 */
	public static <T> int binarySearch(List<T> list, int start, int len, T key, @Nullable Comparator<? super T> comparator) {
		if(comparator == null){
			comparator = CMP_DEFAULT;
		}
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (comparator.compare(list.get(guess), key) < 0) {
				low = guess;
			} else {
				high = guess;
			}
		}

		if (high == start + len)
			return ~(start + len);
		else if (comparator.compare(list.get(high), key) == 0)
			return high;
		else
			return ~high;
	}

	/**
	 * binary search the key in the array. return the index of it. if index < 0,
	 * means the element is not in the array, and the right insert position =
	 * -(index +1 ) . if index >=0 . right
	 * 
	 * @param a
	 *            the sorted array. (sort ascending)
	 * @param key
	 *            the target key.
	 * @return the index. if index < 0, means the element is not in the array,
	 *         and the right insert position = -(index +1 ) . if index >=0 .
	 *         right
	 */
	public static int binarySearch(int[] a, int key) {
		return binarySearch(a, 0, a.length, key);
	}

	/**
	 * binary search the key in the array. return the index of it. if index < 0,
	 * means the element is not in the array, and the right insert position =
	 * -(index +1 ) . if index >=0 . right
	 * 
	 * @param a
	 *            the sorted array
	 * @param start
	 *            the start index of array
	 * @param len
	 *            the length of elements in array
	 * @param key
	 *            the target key.
	 * @return the index. if index < 0, means the element is not in the array,
	 *         and the right insert position = -(index +1 ) . if index >=0 .
	 *         right
	 */
	public static int binarySearch(int[] a, int start, int len, int key) {
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (a[guess] < key)
				low = guess;
			else
				high = guess;
		}

		if (high == start + len)
			return ~(start + len);
		else if (a[high] == key)
			return high;
		else
			return ~high;
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	private static final Comparator<Object> CMP_DEFAULT = new Comparator<Object>() {
		@Override
		public int compare(Object o1, Object o2) {
			return ((Comparable)o1).compareTo(o2);
		}
	};
}
