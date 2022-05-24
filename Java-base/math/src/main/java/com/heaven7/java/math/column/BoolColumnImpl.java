package com.heaven7.java.math.column;

import com.heaven7.java.base.anno.Nullable;
import com.heaven7.java.base.util.Throwables;
import com.heaven7.java.sort.IndexComparator;
import com.heaven7.java.sort.IndirectSorter;
import com.heaven7.java.sort.QuickSortUtils;

import java.util.Arrays;
import java.util.Comparator;

public class BoolColumnImpl extends AbsPrimitiveColumn {

    private byte[] mData;

    public BoolColumnImpl(int mSize) {
        this.mData = new byte[mSize];
        this.mSize = mSize;
    }
    public BoolColumnImpl(byte[] arr) {
        this.mData = arr;
        this.mSize = arr.length;
    }
    public BoolColumnImpl(boolean[] arr) {
        this.mData = new byte[arr.length];
        this.mSize = arr.length;
        for (int i = 0; i < arr.length; i++) {
            mData[i] = (byte) (arr[i] ? 1 : 0);
        }
    }

    @Override
    public Object get(int index) {
        return mData[index] != Byte.MIN_VALUE ? mData[index] == 1: null;
    }

    @Override
    public void set(int index, String val) {
        try {
            mData[index] = (byte) (Boolean.parseBoolean(val) ? 1 : 0);
        }catch (NumberFormatException e){
            mData[index] = Byte.MIN_VALUE;
        }
    }
    @Override
    public void set0(int index, Object val) {
        if(val == null){
            mData[index] = Byte.MIN_VALUE;
        }else if(val instanceof Boolean){
            mData[index] = (byte) (((Boolean) val) ? 1 : 0);
        }else{
            set(index, val.toString());
        }
    }
    @Override
    public boolean add(int index, Object obj) {
        int c = mSize - index;
        if(mData.length < mSize + 1){
            byte[] arr = new byte[mSize + 1];
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
    public void prepareSize(int size) {
        if(mData.length < size){
            byte[] arr = new byte[size];
            for(int i = 0; i < mSize; i ++ ){
                arr[i] = mData[i];
            }
            mData = arr;
        }
    }

    @Override
    public long computeSize(boolean deep) {
        return (mData.length + 4);
    }

    @Override
    public void swap(int index1, int index2) {
        Throwables.checkArgument(index1 < mSize, "index must < size");
        Throwables.checkArgument(index2 < mSize, "index must < size");
        byte old1 = mData[index1];
        mData[index1] = mData[index2];
        mData[index2] = old1;
    }
    @Override
    public int type() {
        return TYPE_BOOLEAN;
    }
    @Override
    public Class<?> typeClass() {
        return boolean.class;
    }

    @Override
    public IColumn copy() {
        BoolColumnImpl col = new BoolColumnImpl(this.mSize);
        for (int i = 0; i < mSize; i++) {
            col.mData[i] = mData[i];
        }
        return col;
    }
    @Override
    public IColumn subColumn(int start, int end) {
        if(start < 0 || end > size()){
            throw new IllegalArgumentException();
        }
        byte[] arr = new byte[end - start];
        for (int i = start; i < end; i++) {
            arr[i - start] = mData[i];
        }
        return new BoolColumnImpl(arr);
    }
    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
    public IColumn sort(@Nullable Comparator cmp){
        boolean aesc = cmp == null || cmp.compare((byte)2, (byte)1) > 0;
        if(aesc){
            Arrays.sort(mData, 0, mSize);
        }else{
            QuickSortUtils.quickSortDesc(mData, 0, mSize - 1);
        }
        return this;
    }
    @Override
    public IColumn sortIndex(IndexComparator<Object> cmp) {
        return new IntColumnImpl(IndirectSorter.sort(mData, 0, mSize, cmp));
    }
    @Override
    @SuppressWarnings({"rawtypes"})
    public int binarySearch(Object o, Comparator cmp) {
        throw new RuntimeException();
    }
}
