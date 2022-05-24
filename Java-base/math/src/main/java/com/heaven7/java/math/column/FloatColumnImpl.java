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


public class FloatColumnImpl extends AbsPrimitiveColumn {

    private float[] mData;

    public FloatColumnImpl(int mSize) {
        this.mSize = mSize;
        this.mData = new float[mSize];
    }

    public FloatColumnImpl(float[] vals) {
        this.mSize = vals.length;
        this.mData = vals;
    }
    public FloatColumnImpl(List<Float> list) {
        this(list.size());
        for (int i = 0; i < list.size(); i++) {
            Float o = list.get(i);
            mData[i] = o != null ? o : Float.NaN;
        }
    }

    @Override
    public Object get(int index) {
        return Float.isNaN(mData[index]) ? null : mData[index];
    }

    @Override
    public void set(int index, String val) {
        try {
            mData[index] = Float.parseFloat(val);
        }catch (NumberFormatException e){
            mData[index] = Float.NaN;
        }
    }
    @Override
    public void set0(int index, Object val) {
        if(val == null){
            mData[index] = Float.NaN;
        }else if(val instanceof Float || val instanceof Integer){
            mData[index] = (float) val;
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
            float[] arr = new float[mSize + 1];
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
        return (long) (mData.length + 1) << 2;
    }

    @Override
    public void swap(int index1, int index2) {
        Throwables.checkArgument(index1 < mSize, "index must < size");
        Throwables.checkArgument(index2 < mSize, "index must < size");
        float old1 = mData[index1];
        mData[index1] = mData[index2];
        mData[index2] = old1;
    }
    @Override
    public void prepareSize(int size) {
        if(mData.length < size){
            float[] arr = new float[size];
            for(int i = 0; i < mSize; i ++ ){
                arr[i] = mData[i];
            }
            mData = arr;
        }
    }
    @Override
    public int type() {
        return TYPE_FLOAT;
    }
    @Override
    public Class<?> typeClass() {
        return float.class;
    }

    @Override
    public IColumn copy() {
        FloatColumnImpl col = new FloatColumnImpl(this.mSize);
        for (int i = 0; i < mSize; i++) {
            col.mData[i] = mData[i];
        }
        return col;
    }
    @Override
    public IColumn asFloat(float defVal) {
        return this;
    }
    @Override
    public IColumn subColumn(int start, int end) {
        if(start < 0 || end > size()){
            throw new IllegalArgumentException();
        }
        float[] arr = new float[end - start];
        for (int i = start; i < end; i++) {
            arr[i - start] = mData[i];
        }
        return new FloatColumnImpl(arr);
    }
    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
    public IColumn sort(@Nullable Comparator cmp){
        boolean aesc = cmp == null || cmp.compare(2f, 1f) > 0;
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
        boolean aesc = cmp == null || cmp.compare(2f, 1f) > 0;
        if(aesc){
            return SearchUtils.binarySearch(mData, 0, mSize, (float)o);
        }
        return SearchUtils.binarySearchDesc(mData, 0, mSize, (float)o);
    }
}
