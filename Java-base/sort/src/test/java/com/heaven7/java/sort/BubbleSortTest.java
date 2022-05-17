package com.heaven7.java.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BubbleSortTest {

    @Test
    public void test1(){
        List<String> cases = new ArrayList<>(Arrays.asList("a", "e", "c", "c", "b", "a"));
        List<String> list = new ArrayList<>(Arrays.asList(
                "5","1","3","9","3","2"
        ));
        BubbleSort<String> bubbleSort = new BubbleSort<>(new IndexComparator<String>() {
            @Override
            public int compare(int index1, int index2, String o1, String o2) {
                String s = cases.get(index1);
                String s2 = cases.get(index2);
                int ret = s.compareTo(s2);
                if(ret != 0){
                    return ret;
                }
                return o1.compareTo(o2); //AESC
            }
        });
        bubbleSort.sort(list, new BubbleSort.Callback() {
            @Override
            public void onSwap(int index1, int index2) {
                System.out.println("index1 = " + index1 + " ,index2 = " + index2);
                String old1 = cases.get(index1);
                cases.set(index1, cases.get(index2));
                cases.set(index2, old1);
            }
        });
        System.out.println("cases ---- list");
        System.out.println(cases);
        System.out.println(list);
        assertEquals(cases.toString(), "[a, a, b, c, c, e]");
        assertEquals(list.toString(), "[2, 5, 3, 3, 9, 1]");
    }
}
