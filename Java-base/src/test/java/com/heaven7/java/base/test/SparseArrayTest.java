package com.heaven7.java.base.test;

import com.heaven7.java.base.util.SparseArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SparseArrayTest {

    @Test
    public void testOldValue() {
        SparseArray<String> map = new SparseArray<>();
        System.out.println(map.put(1, "123"));
        System.out.println(map.put(1, "456"));
    }
}
