package com.heaven7.java.sort;

/**
 * @author heaven7
 * @since 1.2.6
 */
public final class InsertSortUtils {

    public static void insertSort(short[] arr) {
        int L, R, mid;
        short key;
        for (int i = 1; i < arr.length; i++) {
            key = arr[i];
            L = 0;
            R = i - 1;
            while (L <= R) {
                mid = L + (R - L) / 2;
                if (arr[mid] > key) R = mid - 1;
                else L = mid + 1;
            }
            //after binary. the L is now just at the pos, which is bigger than key,
            if (i - L >= 0) System.arraycopy(arr, L, arr, L + 1, i - L);
            arr[L] = key;
        }
    }
    public static void insertSort(int[] arr) {
        int L, R, mid;
        int key;
        for (int i = 1; i < arr.length; i++) {
            key = arr[i];
            L = 0;
            R = i - 1;
            while (L <= R) {
                mid = L + (R - L) / 2;
                if (arr[mid] > key) R = mid - 1;
                else L = mid + 1;
            }
            //after binary. the L is now just at the pos, which is bigger than key,
            if (i - L >= 0) System.arraycopy(arr, L, arr, L + 1, i - L);
            arr[L] = key;
        }
    }
    public static void insertSort(long[] arr) {
        int L, R, mid;
        long key;
        for (int i = 1; i < arr.length; i++) {
            key = arr[i];
            L = 0;
            R = i - 1;
            while (L <= R) {
                mid = L + (R - L) / 2;
                if (arr[mid] > key) R = mid - 1;
                else L = mid + 1;
            }
            //after binary. the L is now just at the pos, which is bigger than key,
            if (i - L >= 0) System.arraycopy(arr, L, arr, L + 1, i - L);
            arr[L] = key;
        }
    }
    public static void insertSort(float[] arr) {
        int L, R, mid;
        float key;
        for (int i = 1; i < arr.length; i++) {
            key = arr[i];
            L = 0;
            R = i - 1;
            while (L <= R) {
                mid = L + (R - L) / 2;
                if (arr[mid] > key) R = mid - 1;
                else L = mid + 1;
            }
            //after binary. the L is now just at the pos, which is bigger than key,
            if (i - L >= 0) System.arraycopy(arr, L, arr, L + 1, i - L);
            arr[L] = key;
        }
    }
    public static void insertSort(double[] arr) {
        int L, R, mid;
        double key;
        for (int i = 1; i < arr.length; i++) {
            key = arr[i];
            L = 0;
            R = i - 1;
            while (L <= R) {
                mid = L + (R - L) / 2;
                if (arr[mid] > key) R = mid - 1;
                else L = mid + 1;
            }
            //after binary. the L is now just at the pos, which is bigger than key,
            if (i - L >= 0) System.arraycopy(arr, L, arr, L + 1, i - L);
            arr[L] = key;
        }
    }

    public static void insertSortWithCallback(Object arr, int arrLength, Callback cb) {
        int L, R, mid;
        Object key;
        for (int i = 1; i < arrLength; i++) {
            key = cb.getValue(arr, i);
            L = 0;
            R = i - 1;
            while (L <= R) {
                mid = L + (R - L) / 2;
                //if(arr[mid] > key) R = mid - 1;
                if (cb.isBigger(arr, mid, key)) {
                    R = mid - 1;
                } else {
                    L = mid + 1;
                }
            }
            //after binary. the L is now just at the pos, which is bigger than key,
//            for (int j = i - 1; j >= L; j--) {
//                cb.swap(arr, j + 1, j);
//            }
            if(i - L > 0){
                cb.arraycopy(arr, L, L + 1, i - L);
            }
            cb.setValue(arr, L, key);
        }
    }

    public interface Callback {
        Object getValue(Object arr, int index);

        void setValue(Object arr, int index, Object val);

       // void swap(Object arr, int i, int j);

        boolean isBigger(Object arr, int index, Object key);
        /**
         * <pre>
         for (int j = i - 1; j >= L; j--) {
            arr[j + 1] = arr[j]
         }
         * </pre>
         * @param arr the array
         * @param srcPos the src position
         * @param dstPos the dest position
         * @param len the len for copy
         */
        void arraycopy(Object arr, int srcPos, int dstPos, int len);
    }
}
