package com.heaven7.java.sort;

import com.heaven7.java.base.anno.Nullable;

import java.util.List;

/**
 * the bubble sort helper. which help we handle dynamic sort with extra resources.
 * @param <T> the type
 */
public final class BubbleSort<T>{

    private final IndexComparator<? super T> cmp;

    public BubbleSort(IndexComparator<? super T> cmp) {
        this.cmp = cmp;
    }

    public void sort(List<T> list, @Nullable Callback cb){
        int rowCount = list.size();
        int L = 0, R = rowCount - 1;
        while(L < R) {
            for(int i = L; i < R; i++) {
                T rowi = list.get(i);
                T rowi1 =  list.get(i + 1);
                if(cmp.compare(i, i + 1, rowi, rowi1) > 0){
                   // swapRow(i, i + 1);
                    list.set(i, rowi1);
                    list.set(i + 1, rowi);
                    if(cb != null){
                        cb.onSwap(i, i + 1);
                    }
                }
            }
            R--;
            for(int i = R; i > L; i--) {
                T rowi = list.get(i);
                T rowi1 =  list.get(i - 1);
                if(cmp.compare(i, i - 1, rowi, rowi1) < 0){
                   // swapRow(i, i - 1);
                    list.set(i, rowi1);
                    list.set(i - 1, rowi);
                    if(cb != null){
                        cb.onSwap(i, i - 1);
                    }
                }
            }
            L++;
        }
    }
    public interface Callback{
        void onSwap(int index1, int index2);
    }
}
