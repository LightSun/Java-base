package com.heaven7.java.api.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.ObjIntConsumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamTest2 {

	public static void main(String[] args) {
		/*
		 * testFlatMap(); testParallelStream(); testCreateStreams();
		 */
		//testStatistics();
		//testConvertIterator();
		//testConcatenateStream();
		//testCharStream();
		testReduction();
	}

	private static void testReduction() {
		//1-2-3-4
		OptionalInt optionalInt = IntStream.range(1, 5).reduce(new IntBinaryOperator() {
			@Override // 1-2 -3 -4
			public int applyAsInt(int left, int right) {
				System.out.println("left =" + left + " , right = " + right);
				return left + right; //left 相当于上一次的结果(如果是第一次则为第一个元素)， right是下一个元素
			}
		});
		System.out.println(optionalInt.getAsInt());
		/**
		 *  left =1 , right = 2
			left =-1 , right = 3
			left =-4 , right = 4
			-8
		 */
		System.out.println(optionalInt.orElse(6));
		optionalInt.orElseGet(new IntSupplier() {
			@Override
			public int getAsInt() {
				return 6;
			}
		});
	}

	/**
	 * 
Java does not have a Char Stream, so when working with Strings and constructing a Stream of Characters, 
an option is to get a IntStream of code points using String.codePoints() method.
	 */
	private static void testCharStream() {
		// TODO Auto-generated method stub

	}

	// 连接多个stream
	private static void testConcatenateStream() {
		Collection<String> abc = Arrays.asList("a", "b", "c");
		Collection<String> digits = Arrays.asList("1", "2", "3");
		Collection<String> greekAbc = Arrays.asList("alpha", "beta", "gamma");
		final Stream<String> concat1 = Stream.concat(abc.stream(), digits.stream());

		concat1.forEach(System.out::print);
		// prints: abc123
		System.out.println("=================");

		final Stream<String> concat2 = Stream.concat(Stream.concat(abc.stream(), digits.stream()), greekAbc.stream());

		System.out.println(concat2.collect(Collectors.joining(", ")));
		// prints: a, b, c, 1, 2, 3, alpha, beta, gamma
	}

	private static void testConvertIterator() {
		Iterator<String> iterator = Arrays.asList("A", "B", "C").iterator();
		Spliterator<String> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
		StreamSupport.stream(spliterator, false).forEach(System.out::println);
		;
	}

	/**
	 * Java 8 provides classes called IntSummaryStatistics,
	 * DoubleSummaryStatistics and LongSummaryStatistics which give a state
	 * object for collecting statistics such as count, min, max, sum, and
	 * average.
	 */
	private static void testStatistics() {
		List<Integer> naturalNumbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		IntSummaryStatistics stats = naturalNumbers.stream().mapToInt((x) -> x).summaryStatistics();
		System.out.println(stats);
		// IntSummaryStatistics{count=10, sum=55, min=1, average=5.500000,
		// max=10}
	}

	private static void testCreateStreams() {
		// TODO Auto-generated method stub
		Collection<String> stringList = new ArrayList<>();
		Stream<String> stream = stringList.parallelStream();

		String[] values = { "aaa", "bbbb", "ddd", "cccc" };
		Stream<String> stringStream = Arrays.stream(values);
		Stream<String> stringStreamAlternative = Stream.of(values);

		Stream<Integer> integerStream = Stream.of(1, 2, 3);
		IntStream intStream = IntStream.of(1, 2, 3);
		DoubleStream doubleStream = DoubleStream.of(1.0, 2.0, 3.0);
		intStream = Arrays.stream(new int[] { 1, 2, 3 });

		// ranges
		int[] values2 = new int[] { 1, 2, 3, 4, 5 };
		IntStream intStram = Arrays.stream(values2, 1, 3);
		// box into Stream<Integer>
		integerStream = intStream.boxed();

		int[] ints = { 1, 2, 3 };
		List<Integer> list = IntStream.of(ints).boxed().collect(Collectors.toList());
	}

	private static void testParallelStream() {
		List<String> data = Arrays.asList("One", "Two", "Three", "Four", "Five");
		Stream<String> stream = data.stream().parallel();
		Stream<String> stream2 = data.parallelStream();
		stream.forEach(System.out::println);
		stream2.forEachOrdered(System.out::println);
	}

	private static void testFlatMap() {
		List<String> list1 = Arrays.asList("one", "two");
		List<String> list2 = Arrays.asList("three", "four", "five");
		List<String> list3 = Arrays.asList("six");
		List<String> finalList = Stream.of(list1, list2, list3).flatMap(Collection::stream) // 合并成
																							// Stream<String>
				.collect(Collectors.toList());
		System.out.println(finalList);
		// [one, two, three, four, five, six]

		Map<String, List<Integer>> map = new LinkedHashMap<>();
		map.put("a", Arrays.asList(1, 2, 3));
		map.put("b", Arrays.asList(4, 5, 6));

		List<Integer> allValues = map.values() // Collection<List<Integer>>
				.stream() // Stream<List<Integer>>
				.flatMap(List::stream) // Stream<Integer>
				.collect(Collectors.toList());

		System.out.println(allValues);
		// [1, 2, 3, 4, 5, 6]

		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map1 = new HashMap<>();
		map1.put("1", "one");
		map1.put("2", "two");

		Map<String, String> map2 = new HashMap<>();
		map2.put("3", "three");
		map2.put("4", "four");
		list.add(map1);
		list.add(map2);

		Set<String> output = list.stream() // Stream<Map<String, String>>
				.map(Map::values) // Stream<List<String>>
				.flatMap(Collection::stream) // Stream<String>
				.collect(Collectors.toSet()); // Set<String>
		System.out.println(output);
		// [one, two, three,four]
	}
	
	//======================== sample method =================
	
	public String intStreamToString(IntStream intStream) {
		  return intStream.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}
	public String intStreamToString2(IntStream intStream) {
		return intStream.collect(StringBuilder::new, new ObjIntConsumer<StringBuilder>() {
			@Override
			public void accept(StringBuilder t, int value) {
				t.appendCodePoint(value);
			}
		}, new BiConsumer<StringBuilder, StringBuilder>() {
			@Override
			public void accept(StringBuilder t, StringBuilder u) {
				t.append(u);
			}
		}).toString();
	}
	public IntStream stringToIntStream(String in) {
		return in.codePoints();
	}
}
