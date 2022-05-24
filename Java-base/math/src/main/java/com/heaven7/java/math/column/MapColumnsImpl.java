package com.heaven7.java.math.column;

import com.heaven7.java.base.util.Throwables;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapColumnsImpl implements IColumn.IMapColumns {

    private final IColumn keys;
    private final IColumn values;

    public MapColumnsImpl(IColumn keys, IColumn values) {
        Throwables.checkArgument(keys.size() == values.size(), " size must be the same");
        this.keys = keys;
        this.values = values;
    }

    @Override
    public IColumn keys() {
        return keys;
    }
    @Override
    public IColumn values() {
        return values;
    }
    @Override
    public Object getValue(Object key) {
        if(key == null){
            throw new NullPointerException();
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if(key.equals(keys.get(i))){
                return values.get(i);
            }
        }
        return null;
    }
    @Override
    public Object getKey(Object value) {
        int size = size();
        if(value == null){
            for (int i = 0; i < size; i++) {
                if(values.get(i) == null){
                    return keys.get(i);
                }
            }
        }else{
            for (int i = 0; i < size; i++) {
                if(value.equals(values.get(i))){
                    return keys.get(i);
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
        int size = size();
        for (int i = 0; i < size; i++) {
            if(k.equals(keys.get(i))){
                Object oldV = values.get(i);
                values.set0(i, v);
                return oldV;
            }
        }
        return null;
    }
    @Override
    public int size() {
        return keys.size();
    }
    @Override
    public Map toMap(Comparator<Object> cmp) {
        Map<Object, Object> map = cmp != null ? new TreeMap<>(cmp) : new HashMap<>();
        Object o;
        int size = size();
        for (int i = 0; i < size; i++) {
            o = keys.get(i);
            if(o != null){
                map.put(o, values.get(i));
            }
        }
        return map;
    }
}
