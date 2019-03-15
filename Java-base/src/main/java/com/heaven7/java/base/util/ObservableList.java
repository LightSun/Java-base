package com.heaven7.java.base.util;

import com.heaven7.java.base.anno.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @param <E> the element type
 * Created by heaven7 on 2018/11/2 0002.
 * @since 1.1.4
 * @update 1.1.7
 */
public class ObservableList<E> implements List<E> {

    /**
     * the callback of observe list
     * @param <E> the element type
     */
    public interface Callback<E> {

        /**
         * called on remove element
         * @param origin the observe list
         * @param index the index of element
         * @param ele the element
         */
        void onRemove(ObservableList<E> origin, int index, E ele);
        /**
         * called on add element
         * @param origin the observe list
         * @param index the index of element
         * @param ele the element
         */
        void onAdd(ObservableList<E> origin, int index, E ele);
        /**
         * called on set new element
         * @param origin the observe list
         * @param index the index of element
         * @param old the old element
         * @param newE the new element
         */
        void onSet(ObservableList<E> origin, int index, E old, E newE);
        /**
         * called on clear all
         * @param origin the observe list
         * @param old the elements which were cleared
         */
        void onClear(ObservableList<E> origin, List<E> old);
        /**
         * called on batch remove
         * @param origin the observe list
         * @param batch the elements which were removed
         */
        void onBatchRemove(ObservableList<E> origin, Collection<E> batch);
        /**
         * called on batch add
         * @param origin the observe list
         * @param startIndex the start index of add
         * @param batch the elements which were added
         */
        void onBatchAdd(ObservableList<E> origin, int startIndex, Collection<E> batch);

        /**
         * called on update element data. Note this need manually call by {@linkplain ObservableList#notifyUpdate(int, Object)}/
         * {@linkplain ObservableList#notifyUpdate(Object)} /{@linkplain ObservableList#notifyUpdate(int)}.
         * @param origin the observe list
         * @param index the index
         * @param ele the element
         */
         void onUpdate(ObservableList<E> origin, int index, E ele);
        /**
         * called on some elements changed. Note this need manually call by {@linkplain ObservableList#notifyBatchChanged(List)}.
         * @param origin the observe list
         * @param batch the batch elements
         */
         void onBatchChanged(ObservableList<E> origin, List<E> batch);
        /**
         * called on all elements changed. Note this need manually call by {@linkplain ObservableList#notifyAllChanged()}.
         * @param origin the observe list
         */
         void onAllChanged(ObservableList<E> origin);
    }

    private final List<E> mList;
    private final Callback<E> mCallback;

    public ObservableList(List<E> mList, Callback<E> mCallback) {
        this.mList = mList;
        this.mCallback = mCallback;
    }
    @Override
    public int size() {
        return mList.size();
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mList.contains(o);
    }

    @Override
    public @NonNull Object[] toArray() {
        return mList.toArray();
    }

    @Override
    public @NonNull
    <T> T[] toArray(T[] a) {
        return mList.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return mList.containsAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return mList.retainAll(c);
    }

    @Override
    public E get(int index) {
        return mList.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return mList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return mList.lastIndexOf(o);
    }

    @NonNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return mList.subList(fromIndex, toIndex);
    }

    //-----------------------------------------------------------

    @Override
    public @NonNull Iterator<E> iterator() {
        return new ListIteratorImpl(mList.listIterator());
    }
    @NonNull
    @Override
    public ListIterator<E> listIterator() {
        return new ListIteratorImpl(mList.listIterator());
    }
    @NonNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIteratorImpl(mList.listIterator(index));
    }

    //-------------------------------------------------------------------
    @Override
    public boolean add(E e) {
        boolean result = mList.add(e);
        if(result) {
            mCallback.onAdd(this, mList.size() - 1, e);
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        int index = mList.indexOf(o);
        if(index >= 0){
            E old = mList.remove(index);
            mCallback.onRemove(this, index, old);
            return true;
        }
        return false;
    }

    @Override @SuppressWarnings("unchecked")
    public boolean addAll(Collection<? extends E> c) {
        final int startIndex = mList.size();
        //no care index mode .here
        final boolean result = mList.addAll(c);
        if(result){
            mCallback.onBatchAdd(this, startIndex, (Collection<E>) c);
        }
        return result;
    }

    @Override @SuppressWarnings("unchecked")
    public boolean addAll(int index, @NonNull Collection<? extends E> c) {
        final boolean result = mList.addAll(index, c);
        if(result){
            mCallback.onBatchAdd(this, index, (Collection<E>) c);
        }
        return result;
    }

    @Override  @SuppressWarnings("unchecked")
    public boolean removeAll(Collection<?> c) {
        boolean result = mList.removeAll(c);
        if(result){
            mCallback.onBatchRemove(this, (Collection<E>) c);
        }
        return result;
    }

    @Override
    public void clear() {
        if(!isEmpty()){
            List<E> list = obtainList();
            list.addAll(mList);
            mList.clear();
            mCallback.onClear(this, list);
        }
    }

    @Override
    public E set(int index, E element) {
        E old = mList.set(index, element);
        mCallback.onSet(this, index, old, element);
        return old;
    }

    @Override
    public void add(int index, E element) {
        mList.add(index, element);
        mCallback.onAdd(this, index, element);
    }

    @Override
    public E remove(int index) {
        E old = mList.remove(index);
        mCallback.onRemove(this, index, old);
        return old;
    }

    protected List<E> obtainList(){
        return new ArrayList<>();
    }
    //---------------------- manually notify ---------------

    public void notifyUpdate(int index){
        mCallback.onUpdate(this, index, mList.get(index));
    }
    public void notifyUpdate(int index, E e){
        mCallback.onUpdate(this, index, e);
    }
    public void notifyUpdate(E e){
        mCallback.onUpdate(this, mList.indexOf(e), e);
    }
    public void notifyBatchChanged(List<E> batch){
        mCallback.onBatchChanged(this, batch);
    }
    public void notifyAllChanged(){
        mCallback.onAllChanged(this);
    }
    private class ListIteratorImpl implements ListIterator<E>{

        private final ListIterator<E> base;
        public ListIteratorImpl(ListIterator<E> base) {
            this.base = base;
        }
        @Override
        public boolean hasNext() {
            return base.hasNext();
        }
        @Override
        public E next() {
            return base.next();
        }
        @Override
        public boolean hasPrevious() {
            return base.hasPrevious();
        }

        @Override
        public E previous() {
            return base.previous();
        }

        @Override
        public int nextIndex() {
            return base.nextIndex();
        }

        @Override
        public int previousIndex() {
            return base.previousIndex();
        }
        @Override
        public void remove() {
            int index = base.nextIndex();
            E e = get(index);
            base.remove();
            mCallback.onRemove(ObservableList.this, index, e);
        }
        @Override
        public void set(E e) {
            int index = base.nextIndex();
            E old = get(index);
            base.set(e);
            mCallback.onSet(ObservableList.this, index, old, e);
        }
        @Override
        public void add(E e) {
            int index = base.nextIndex();
            base.add(e);
            mCallback.onAdd(ObservableList.this, index, e);
        }
    }

    /**
     * the callback adapter
     * @param <T> the element type
     * @since 1.1.7
     */
    public static class SimpleCallback<T> implements Callback<T>{

        @Override
        public void onRemove(ObservableList<T> origin, int index, T ele) {

        }

        @Override
        public void onAdd(ObservableList<T> origin, int index, T ele) {

        }

        @Override
        public void onSet(ObservableList<T> origin, int index, T old, T newE) {

        }

        @Override
        public void onClear(ObservableList<T> origin, List<T> old) {

        }

        @Override
        public void onBatchRemove(ObservableList<T> origin, Collection<T> batch) {

        }

        @Override
        public void onBatchAdd(ObservableList<T> origin, int startIndex, Collection<T> batch) {

        }

        @Override
        public void onUpdate(ObservableList<T> origin, int index, T ele) {

        }

        @Override
        public void onBatchChanged(ObservableList<T> origin, List<T> batch) {

        }

        @Override
        public void onAllChanged(ObservableList<T> origin) {

        }
    }
}
