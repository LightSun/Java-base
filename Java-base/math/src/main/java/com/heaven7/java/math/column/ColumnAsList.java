package com.heaven7.java.math.column;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ColumnAsList implements List<Object> {

    private final IColumn column;

    public ColumnAsList(IColumn column) {
        this.column = column;
    }

    @Override
    public int size() {
        return column.size();
    }

    @Override
    public boolean isEmpty() {
        return column.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return column.contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = column.get(i);
        }
        return arr;
    }

    @Override
    public boolean add(Object o) {
        return column.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return column.remove(o);
    }

    @Override
    public boolean addAll(Collection c) {
        int size = c.size();
        for(Object obj : c){
            column.add(obj);
        }
        return size != 0;
    }
    @Override @SuppressWarnings({"rawtypes", "unchecked"})
    public boolean addAll(int index, Collection c) {
        return column.addAll(index, new ArrayList(c));
    }

    @Override
    public void clear() {
        column.clear();
    }

    @Override
    public Object get(int index) {
        return column.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        return column.set(index, element);
    }

    @Override
    public void add(int index, Object element) {
        column.add(index, element);
    }

    @Override
    public Object remove(int index) {
        return column.removeAt(index);
    }

    @Override
    public int indexOf(Object o) {
        return column.indexOf(o);
    }
    @Override
    public int lastIndexOf(Object o) {
        return column.lastIndexOf(o);
    }
    @Override
    public ListIterator listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return column.subColumn(fromIndex, toIndex).asList();
    }

    @Override
    public boolean retainAll(Collection c) {
        List<Object> list = new ArrayList<>();
        int size = size();
        Object obj;
        for (int i = 0; i < size; i++) {
            obj = get(i);
            if(c.contains(obj)){
                list.add(obj);
            }
        }
        int newSize = list.size();
        column.setSize(newSize);
        for (int i = 0; i < newSize; i++) {
            column.set(i, list.get(i));
        }
        return newSize != size;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean result = true;
        for(Object obj : c){
            if(!remove(obj)){
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection c) {
        for(Object obj : c){
            if(!contains(obj)){
                return false;
            }
        }
        return true;
    }
    @Override
    public Object[] toArray(Object[] a) {
        int size = size();
        if(a.length < size){
            return toArray();
        }
        for (int i = 0; i < size; i++) {
            a[i] = get(i);
        }
        return a;
    }
    private class Itr implements Iterator<Object> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        Itr() {}

        public boolean hasNext() {
            return cursor != size();
        }

        public Object next() {
            int i = cursor;
            if (i >= size())
                throw new NoSuchElementException();
            cursor = i + 1;
            return get(lastRet = i);
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                column.removeAt0(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super Object> consumer) {
            Objects.requireNonNull(consumer);
            final int size = size();
            int i = cursor;
            if (i >= size) {
                return;
            }
            while (i != size) {
                consumer.accept(get(i++));
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
        }
    }
    private class ListItr extends Itr implements ListIterator<Object> {
        ListItr(int index) {
            super();
            cursor = index;
        }
        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public Object previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            cursor = i;
            return get(lastRet = i);
        }

        public void set(Object e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                column.set0(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
        public void add(Object e) {
            try {
                int i = cursor;
                ColumnAsList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
