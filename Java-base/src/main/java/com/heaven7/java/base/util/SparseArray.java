/*
 * Copyright 2013, Google Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.heaven7.java.base.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.heaven7.java.base.anno.Deprecated;
import com.heaven7.java.base.anno.ThreadNotSafe;
import static com.heaven7.java.base.util.SearchUtils.binarySearch;

/**
 * SparseArrays map integers to Objects. Unlike a normal array of Objects, there
 * can be gaps in the indices. It is intended to be more efficient than using a
 * HashMap to map Integers to Objects.
 * <p>Use {@linkplain SparseArrayDelegate} by {@linkplain SparseFactory#newSparseArray(int)} instead. this will removed in 2.x</p>
 */
@Deprecated("use SparseFactory instead.")
@ThreadNotSafe
public class SparseArray<E> implements SparseArrayDelegate<E> {
	private static final Object DELETED = new Object();
	private boolean mGarbage = false;

	/**
	 * Creates a new SparseArray containing no mappings.
	 * <p>Use {@linkplain SparseArrayDelegate} by {@linkplain SparseFactory#newSparseArray(int)} instead. this will removed in 2.x</p>
	 */
	@java.lang.Deprecated
	public SparseArray() {
		this(10);
	}

	/**
	 * Creates a new SparseArray containing no mappings that will not require
	 * any additional memory allocation to store the specified number of
	 * mappings.
	 * <p>Use {@linkplain SparseArrayDelegate} by {@linkplain SparseFactory#newSparseArray(int)} instead. this will removed in 2.x</p>
	 */
	@java.lang.Deprecated
	public SparseArray(int initialCapacity) {
		mKeys = new int[initialCapacity];
		mValues = new Object[initialCapacity];
		mSize = 0;
	}

	@Override
	public E get(int key) {
		return get(key, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(int key, E valueIfKeyNotFound) {
		int i = binarySearch(mKeys, 0, mSize, key);

		if (i < 0 || mValues[i] == DELETED) {
			return valueIfKeyNotFound;
		} else {
			return (E) mValues[i];
		}
	}

	@Override
	public void delete(int key) {
		int i = binarySearch(mKeys, 0, mSize, key);

		if (i >= 0) {
			if (mValues[i] != DELETED) {
				mValues[i] = DELETED;
				mGarbage = true;
			}
		}
	}

	@Override
	public void remove(int key) {
		delete(key);
	}

	@Override
	@SuppressWarnings("unchecked")
	public E getAndRemove(int key) {
		int i = binarySearch(mKeys, 0, mSize, key);

		Object val = null;
		if (i >= 0) {
			if (mValues[i] != DELETED) {
				val = mValues[i];
				mValues[i] = DELETED;
				mGarbage = true;
			}
		}
		return (E) val;
	}

	@Override
	public void removeAt(int index) {
		if (mValues[index] != DELETED) {
			mValues[index] = DELETED;
			mGarbage = true;
		}
	}

	@Override
	public void removeAtRange(int index, int size) {
		final int end = Math.min(mSize, index + size);
		for (int i = index; i < end; i++) {
			removeAt(i);
		}
	}

	private void gc() {
		// Log.e("SparseArray", "gc start with " + mSize);

		int n = mSize;
		int o = 0;
		int[] keys = mKeys;
		Object[] values = mValues;

		for (int i = 0; i < n; i++) {
			Object val = values[i];

			if (val != DELETED) {
				if (i != o) {
					keys[o] = keys[i];
					values[o] = val;
				}

				o++;
			}
		}

		mGarbage = false;
		mSize = o;

		// Log.e("SparseArray", "gc end with " + mSize);
	}

	@Override
	@SuppressWarnings("unchecked")
	public E put(int key, E value) {
		int i = binarySearch(mKeys, 0, mSize, key);

		if (i >= 0) {
			Object old = mValues[i];
			mValues[i] = value;
			if(old == DELETED){
                 return null;
			}
			return (E) old;
		} else {
			i = ~i;

			if (i < mSize && mValues[i] == DELETED) {
				mKeys[i] = key;
				mValues[i] = value;
				return null;
			}

			if (mGarbage && mSize >= mKeys.length) {
				gc();

				// Search again because indices may have changed.
				i = ~binarySearch(mKeys, 0, mSize, key);
			}

			if (mSize >= mKeys.length) {
				int n = Math.max(mSize + 1, mKeys.length * 2);

				int[] nkeys = new int[n];
				Object[] nvalues = new Object[n];

				// Log.e("SparseArray", "grow " + mKeys.length + " to " + n);
				System.arraycopy(mKeys, 0, nkeys, 0, mKeys.length);
				System.arraycopy(mValues, 0, nvalues, 0, mValues.length);

				mKeys = nkeys;
				mValues = nvalues;
			}

			if (mSize - i != 0) {
				// Log.e("SparseArray", "move " + (mSize - i));
				System.arraycopy(mKeys, i, mKeys, i + 1, mSize - i);
				System.arraycopy(mValues, i, mValues, i + 1, mSize - i);
			}

			mKeys[i] = key;
			mValues[i] = value;
			mSize++;
		}
		return null;
	}

	@Override
	public int size() {
		if (mGarbage) {
			gc();
		}

		return mSize;
	}

	@Override
	public int keyAt(int index) {
		if (mGarbage) {
			gc();
		}

		return mKeys[index];
	}

	@Override
	@SuppressWarnings("unchecked")
	public E valueAt(int index) {
		if (mGarbage) {
			gc();
		}

		return (E) mValues[index];
	}

	@Override
	public void setValueAt(int index, E value) {
		if (mGarbage) {
			gc();
		}

		mValues[index] = value;
	}

	@Override
	public int indexOfKey(int key) {
		if (mGarbage) {
			gc();
		}

		return binarySearch(mKeys, 0, mSize, key);
	}

	@Override
	public int indexOfValue(E value) {
		return indexOfValue(value, true);
	}

	@Override
	public int indexOfValue(E value, boolean identity) {
		if (mGarbage) {
			gc();
		}
		for (int i = 0; i < mSize; i++) {

			if (mValues[i] == value) {
				return i;
			}
			if (!identity) {
				if (mValues[i].equals(value))
					return i;
			}
		}
		return -1;
	}

	@Override
	public void clear() {
		int n = mSize;
		Object[] values = mValues;

		for (int i = 0; i < n; i++) {
			values[i] = null;
		}

		mSize = 0;
		mGarbage = false;
	}

	/**
	 * Puts a key/value pair into the array, optimizing for the case where the
	 * key is greater than all existing keys in the array.
	 */
	public void append(int key, E value) {
		if (mSize != 0 && key <= mKeys[mSize - 1]) {
			put(key, value);
			return;
		}

		if (mGarbage && mSize >= mKeys.length) {
			gc();
		}

		int pos = mSize;
		if (pos >= mKeys.length) {
			int n = Math.max(pos + 1, mKeys.length * 2);

			int[] nkeys = new int[n];
			Object[] nvalues = new Object[n];

			// Log.e("SparseArray", "grow " + mKeys.length + " to " + n);
			System.arraycopy(mKeys, 0, nkeys, 0, mKeys.length);
			System.arraycopy(mValues, 0, nvalues, 0, mValues.length);

			mKeys = nkeys;
			mValues = nvalues;
		}

		mKeys[pos] = key;
		mValues[pos] = value;
		mSize = pos + 1;
	}

	@Override
	public void ensureCapacity(int capacity) {
		if (mGarbage && mSize >= mKeys.length) {
			gc();
		}

		if (mKeys.length < capacity) {
			int[] nkeys = new int[capacity];
			Object[] nvalues = new Object[capacity];

			System.arraycopy(mKeys, 0, nkeys, 0, mKeys.length);
			System.arraycopy(mValues, 0, nvalues, 0, mValues.length);

			mKeys = nkeys;
			mValues = nvalues;
		}
	}

	/*
	 * private static int binarySearch(int[] a, int start, int len, int key) {
	 * int high = start + len, low = start - 1, guess;
	 * 
	 * while (high - low > 1) { guess = (high + low) / 2;
	 * 
	 * if (a[guess] < key) low = guess; else high = guess; }
	 * 
	 * if (high == start + len) return ~(start + len); else if (a[high] == key)
	 * return high; else return ~high; }
	 */

	@Override
	@SuppressWarnings("unchecked")
	public List<E> getValues() {
		return Collections.unmodifiableList(Arrays.asList((E[]) mValues));
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (int i = 0, s = size(); i < s; i++) {
			E value = valueAt(i);
			Integer key = keyAt(i);
			result += key.hashCode() ^ (value == null ? 0 : value.hashCode());
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof SparseArray)) {
			return false;
		}
		SparseArray<?> sa = (SparseArray<?>) obj;
		if (size() != sa.size()) {
			return false;
		}
		for (int i = 0, s = size(); i < s; i++) {
			E mine = valueAt(i);
			int key = keyAt(i);
			Object theirs = sa.get(key);
			 if (mine == null) {
                 if (theirs != null) {
                     return false;
                 }
             } else if (!mine.equals(theirs)) {
                 return false;
             }
		}

		return true;
	}

	@Override
	public String toString() {
		if (size() <= 0) {
			return "{}";
		}

		StringBuilder buffer = new StringBuilder(mSize * 28);
		buffer.append('{');
		for (int i = 0; i < mSize; i++) {
			if (i > 0) {
				buffer.append(", ");
			}
			int key = keyAt(i);
			buffer.append(key);
			buffer.append('=');
			Object value = valueAt(i);
			if (value != this) {
				buffer.append(value);
			} else {
				buffer.append("(this Map)");
			}
		}
		buffer.append('}');
		return buffer.toString();
	}

	private int[] mKeys;
	private Object[] mValues;
	private int mSize;
}
