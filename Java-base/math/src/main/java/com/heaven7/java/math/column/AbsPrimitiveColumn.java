package com.heaven7.java.math.column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbsPrimitiveColumn implements IColumn {

    protected int mSize = 0;

    @Override
    public int size() {
        return mSize;
    }

    @Override
    public void setSize(int size) {
        if (mSize != size) {
            prepareSize(size);
            this.mSize = size;
        }
    }

    @Override
    public void clear() {
        mSize = 0;
    }

    @Override
    public void add(String s) {
        addAll(Arrays.asList(s));
    }

    @Override
    public final void addAll(List<String> column) {
        final int preSize = this.mSize;
        setSize(preSize + column.size());
        int c = column.size();
        for (int i = 0; i < c; i++) {
            set(i + preSize, column.get(i));
        }
    }

    @Override
    public List<String> toListString() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mSize; i++) {
            Object o = get(i);
            list.add(o != null ? o.toString() : "");
        }
        return list;
    }

    @Override
    public IColumn asInt(int defVal) {
        int[] arr = new int[mSize];
        for (int i = 0; i < mSize; i++) {
            Object val = get(i);
            arr[i] = val instanceof Number ? ((Number) val).intValue() : defVal;
        }
        return new IntColumnImpl(arr);
    }

    @Override
    public IColumn asFloat(float defVal) {
        float[] arr = new float[mSize];
        for (int i = 0; i < mSize; i++) {
            Object val = get(i);
            arr[i] = val instanceof Number ? ((Number) val).floatValue() : defVal;
        }
        return new FloatColumnImpl(arr);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public IColumn unique() {
        List list = new ArrayList();
        int size = size();
        Object o;
        for (int i = 0; i < size; i++) {
            o = get(i);
            if (!list.contains(o)) {
                list.add(o);
            }
        }
        //copy
        size = list.size();
        for (int i = 0; i < size; i++) {
            set(i, list.get(i));
        }
        this.mSize = size;
        return this;
    }

    @Override
    public Object set(int index, Object val) {
        Object o = get(index);
        set0(index, val);
        return o;
    }

    @Override
    public Object removeAt(int index) {
        Object o = get(index);
        removeAt0(index);
        return o;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        int size = size();
        for (int i = 0; i < size; i++) {
            sb.append(getString(i, ""));
            if(i != size - 1){
                sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbsPrimitiveColumn that = (AbsPrimitiveColumn) o;
        if(mSize != that.mSize){
            return false;
        }
        Object o1;
        for (int i = 0; i < mSize; i++) {
            o1 = get(i);
            if(o1 != null){
                if(!o1.equals(that.get(i))){
                    return false;
                }
            }else{
                if(that.get(i) != null){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object o;
        for (int i = 0; i < mSize; i++) {
            o = get(i);
            result = 31 * result + (o != null ? o.hashCode() : 0);
        }
        return result;
    }
}