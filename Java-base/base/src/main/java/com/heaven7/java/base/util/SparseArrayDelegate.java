package com.heaven7.java.base.util;

import java.util.List;

/**
 * the sparse array delegate
 * @param <E> the value type of sparse array.
 * @author heaven7
 * @since 1.1.7
 */
public interface SparseArrayDelegate<E> {
    /**
     * Gets the Object mapped from the specified key, or <code>null</code> if no
     * such mapping has been made.
     */
    E get(int key);

    /**
     * Gets the Object mapped from the specified key, or the specified Object if
     * no such mapping has been made.
     */
    @SuppressWarnings("unchecked")
    E get(int key, E valueIfKeyNotFound);

    /**
     * Removes the mapping from the specified key, if there was any.
     */
    void delete(int key);

    /**
     * Alias for {@link #delete(int)}.
     */
    void remove(int key);

    /**
     * get the value and remove mapping.
     *
     * @param key
     *            the key
     * @return the previous value
     * @since 1.0.7
     */
    @SuppressWarnings("unchecked")
    E getAndRemove(int key);

    /**
     * Removes the mapping at the specified index.
     */
    void removeAt(int index);

    /**
     * Remove a range of mappings as a batch.
     *
     * @param index
     *            Index to begin at
     * @param size
     *            Number of mappings to remove
     */
    void removeAtRange(int index, int size);

    /**
     * Adds a mapping from the specified key to the specified value, replacing
     * the previous mapping from the specified key if there was one.
     * @return the old value.
     * changed 1.1.1 return from void to E.
     */
    @SuppressWarnings("unchecked")
    E put(int key, E value);

    /**
     * Returns the number of key-value mappings that this SparseArray currently
     * stores.
     */
    int size();

    /**
     * Given an index in the range <code>0...size()-1</code>, returns the key
     * from the <code>index</code>th key-value mapping that this SparseArray
     * stores.
     */
    int keyAt(int index);

    /**
     * Given an index in the range <code>0...size()-1</code>, returns the value
     * from the <code>index</code>th key-value mapping that this SparseArray
     * stores.
     */
    @SuppressWarnings("unchecked")
    E valueAt(int index);

    /**
     * Given an index in the range <code>0...size()-1</code>, sets a new value
     * for the <code>index</code>th key-value mapping that this SparseArray
     * stores.
     */
    void setValueAt(int index, E value);

    /**
     * Returns the index for which {@link #keyAt} would return the specified
     * key, or a negative number if the specified key is not mapped.
     */
    int indexOfKey(int key);

    /**
     * Returns an index for which {@link #valueAt} would return the specified
     * key, or a negative number if no keys map to the specified value.
     * <p>
     * Beware that this is a linear search, unlike lookups by key, and that
     * multiple keys can map to the same value and this will find only one of
     * them.
     * <p>
     * Note also that unlike most collections' {@code indexOf} methods, this
     * method compares values using {@code ==} rather than {@code equals}.
     */
    int indexOfValue(E value);

    /**
     * Returns an index for which {@link #valueAt} would return the specified
     * key, or a negative number if no keys map to the specified value.
     *
     * @param value
     *            the value
     * @param identity
     *            true .if only use '==' to judge. false include use 'equals()'
     *            to judge.
     * @return the index of value
     * @since 1.0.6
     */
    int indexOfValue(E value, boolean identity);

    /**
     * Removes all key-value mappings from this SparseArray.
     */
    void clear();

    /**
     * Increases the size of the underlying storage if needed, to ensure that it
     * can hold the specified number of items without having to allocate
     * additional memory
     *
     * @param capacity
     *            the number of items
     */
    void ensureCapacity(int capacity);

    /**
     * @return a read-only list of the values in this SparseArray which are in
     *         ascending order, based on their associated key
     */
    @SuppressWarnings("unchecked")
    List<E> getValues();
}
