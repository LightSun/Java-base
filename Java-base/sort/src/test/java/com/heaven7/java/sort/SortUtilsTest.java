package com.heaven7.java.sort;

import com.heaven7.java.base.util.SearchUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SortUtilsTest {

    @Test
    public void test1() {
        int[] arr = {
                5, 1, 2, 9, 8, 3, 2, 2
        };
        int[] retArr = Arrays.copyOf(arr, arr.length);
        SortUtils.insertSort(retArr);
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
        SortUtils.insertSortWithCallback(retArr, new SortUtils.Callback() {
            int val ;
            @Override
            public void saveValue(Object arr, int index) {
               // val = retArr[index];
                System.out.println("saveValue: " + index + ", " + retArr[index]);
            }
            @Override
            public void restoreValueTo(Object arr, int target) {
               // retArr[target] = val;
                System.out.println("restoreValueTo: " + target);
            }
            @Override
            public void swap(Object arr, int i, int j) {
                System.out.println("swap value: i = " + i + " ,j = " + j);
//                int old = retArr[i];
//                retArr[i] = retArr[j];
//                retArr[j] = old;
            }
        });
        System.out.println(Arrays.toString(retArr));
    }
}
