package com.heaven7.java.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.2.7
 */
public final class CollectionUtils {

    public static String joinString(List<String> list,String seq){
        StringBuilder sb = new StringBuilder();
        final int size = list.size();
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i));
            if( i != size - 1){
                sb.append(seq);
            }
        }
        return sb.toString();
    }
    public static <T> List<T> newUniqueList(){
        return new ArrayList<T>(){
            @Override
            public boolean add(T o) {
                if(contains(o)){
                    return false;
                }
                return super.add(o);
            }
        };
    }

    //a,b   4 -> a,b,a,b
    public static <T> boolean tileToLength(List<T> list, int expectedLength){
        if(list.isEmpty()){
            return false;
        }
        final int rawSize = list.size();
        int diff = expectedLength - list.size();
        int nextIndex = 0;
        for (int i = diff ; i > 0 ; i --){
            list.add(list.get(nextIndex));
            if(nextIndex == rawSize - 1){
                nextIndex = 0;
            }else{
                nextIndex ++;
            }
        }
        return true;
    }
    public static <T> List<T> produce(int count ,T val){
        List<T> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(val);
        }
        return list;
    }
}
