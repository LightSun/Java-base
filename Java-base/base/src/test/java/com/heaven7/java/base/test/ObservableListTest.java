package com.heaven7.java.base.test;

import com.heaven7.java.base.util.Logger;
import com.heaven7.java.base.util.ObservableList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author heaven7
 */
public class ObservableListTest {

    private final ObservableList<Integer> mList = new ObservableList<Integer>(new ArrayList<Integer>(), new LogCallback<Integer>());

    @Test
    public void test1(){
        mList.add(1);
        mList.add(2);
        mList.remove((Object)2);
        mList.set(0, 199);

        mList.add(200);
        mList.clear();
    }

    private static class LogCallback<E> implements ObservableList.Callback<E>{
        private static final String TAG = "LogCallback";
        @Override
        public void onRemove(ObservableList<E> origin, int index, E ele) {
            Logger.d(TAG, "onRemove", "index = " + index + " ,value = " + ele);
        }
        @Override
        public void onAdd(ObservableList<E> origin, int index, E ele) {
            Logger.d(TAG, "onAdd", "index = " + index + " ,value = " + ele);
        }
        @Override
        public void onSet(ObservableList<E> origin, int index, E old, E newE) {
            Logger.d(TAG, "onSet", "index = " + index + " ,old value = "
                    + old + " ,new value = " + newE);
        }
        @Override
        public void onClear(ObservableList<E> origin, List<E> old) {
            Logger.d(TAG, "onClear", "old list  = " + old);
        }

        @Override
        public void onBatchRemove(ObservableList<E> origin, Collection<E> batch) {
            Logger.d(TAG, "onBatchRemove", "batch  = " + batch);
        }
        @Override
        public void onBatchAdd(ObservableList<E> origin, int startIndex, Collection<E> batch) {
            Logger.d(TAG, "onBatchAdd", "startIndex = " + startIndex + " , batch  = " + batch);
        }

        @Override
        public void onUpdate(ObservableList<E> origin, int index, E ele) {

        }
        @Override
        public void onBatchChanged(ObservableList<E> origin, List<E> batch) {

        }
        @Override
        public void onAllChanged(ObservableList<E> origin) {

        }
    }
}
