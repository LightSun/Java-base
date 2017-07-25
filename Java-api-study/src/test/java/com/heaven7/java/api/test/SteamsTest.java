package com.heaven7.java.api.test;

import java.util.stream.Stream;

public class SteamsTest {

	public static void main(String[] args) {
		Stream<String> fruitStream = Stream.of("apple", "banana", "pear", "kiwi", "orange");

		fruitStream.filter(s -> s.contains("a"))
		           .map(String::toUpperCase)
		           .sorted()
		           .forEach((p) -> {
		        	   System.out.println(p);
		        	   }
		           );
		          // .forEach(System.out::println) //ok, lambda 表达式。表示 (p)->System.out.println(p)
		           ;
	}
}
