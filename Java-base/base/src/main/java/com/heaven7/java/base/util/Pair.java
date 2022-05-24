package com.heaven7.java.base.util;

import java.util.Map;

public class Pair<K, V> implements Map.Entry<K, V> {
    public K key;
    public V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public V setValue(V val) {
        V old = this.value;
        this.value = val;
        return old;
    }
}