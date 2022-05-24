package com.heaven7.java.math.column;

import com.heaven7.java.base.util.Pair;

import java.util.*;

public class PairColumnImpl<K,V> extends ListColumnImpl<Pair<K,V>> implements IColumn.IPairColumn {

    public PairColumnImpl(List<Pair<K,V>> list) {
        super(list);
    }
    public PairColumnImpl() {
        super(null);
    }
    @Override @SuppressWarnings("unchecked")
    public IColumn keys() {
        List<K> list = new ArrayList<>();
        Object obj;
        for (int i = 0; i < size(); i++) {
            obj = get(i);
            list.add(obj != null ? ((Pair<K,V>)obj).key : null);
        }
        return new ListColumnImpl<K>(list);
    }

    @Override @SuppressWarnings("unchecked")
    public IColumn values() {
        List<V> list = new ArrayList<>();
        Object obj;
        for (int i = 0; i < size(); i++) {
            obj = get(i);
            list.add(obj != null ? ((Pair<K,V>)obj).value : null);
        }
        return new ListColumnImpl<V>(list);
    }
    @Override
    public Object getValue(Object key) {
        if(key == null){
            throw new NullPointerException();
        }
        Object obj;
        for (int i = 0; i < size(); i++) {
            obj = get(i);
            if(obj instanceof Pair){
                if(key.equals(((Pair) obj).key)){
                    return ((Pair) obj).value;
                }
            }
        }
        return null;
    }

    @Override
    public Object getKey(Object value) {
        Object obj;
        if(value != null){
            for (int i = 0; i < size(); i++) {
                obj = get(i);
                if(obj instanceof Pair){
                    if(value.equals(((Pair) obj).value)){
                        return ((Pair) obj).key;
                    }
                }
            }
        }else{
            for (int i = 0; i < size(); i++) {
                obj = get(i);
                if(obj instanceof Pair){
                    if(((Pair) obj).value == null){
                        return ((Pair) obj).key;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object put(Object k, Object v) {
        if(k == null){
            throw new NullPointerException();
        }
        Object obj;
        for (int i = 0; i < size(); i++) {
            obj = get(i);
            if(obj instanceof Pair){
                if(k.equals(((Pair) obj).key)){
                    Object oldV = ((Pair) obj).value;
                    set0(i, new Pair<>(((Pair) obj).key,  v));
                    return oldV;
                }
            }
        }
        return null;
    }

    @Override
    public Pair getPair(int index) {
        return (Pair) get(index);
    }

    @Override @SuppressWarnings("unchecked")
    public Map toMap(Comparator<Object> cmp) {
        if(cmp != null){
            mList.sort(new Comparator<Pair<K,V>>() {
                @Override
                public int compare(Pair<K,V> o1, Pair<K,V> o2) {
                    if(o1 == null && o2 == null){
                        return 0;
                    }
                    if(o1 != null){
                        return cmp.compare(o1.key, o2 != null ? o2.key : null);
                    }else{
                        return cmp.compare(null, o2.key);
                    }
                }
            });
        }
        return new Map0();
    }

    private class Map0 extends AbstractMap<K, V>{
        @Override
        public Set<Entry<K, V>> entrySet() {
            return new HashSet<>(mList);
        }
        @SuppressWarnings("unchecked")
        public V put(K key, V value) {
            return (V) PairColumnImpl.this.put(key, value);
        }
    }
}
