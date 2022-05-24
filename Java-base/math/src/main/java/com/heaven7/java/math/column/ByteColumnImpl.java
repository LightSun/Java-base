package com.heaven7.java.math.column;

import com.heaven7.java.base.anno.Nullable;
import com.heaven7.java.base.util.SearchUtils;
import com.heaven7.java.base.util.Throwables;
import com.heaven7.java.sort.IndexComparator;
import com.heaven7.java.sort.IndirectSorter;
import com.heaven7.java.sort.QuickSortUtils;

import java.util.Arrays;
import java.util.Comparator;

public class ByteColumnImpl extends AbsPrimitiveColumn {

    private byte[] mData;

    public ByteColumnImpl(int mSize) {
        this.mSize = mSize;
        this.mData = new byte[mSize];
    }
    public ByteColumnImpl(byte[] data) {
        this.mSize = data.length;
        this.mData = data;
    }

    @Override
    public Object get(int index) {
        return mData[index] != Byte.MIN_VALUE ? mData[index] : null;
    }

    @Override
    public void set(int index, String val) {
        try {
            mData[index] = Byte.parseByte(val);
        }catch (NumberFormatException e){
            mData[index] = Byte.MIN_VALUE;
        }
    }
    @Override
    public void set0(int index, Object val) {
        if(val == null){
            mData[index] = Byte.MIN_VALUE;
        }else if(val instanceof Byte || val instanceof Integer){
            mData[index] = (byte) val;
        }else{
            set(index, val.toString());
        }
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
        return TYPE_BYTE;
    }
    @Override
    public Class<?> typeClass() {
        return byte.class;
    }

    @Override
    public IColumn copy() {
        ByteColumnImpl col = new ByteColumnImpl(this.mSize);
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
        return new ByteColumnImpl(arr);
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
    @SuppressWarnings({"unchecked","rawtypes"})
    public int binarySearch(Object o, Comparator cmp) {
        if(o == null){
            return -1;
        }
        boolean aesc = cmp == null || cmp.compare((byte)2, (byte)1) > 0;
        if(aesc){
            return SearchUtils.binarySearch(mData, 0, mSize, (byte)o);
        }
        return SearchUtils.binarySearchDesc(mData, 0, mSize, (byte)o);
    }
}
