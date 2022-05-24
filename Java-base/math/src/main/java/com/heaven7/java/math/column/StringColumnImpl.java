package com.heaven7.java.math.column;

import com.heaven7.java.base.anno.Nullable;
import com.heaven7.java.base.util.SearchUtils;
import com.heaven7.java.base.util.Throwables;
import com.heaven7.java.sort.IndexComparator;
import com.heaven7.java.sort.IndirectSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StringColumnImpl extends AbsPrimitiveColumn{

    private String[] mData;

    public StringColumnImpl(List<String> list) {
        mData = list.toArray(new String[0]);
        mSize = list.size();
    }
    public StringColumnImpl(String[] list) {
        mData = list;
        mSize = list.length;
    }
    public StringColumnImpl() {
        mData = new String[0];
        mSize = 0;
    }
    public StringColumnImpl(int initSize){
        mSize = initSize;
        mData = new String[initSize];
    }
    @Override
    public int type() {
        return TYPE_STRING;
    }
    @Override
    public Class<?> typeClass() {
        return String.class;
    }

    @Override
    public Object get(int index) {
        return mData[index];
    }

    @Override
    public void set(int index, String val) {
        mData[index] = val;
    }
    @Override
    public void set0(int index, Object val) {
       if(val instanceof String){
            mData[index] = (String) val;
        }else{
            set(index, val != null ? val.toString() : null);
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
            String[] arr = new String[mSize + 1];
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
    public long computeSize(boolean deep) {
        long size = 4;
        for (int i = 0; i < mSize; i++) {
            size += mData[i] != null ? mData[i].getBytes().length + 40 : 0;
        }
        return size;
    }

    @Override
    public void swap(int index1, int index2) {
        Throwables.checkArgument(index1 < mSize, "index must < size");
        Throwables.checkArgument(index2 < mSize, "index must < size");
        String old1 = mData[index1];
        mData[index1] = mData[index2];
        mData[index2] = old1;
    }
    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
    public IColumn sort(@Nullable Comparator cmp){
        Arrays.sort(mData, cmp);
        return this;
    }
    @Override
    public void prepareSize(int size) {
        if(mData.length < size){
            String[] arr = new String[size];
            for(int i = 0; i < mSize; i ++ ){
                arr[i] = mData[i];
            }
            mData = arr;
        }
    }
    @Override
    public IColumn copy() {
        StringColumnImpl col = new StringColumnImpl(this.mSize);
        for (int i = 0; i < mSize; i++) {
            col.mData[i] = mData[i];
        }
        return col;
    }
    @Override
    public IColumn asInt(int defVal) {
        int[] arr = new int[mSize];
        for (int i = 0; i < mSize; i++) {
            try{
                arr[i] = Integer.parseInt(getString(i, ""));
            }catch(NumberFormatException e){
                arr[i] = defVal;
            }
        }
        return new IntColumnImpl(arr);
    }
    @Override
    public IColumn asFloat(float defVal) {
        float[] arr = new float[mSize];
        for (int i = 0; i < mSize; i++) {
            try{
                arr[i] = Float.parseFloat(getString(i, ""));
            }catch(NumberFormatException e){
                arr[i] = defVal;
            }
        }
        return new FloatColumnImpl(arr);
    }
    @Override
    public IColumn subColumn(int start, int end) {
        if(start < 0 || end > size()){
            throw new IllegalArgumentException();
        }
        String[] arr = new String[end - start];
        for (int i = start; i < end; i++) {
            arr[i - start] = mData[i];
        }
        return new StringColumnImpl(arr);
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
        ArrayList<String> list = new ArrayList<>(Arrays.asList(mData));
        boolean aesc = cmp == null || cmp.compare("2", "1") > 0;
        if(aesc){
            return SearchUtils.binarySearch(list, 0, mSize, (String)o, cmp);
        }
        return SearchUtils.binarySearchDesc(list, 0, mSize, (String)o, cmp);
    }
}
