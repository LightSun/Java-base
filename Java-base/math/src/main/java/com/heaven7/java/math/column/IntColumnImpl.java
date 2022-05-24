package com.heaven7.java.math.column;

import com.heaven7.java.base.anno.Nullable;
import com.heaven7.java.base.util.SearchUtils;
import com.heaven7.java.base.util.Throwables;
import com.heaven7.java.sort.IndexComparator;
import com.heaven7.java.sort.IndirectSorter;
import com.heaven7.java.sort.QuickSortUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class IntColumnImpl extends AbsPrimitiveColumn {

    private int[] mData;

    public IntColumnImpl(List<Integer> list) {
        this(list.size());
        for (int i = 0; i < list.size(); i++) {
            Integer o = list.get(i);
            mData[i] = o != null ? o : Integer.MIN_VALUE;
        }
    }
    public IntColumnImpl(int size){
        this.mData = new int[size];
        this.mSize = size;
    }

    public IntColumnImpl(int[] starts) {
        this.mSize = starts.length;
        this.mData = starts;
       // Arrays.hashCode(mData)
    }

    @Override
    public Object get(int index) {
        return mData[index] != Integer.MIN_VALUE ? mData[index] : null;
    }

    @Override
    public void set(int index, String val) {
        try {
            mData[index] = Integer.parseInt(val);
        }catch (NumberFormatException e){
            mData[index] = Integer.MIN_VALUE;
        }
    }
    @Override
    public void set0(int index, Object val) {
        if(val == null){
            mData[index] = Integer.MIN_VALUE;
        }else if(val instanceof Integer){
            mData[index] = (int) val;
        }else{
            set(index, val.toString());
        }
    }
    @Override
    public boolean add(int index, Object obj) {
        int c = mSize - index;
        if(mData.length < mSize + 1){
            int[] arr = new int[mSize + 1];
            for(int i = 0; i < index; i ++ ){
                arr[i] = mData[i];
            }
            for (int i = c - 1; i >= 0; i++) {
                arr[i + index + 1] = mData[i + index];
            }
            mData = arr;
        }else{
            for (int i = c - 1; i >= 0; i++) {
                mData[i + index + 1] = mData[i + index];
            }
        }
        mSize ++;
        return true;
    }

    @Override
    public void removeAt0(int index) {
        if(index >= mSize - 1){
            return;
        }
        for (int i = index; i < mSize - 1; i++) {
            mData[i] = mData[i + 1];
        }
        mSize --;
    }
    @Override
    public long computeSize(boolean deep) {
        return (long) (mData.length + 1) << 2;
    }

    @Override
    public void swap(int index1, int index2) {
        Throwables.checkArgument(index1 < mSize, "index must < size");
        Throwables.checkArgument(index2 < mSize, "index must < size");
        int old1 = mData[index1];
        mData[index1] = mData[index2];
        mData[index2] = old1;
    }
    @Override
    public void prepareSize(int size) {
        if(mData.length < size){
            int[] arr = new int[size];
            for(int i = 0; i < mSize; i ++ ){
                arr[i] = mData[i];
            }
            mData = arr;
        }
    }
    @Override
    public int type() {
        return TYPE_INT;
    }
    @Override
    public Class<?> typeClass() {
        return int.class;
    }

    @Override
    public IColumn copy() {
        IntColumnImpl col = new IntColumnImpl(this.mSize);
        for (int i = 0; i < mSize; i++) {
            col.mData[i] = mData[i];
        }
        return col;
    }
    @Override
    public IColumn asInt(int defVal) {
        return this;
    }
    @Override
    public IColumn subColumn(int start, int end) {
        if(start < 0 || end > size()){
            throw new IllegalArgumentException();
        }
        int[] arr = new int[end - start];
        for (int i = start; i < end; i++) {
            arr[i - start] = mData[i];
        }
        return new IntColumnImpl(arr);
    }
    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
    public IColumn sort(@Nullable Comparator cmp){
        boolean aesc = cmp == null || cmp.compare(2, 1) > 0;
        if(aesc){
            Arrays.sort(mData, 0, mSize);
        }else{
            QuickSortUtils.quickSortDesc(mData, 0, mSize - 1);
        }
        return this;
    }
    @Override
    @SuppressWarnings({"unchecked","rawtypes"})
    public int binarySearch(Object o, Comparator cmp) {
        if(o == null){
            return -1;
        }
        boolean aesc = cmp == null || cmp.compare(2, 1) > 0;
        if(aesc){
            return SearchUtils.binarySearch(mData, 0, mSize, (int)o);
        }
        return SearchUtils.binarySearchDesc(mData, 0, mSize, (int)o);
    }
    @Override
    public IColumn sortIndex(IndexComparator<Object> cmp) {
        return new IntColumnImpl(IndirectSorter.sort(mData, 0, mSize, cmp));
    }
}
