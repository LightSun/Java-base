package com.heaven7.java.sort;

import java.util.Comparator;
import java.util.LinkedList;

public final class QuickSortUtils {

    private static final long MAX_LEN = 128;

    public static <T> void quickSortDesc(T[] arr, int l, int r, Comparator<? super T> cmp) {
        if(arr.length <= MAX_LEN){
            quickSortDesc0(arr, l, r, cmp);
        }else{
            LinkedList<SortTask> tasks = new LinkedList<>();
            quickSortDesc1(arr, l, r, cmp, tasks);
            SortTask task;
            for(;;){
                task = tasks.pollFirst();
                if(task != null){
                    quickSortDesc1(arr, task.begin, task.end, cmp, tasks);
                }else{
                    break;
                }
            }
        }
    }
    public static void quickSortDesc(int[] arr, int l, int r) {
        if(arr.length <= MAX_LEN){
            quickSortDesc0(arr, l, r);
        }else{
            LinkedList<SortTask> tasks = new LinkedList<>();
            quickSortDesc1(arr, l, r, tasks);
            SortTask task;
            for(;;){
                task = tasks.pollFirst();
                if(task != null){
                    quickSortDesc1(arr, task.begin, task.end, tasks);
                }else{
                    break;
                }
            }
        }
    }
    public static void quickSortDesc(float[] arr, int l, int r) {
        if(arr.length <= MAX_LEN){
            quickSortDesc0(arr, l, r);
        }else{
            LinkedList<SortTask> tasks = new LinkedList<>();
            quickSortDesc1(arr, l, r, tasks);
            SortTask task;
            for(;;){
                task = tasks.pollFirst();
                if(task != null){
                    quickSortDesc1(arr, task.begin, task.end, tasks);
                }else{
                    break;
                }
            }
        }
    }
    public static void quickSortDesc(byte[] arr, int l, int r) {
        if(arr.length <= MAX_LEN){
            quickSortDesc0(arr, l, r);
        }else{
            LinkedList<SortTask> tasks = new LinkedList<>();
            quickSortDesc1(arr, l, r, tasks);
            SortTask task;
            for(;;){
                task = tasks.pollFirst();
                if(task != null){
                    quickSortDesc1(arr, task.begin, task.end, tasks);
                }else{
                    break;
                }
            }
        }
    }

    private static void quickSortDesc1(int[] arr, int l, int r, LinkedList<SortTask> list) {
        int i = l, j = r;
        int A = arr[l];
        while (i < j) {
            while (i < j && A > arr[j]) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && A < arr[i]) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = A;
        if (l < i) {
            list.addLast(new SortTask(l, i - 1));
            // quickSortDesc(arr, l, i - 1);
        }
        if (r > i) {
            list.addLast(new SortTask(i + 1, r));
            // quickSortDesc(arr, i + 1, r);
        }
    }
    private static void quickSortDesc0(int[] arr, int l, int r) {
        int i = l, j = r;
        int A = arr[l];
        while (i < j) {
            while (i < j && A > arr[j]) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && A < arr[i]) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = A;
        if (l < i) {
            quickSortDesc0(arr, l, i - 1);
        }
        if (r > i) {
            quickSortDesc0(arr, i + 1, r);
        }
    }
    private static void quickSortDesc1(float[] arr, int l, int r, LinkedList<SortTask> list) {
        int i = l, j = r;
        float A = arr[l];
        while (i < j) {
            while (i < j && A > arr[j]) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && A < arr[i]) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = A;
        if (l < i) {
            list.addLast(new SortTask(l, i - 1));
            // quickSortDesc(arr, l, i - 1);
        }
        if (r > i) {
            list.addLast(new SortTask(i + 1, r));
            // quickSortDesc(arr, i + 1, r);
        }
    }
    private static void quickSortDesc0(float[] arr, int l, int r) {
        int i = l, j = r;
        float A = arr[l];
        while (i < j) {
            while (i < j && A > arr[j]) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && A < arr[i]) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = A;
        if (l < i) {
            quickSortDesc0(arr, l, i - 1);
        }
        if (r > i) {
            quickSortDesc0(arr, i + 1, r);
        }
    }
    private static void quickSortDesc1(byte[] arr, int l, int r, LinkedList<SortTask> list) {
        int i = l, j = r;
        byte A = arr[l];
        while (i < j) {
            while (i < j && A > arr[j]) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && A < arr[i]) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = A;
        if (l < i) {
            list.addLast(new SortTask(l, i - 1));
            // quickSortDesc(arr, l, i - 1);
        }
        if (r > i) {
            list.addLast(new SortTask(i + 1, r));
            // quickSortDesc(arr, i + 1, r);
        }
    }
    private static void quickSortDesc0(byte[] arr, int l, int r) {
        int i = l, j = r;
        byte A = arr[l];
        while (i < j) {
            while (i < j && A > arr[j]) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && A < arr[i]) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = A;
        if (l < i) {
            quickSortDesc0(arr, l, i - 1);
        }
        if (r > i) {
            quickSortDesc0(arr, i + 1, r);
        }
    }
    private static <T> void quickSortDesc1(T[] arr, int l, int r, Comparator<? super T> cmp, LinkedList<SortTask> list) {
        int i = l, j = r;
        T A = arr[l];
        while (i < j) {
            while (i < j && cmp.compare(A, arr[j]) > 0 ) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && cmp.compare(A, arr[i]) < 0) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = A;
        if (l < i) {
            list.addLast(new SortTask(l, i - 1));
            // quickSortDesc(arr, l, i - 1);
        }
        if (r > i) {
            list.addLast(new SortTask(i + 1, r));
            // quickSortDesc(arr, i + 1, r);
        }
    }
    private static <T> void quickSortDesc0(T[] arr, int l, int r, Comparator<? super T> cmp) {
        int i = l, j = r;
        T A = arr[l];
        while (i < j) {
            while (i < j && cmp.compare(A, arr[j]) > 0 ) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && cmp.compare(A, arr[i]) < 0) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = A;
        if (l < i) {
            quickSortDesc0(arr, l, i - 1, cmp);
        }
        if (r > i) {
            quickSortDesc0(arr, i + 1, r, cmp);
        }
    }
}
