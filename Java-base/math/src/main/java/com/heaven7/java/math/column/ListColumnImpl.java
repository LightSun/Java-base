package com.heaven7.java.math.column;

import com.heaven7.java.base.util.SearchUtils;
import com.heaven7.java.sort.IndexComparator;
import com.heaven7.java.sort.IndirectSorter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ListColumnImpl<T> extends BaseListColumn<T> {

    protected final List<T> mList;

    public ListColumnImpl(List<T> list) {
        mList = list != null ? list : new ArrayList<>();
    }
    public ListColumnImpl() {
        this(null);
    }

    @Override
    protected Type getElementType0() {
        if(!mList.isEmpty()){
            return mList.get(0).getClass();
        }
       /* Type clazz = mList.getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType)clazz;
        return pt.getActualTypeArguments()[0];*/
        return null;
    }

    @Override
    public int size() {
        return mList.size();
    }
    @Override
    public Object get(int index) {
        return mList.get(index);
    }

    @Override
    public void set(int index, String val) {
        set(index, toElement(val));
    }
    @Override @SuppressWarnings("unchecked")
    public Object set(int index, Object val) {
        return mList.set(index, (T) val);
    }
    @SuppressWarnings("unchecked")
    @Override
    public boolean add(Object t) {
        return mList.add((T) t);
    }

    @Override
    public boolean remove(Object t) {
        return mList.remove(t);
    }
    @Override @SuppressWarnings("unchecked")
    public List<String> toListString() {
        if(getElementType() == String.class){
            return (List<String>) mList;
        }
        List<String> list = new ArrayList<>();
        T t;
        for (int i = 0; i < mList.size(); i++) {
            t = mList.get(i);
            list.add(t != null ? t.toString() : "");
        }
        return list;
    }
    @Override
    public void clear() {
        mList.clear();
    }
    @Override
    public Object removeAt(int index) {
        return mList.remove(index);
    }
    @Override
    public void addAll(List<String> column) {
        for (String val : column){
            mList.add(toElement(val));
        }
    }
    @Override
    public void add(String s) {
        mList.add(toElement(s));
    }
    @Override
    public long computeSize(boolean deep) {
        //return CollectionUtils.strsSize(mList, deep);
        TypeResolver resolver = getTypeResolver();
        Object context = getContext();
        long size = mList.size();
        for (int i = 0; i < size; i++) {
            size += resolver.sizeOf(context, get(i));
        }
        return size;
    }
    @Override
    public void swap(int index1, int index2) {
        T s1 = mList.get(index1);
        mList.set(index1, mList.get(index2));
        mList.set(index2, s1);
    }
    @Override
    public void setSize(int size) {
        int c = size - this.size();
        for (int i = 0; i < c; i++) {
            mList.add(null);
        }
        //current > size
        if(c < 0){
            c = -c;
            for (int i = 0; i < c; i++) {
                mList.remove(mList.size() - 1);
            }
        }
    }
    @Override @SuppressWarnings("unchecked")
    public boolean add(int index, Object obj) {
        mList.add(index, (T) obj);
        return true;
    }

    @Override  @SuppressWarnings("unchecked")
    public void set0(int index, Object val) {
        mList.set(index, (T) val);
    }
    @Override
    public void removeAt0(int index) {
        mList.remove(index);
    }
    @Override
    public void prepareSize(int size) {

    }
    @Override
    public int type() {
        return TYPE_LIST;
    }

    @Override
    public Type typeClass() {
        return getElementType();
    }

    @Override
    public IColumn copy() {
        return new ListColumnImpl<>(new ArrayList<>(mList));
    }

    @Override
    public IColumn asInt(int defVal) {
        TypeResolver resolver = getTypeResolver();
        Object context = getContext();
        Integer val;
        int[] arr = new int[mList.size()];
        for (int i = 0; i < arr.length; i++) {
            val = resolver.getInt(context, get(i));
            arr[i] = val != null ? val : defVal;
        }
        return new IntColumnImpl(arr);
    }
    @Override
    public IColumn asFloat(float defVal) {
        TypeResolver resolver = getTypeResolver();
        Object context = getContext();
        Float val;
        float[] arr = new float[mList.size()];
        for (int i = 0; i < arr.length; i++) {
            val = resolver.getFloat(context, get(i));
            arr[i] = val != null ? val : defVal;
        }
        return new FloatColumnImpl(arr);
    }
    @Override
    public IColumn subColumn(int start, int end) {
        return new ListColumnImpl<T>(new ArrayList<>(mList.subList(start, end)));
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public IColumn sort(Comparator cmp) {
        mList.sort(cmp);
        return this;
    }
    @Override
    public IColumn unique() {
        List<T> list = new ArrayList<>();
        T s;
        for (int i = 0; i < mList.size(); i++) {
            s = mList.get(i);
            if(!list.contains(s)){
                list.add(s);
            }
        }
        mList.clear();
        mList.addAll(list);
        return this;
    }
    @Override
    public IColumn sortIndex(IndexComparator<Object> cmp) {
        return new IntColumnImpl(IndirectSorter.sort(mList, 0, mList.size(), cmp));
    }
    //---------------------------------------------------------------------
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public int binarySearch(Object o, Comparator cmp) {
        if(o == null){
            return -1;
        }
        return SearchUtils.binarySearch(mList, 0, mList.size(), (T)o, cmp);
    }
    @Override
    public List asList() {
        return mList;
    }

    @Override
    public String toString() {
        return mList.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListColumnImpl<?> that = (ListColumnImpl<?>) o;
        return Objects.equals(mList, that.mList);
    }
    @Override
    public int hashCode() {
        return Objects.hash(mList);
    }
}
