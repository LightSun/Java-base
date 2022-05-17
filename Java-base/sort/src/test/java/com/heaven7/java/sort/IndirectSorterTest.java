package com.heaven7.java.sort;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class IndirectSorterTest {
    @Test
    public void test1() {
        String[] origin = {
                "9", "8", "1", "5", "2"
        };
        String[] args = Arrays.copyOf(origin, origin.length);
        int[] indexes = IndirectSorter.sort(args, 0, args.length, new IndexComparator<String>() {
            @Override
            public int compare(int index1, int index2, String o1, String o2) {
                System.out.printf("index1, index2 = (%d, %d)%n", index1, index2);
               // System.out.printf("o1, o2 = (%s, %s)%n", o1, o2);
               // System.out.printf("raw o1, o2 = (%s, %s)%n", origin[index1], origin[index2]);
                assertEquals(o1, origin[index1]);
                assertEquals(o2, origin[index2]);
                return o1.compareTo(o2);
            }
        });
        for (int i : indexes) {
            System.out.printf("original pos: %d, val =  %s\n", i, origin[i]);
        }
    }
    @Test
    public void test2() {
        String[] args = {
                "9", "8", "1", "5", "2"
        };
        int[] indexes = IndirectSorter.sort(args, 1, 4, new IndexComparator<String>() {
            @Override
            public int compare(int index1, int index2, String o1, String o2) {
                System.out.printf("index1, index2 = (%d, %d)%n", index1, index2);
                return o1.compareTo(o2);
            }
        });
        for (int i : indexes) {
            System.out.printf("original pos: %d %s\n", i, args[i]);
        }
        boolean ret = Arrays.equals(indexes, new int[]{2, 3, 1});
        assertTrue(ret);
    }

    @Test
    public void test3(){
        int[] arr = {3569038, 48, 49, 49, 99};
        int[] carr = Arrays.copyOf(arr, arr.length);
        int[] indexes = IndirectSorter.sort(carr, 0, arr.length, (IndexComparator<? super Integer>) null);
        for (int i : indexes) {
            System.out.printf("original pos: %d %s\n", i, arr[i]);
        }
        boolean ret = Arrays.equals(indexes, new int[]{1, 2, 3, 4, 0});
        assertTrue(ret);
        System.out.println(Arrays.toString(carr));
    }
}
