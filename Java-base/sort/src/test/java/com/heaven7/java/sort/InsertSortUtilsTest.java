package com.heaven7.java.sort;

import com.heaven7.java.base.util.SearchUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class InsertSortUtilsTest {

    @Test
    public void test1() {
        int[] arr = {
                5, 1, 2, 9, 8, 3, 2, 2
        };
        int[] retArr = Arrays.copyOf(arr, arr.length);
        InsertSortUtils.insertSort(retArr);
        System.out.println(Arrays.toString(retArr));
        int index = SearchUtils.binarySearch(retArr, 0, retArr.length, 2);
        System.out.println("index = " + index);
        assertEquals(index, 1);
    }

    @Test
    public void test2() {
        int[] arr = {
                5, 1, 2, 9, 8, 3, 2, 2
        };
        int[] retArr = Arrays.copyOf(arr, arr.length);
        InsertSortUtils.insertSortWithCallback(retArr, retArr.length, new InsertSortUtils.Callback() {
            @Override
            public Object getValue(Object arr, int index) {
               // val = retArr[index];
                System.out.println("saveValue: " + index + ", " + retArr[index]);
                return retArr[index];
            }
            @Override
            public void setValue(Object arr, int index, Object val) {
                retArr[index] = (Integer) val;
                System.out.println("restoreValueTo: " + index);
            }

            @Override
            public void arraycopy(Object arr, int srcPos, int dstPos, int len) {
                System.out.println("swap value: i = " + srcPos + " ,j = " + dstPos + " , len = " + len);
                System.arraycopy(retArr, srcPos, retArr, dstPos, len);
            }
            @Override
            public boolean isBigger(Object arr, int index, Object key) {
                return retArr[index] > (Integer) key;
            }
        });
        System.out.println(Arrays.toString(retArr));
    }
}
