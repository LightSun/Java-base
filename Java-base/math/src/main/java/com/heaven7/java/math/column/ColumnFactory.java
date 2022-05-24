package com.heaven7.java.math.column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ColumnFactory {

    public static IColumn createInt(int[] vals){
        return new IntColumnImpl(vals);
    }
    public static IColumn createInt(List<Integer> vals){
        return new IntColumnImpl(vals);
    }
    public static IColumn createInt(int size){
        return new IntColumnImpl(size);
    }

    public static IColumn createString(String[] vals){
        return new StringColumnImpl(vals);
    }
    public static IColumn createString(List<String> vals){
        return new StringColumnImpl(vals);
    }
    public static IColumn createString(int size){
        return new StringColumnImpl(size);
    }
    public static IColumn createFloat(float[] vals){
        return new FloatColumnImpl(vals);
    }
    public static IColumn createFloat(List<Float> vals){
        return new FloatColumnImpl(vals);
    }
    public static IColumn createFloat(int size){
        return new FloatColumnImpl(size);
    }

    public static <T> IColumn createList(List<T> list){
        return new ListColumnImpl<T>(list);
    }
    public static IColumn strsplit(String str, String regex){
        return new ListColumnImpl<String>(new ArrayList<String>(Arrays.asList(str.split(regex))));
    }
}


