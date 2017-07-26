package com.heaven7.java.api.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SteamsTest {

	public static void main(String[] args) {
		/*testStreamOf();
		testListStream();
		testParallelStream();*/
		//testConsumeStream();
		//testFilterNullPointerException();
		//testFrequencyMap();
		//testInfiniteStream();
		testCollectElementsIntoCollection();
	}
	private static void testCollectElementsIntoCollection() {
		// syntax with method reference
		List<String> strings = Arrays.asList("apple", "banana", "pear", "kiwi", "orange");
		System.out.println(strings
		        .stream()
		        .filter(s -> s != null && s.length() >=5 )
		        .collect(Collectors.toCollection(ArrayList::new))
		);
		System.out.println("=========================================");

		// syntax with lambda
		LinkedHashSet<String> set = strings
		        .stream()
		        .filter(s -> s != null && s.length() >=5)
		        .collect(Collectors.toCollection(() -> new LinkedHashSet<>()));
		System.out.println(set);
		
		System.out.println("=========================================");
		List<Student> students = new ArrayList<Student>(); 
	    students.add(new Student(1,"test1"));
	    students.add(new Student(2,"test2"));
	    students.add(new Student(3,"test3"));
	    
	    Map<Integer, String> IdToName = students.stream()
	        .collect(Collectors.toMap(Student::getId, Student::getName));
	    System.out.println(IdToName);
	    
	    System.out.println("=========================================");
	    //partitioningBy 分类，并且把test（）返回值作为key
	    Map<Boolean, List<Student>> map = students.stream()
	    		.collect(Collectors.partitioningBy(new Predicate<Student>() {
					@Override
					public boolean test(Student t) {
						return t.getId() >=2 ; 
					}
				}));
	    System.out.println(map);
	}
	//无限stram
	private static void testInfiniteStream() {
		// Generate infinite stream - 1, 2, 3, 4, 5, 6, 7, ...
		IntStream naturalNumbers = IntStream.iterate(1, x -> x + 1);

		// Print out only the first 5 terms
		naturalNumbers.limit(8).forEach(System.out::println);
	}

	private static void testFrequencyMap() {
		//Collectors
		Stream.of("apple", "orange", "banana", "apple", "APPLE")
	      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
	      .entrySet()
	      .forEach(System.out::println);
		
		System.out.println("=========================================");
		Stream.of("apple", "orange", "banana", "apple","APPLE")
	      .collect(Collectors.groupingBy(new Function<String, String>() {
			@Override
			public String apply(String t) {
				return t.toLowerCase();  //分类函数
			}
		},Collectors.counting()))  //counting（） .value为个数
	      .entrySet()
	      .forEach(System.out::println);
		
		System.out.println("=================== equals bellow ======================");
		/**
		 * Collectors.joining(). key 为元素， value 为 Function转化后的.并且。如果多个。直接追加在末尾
		 */
		Stream.of("apple", "orange", "banana", "apple","APPLE")
		.collect(Collectors.groupingBy(t->t.toLowerCase(),
				Collectors.joining()))
		.entrySet()
		.forEach(System.out::println);
	}

	private static void testFilterNullPointerException() {
		try {
		    IntStream.range(1, 10).filter(null);
		} catch (NullPointerException e) {
		    System.out.println("We got a NullPointerException as null was passed as an argument to filter()");
		}
	}

	private static void testConsumeStream() {
		long count = IntStream.range(1, 10)
			.filter(a -> a % 2 == 0)   //中间操作 
			.peek(System.out::println) //这是一个中间操作
			.count();                  //终结操作
		//System.out.println(count);
	}

	private static void testParallelStream() {
		List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 42); 
		// parallel
		long howManyOddNumbersParallel = integerList.parallelStream()  //顺序不保证
		                                            .filter(e -> (e % 2) == 1)
		                                            .count();

		System.out.println(howManyOddNumbersParallel); // Output: 2
	}

	private static void testStreamOf() {
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
	
	static void testListStream(){
		List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 42); 

		// sequential 
		long howManyOddNumbers = integerList.stream()
		                                    .filter(e -> (e % 2) == 1) //accept
		                                    .count(); 

		System.out.println(howManyOddNumbers); // Output: 2
	}
}
