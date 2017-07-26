package com.heaven7.java.api.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * see {@linkplain Stream}
 * streams() 相当于得到循环控制. (集合的方法)
 * 
 * filter 相当于 判断是否符合条件 .
 * map相当于从当前的这个对象转化为某个值（一般get方法）
 * sorted 排序
 * collect 将最终的对象收集到收集器中(一般是集合)。
 * count 统计个数
 * forEach .迭代
 * flatMap 合并。比如 多个list的stream合并为1个
 * limit 限制
 * allMatch匹配
 */
public class StreamsSelfDocTest {

	public interface Ordered {
	    default int getOrder(){
	        return 0;
	    }
	}

	public interface Valued<V extends Ordered> {
	    boolean hasPropertyTwo();
	    V getValue();
	}

	public interface Thing<V extends Ordered> {
	    boolean hasPropertyOne();
	    Valued<V> getValuedProperty();
	}

	//same function as myMethod2.
	public <V extends Ordered> List<V> myMethod(List<Thing<V>> things) {
	    List<V> results = new ArrayList<V>();
	    for (Thing<V> thing : things) {
	        if (thing.hasPropertyOne()) {
	            Valued<V> valued = thing.getValuedProperty();
	            if (valued != null && valued.hasPropertyTwo()){
	                V value = valued.getValue();
	                if (value != null){
	                    results.add(value);
	                }
	            }
	        }
	    }
	    results.sort((a, b)->{
	        return Integer.compare(a.getOrder(), b.getOrder());
	    });
	    return results;
	}
	
	//same function as myMethod.
	public <V extends Ordered> List<V> myMethod2(List<Thing<V>> things) {
	    return things.stream()
	        .filter(Thing::hasPropertyOne)
	        .map(Thing::getValuedProperty)
	        .filter(Objects::nonNull)
	        .filter(Valued::hasPropertyTwo)
	        .map(Valued::getValue)
	        .filter(Objects::nonNull)
	        .sorted(Comparator.comparing(Ordered::getOrder))
	        .collect(Collectors.toList());
	}    
}
