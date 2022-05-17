package com.heaven7.java.sort;

/**
 * @author heaven7
 * @since 1.2.6
 */
public final class SortUtils {

    public static void insertSort(int[] arr) {
        for(int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int L = 0, R = i-1;
            while(L <= R) {
                int mid = L + (R-L)/2;
                if(arr[mid] > key) R = mid - 1;
                else L = mid + 1;
            }
            //after binary. the L is now just at the pos, which is bigger than key,
            for(int j = i-1; j >= L; j--){
                arr[j+1] = arr[j];
            }
            arr[L] = key;
        }
    }
    public static void insertSortWithCallback(int[] arr, Callback cb) {
        for(int i = 1; i < arr.length; i++) {
            int key = arr[i];
            cb.saveValue(arr, i);
            int L = 0, R = i-1;
            while(L <= R) {
                int mid = L + (R-L)/2;
                if(arr[mid] > key) R = mid - 1;
                else L = mid + 1;
            }
            //after binary. the L is now just at the pos, which is bigger than key,
            for(int j = i-1; j >= L; j--){
                arr[j+1] = arr[j];
                cb.swap(arr, j + 1, j);
            }
            arr[L] = key;
            cb.restoreValueTo(arr, L);
        }
    }
    public interface Callback{
        void saveValue(Object arr,int index);
        void restoreValueTo(Object arr,int target);
        void swap(Object arr,int i, int j);
    }
}
