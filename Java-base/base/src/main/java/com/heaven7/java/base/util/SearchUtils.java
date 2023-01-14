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
	 * @since 1.2.7
	 */
	public static int binarySearchDesc(long[] a, int start, int len, long key) {
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (a[guess] > key)
				low = guess;
			else
				high = guess;
		}

		if (high == start + len)
			return ~(start + len);
		else if (a[high] == key){
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @since 1.2.7
	 */
	public static int binarySearchDesc(float[] a, int start, int len, float key) {
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (a[guess] > key)
				low = guess;
			else
				high = guess;
		}

		if (high == start + len)
			return ~(start + len);
		else if (a[high] == key){
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @since 1.2.7
	 */
	public static int binarySearchDesc(double[] a, int start, int len, double key) {
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (a[guess] > key)
				low = guess;
			else
				high = guess;
		}

		if (high == start + len)
			return ~(start + len);
		else if (a[high] == key){
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @since 1.2.7
	 */
	public static int binarySearchDesc(short[] a, int start, int len, short key) {
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (a[guess] > key)
				low = guess;
			else
				high = guess;
		}

		if (high == start + len)
			return ~(start + len);
		else if (a[high] == key){
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @since 1.2.7
	 */
	public static int binarySearchDesc(byte[] a, int start, int len, byte key) {
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (a[guess] > key)
				low = guess;
			else
				high = guess;
		}

		if (high == start + len)
			return ~(start + len);
		else if (a[high] == key){
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @since 1.2.7
	 */
	public static int binarySearch(byte[] a, int start, int len, byte key) {
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
		else if (a[high] == key){
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @since 1.2.7
	 */
	public static int binarySearch(short[] a, int start, int len, short key) {
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
		else if (a[high] == key){
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @since 1.2.7
	 */
	public static int binarySearch(long[] a, int start, int len, long key) {
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
		else if (a[high] == key){
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @since 1.2.7
	 */
	public static int binarySearch(float[] a, int start, int len, float key) {
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
		else if (a[high] == key){
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @since 1.2.7
	 */
	public static int binarySearch(double[] a, int start, int len, double key) {
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
		else if (a[high] == key) {
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
	}
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
		else if (comparator.compare(list.get(high), key) == 0) {
			//may have the same element. we only want the first element index.
			int id = high;
			for (; id > 0; ) {
				id--;
				if (comparator.compare(list.get(id), key) != 0) {
					return id + 1;
				}
			}
			return id;
			//return high;
		}else
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
		else if (a[high] == key) {
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
			//return high;
		}else
			return ~high;
	}
	/**
	 * binary search the key in the desc-array
	 * @param a the array
	 * @param key the key to search
	 * @return the index. if index < 0, means the element is not in the array,
	 * 	       and the right insert position = -(index +1 ) . if index >=0 .
	 * 	       right
	 * @since 1.2.6
	 */
	public static int binarySearchDesc(int[] a, int key) {
		return binarySearchDesc(a, 0, a.length, key);
	}

	/**
	 * binary search the key in the desc-array
	 * @param a the array
	 * @param start the start index
	 * @param len the end index
	 * @param key the key to search
	 * @return the index. if index < 0, means the element is not in the array,
	 * 	       and the right insert position = -(index +1 ) . if index >=0 .
	 * 	       right
	 * @since 1.2.6
	 */
	public static int binarySearchDesc(int[] a, int start, int len, int key) {
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (a[guess] > key)
				low = guess;
			else
				high = guess;
		}

		if (high == start + len)
			return ~(start + len);
		else if (a[high] == key) {
			//may have the same element. we only want the first element index.
			int id = high;
			while (id > 0) {
				id--;
				if (a[id] != key) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
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
	 * @param key
	 *            the key to search
	 * @param comparator
	 *            the comparator, null for comparable.
	 * @return the index. if index < 0, means the element is not in the array,
	 *         and the right insert position = -(index +1 ) . if index >=0 .
	 *         right
	 * @since 1.2.6
	 */
	public static <T> int binarySearchDesc(List<T> list, T key, @Nullable Comparator<? super T> comparator) {
		return binarySearchDesc(list,0, list.size(), key, comparator);
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
	 * @since 1.2.6
	 */
	public static <T> int binarySearchDesc(List<T> list, int start, int len, T key, @Nullable Comparator<? super T> comparator) {
		if(comparator == null){
			comparator = CMP_DEFAULT;
		}
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (comparator.compare(list.get(guess), key) > 0) {
				low = guess;
			} else {
				high = guess;
			}
		}

		if (high == start + len)
			return ~(start + len);
		else if (comparator.compare(list.get(high), key) == 0) {
			//may have the same element. we only want the first element index.
			int id = high;
			for (; id > 0; ) {
				id--;
				if (comparator.compare(list.get(id), key) != 0) {
					return id + 1;
				}
			}
			return id;
		}else
			return ~high;
	}

	/**
	 *  find first not equals position.
	 * @param a the array, which is sorted
	 * @param start the start position
	 * @param len the end position
	 * @param key the key to search
	 * @return the first not equals position
	 * @since 1.2.7
	 */
	public static int findFirstNeqPos(int[] a, int start, int len, int key) {
		int high = start + len;
		int low = start - 1;
		int guess = -1;
		while(high - low > 1) {
			guess = (high + low) / 2;
			if (a[guess] != key) {
				low = guess;
			} else {
				high = guess;
			}
		}
//        System.out.println("low = " + low);
//        System.out.println("high = " + high);
//        System.out.println("guess = " + guess);
		return low;
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	private static final Comparator<Object> CMP_DEFAULT = new Comparator<Object>() {
		@Override
		public int compare(Object o1, Object o2) {
			return ((Comparable)o1).compareTo(o2);
		}
	};
}
