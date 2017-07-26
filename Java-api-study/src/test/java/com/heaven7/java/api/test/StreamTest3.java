package com.heaven7.java.api.test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 
 *
 */
public class StreamTest3 {

	public static void main(String[] args) {
		testConvertOptionalToStream();
		testStreamToMap();
		testAverage();
		testCollectToArray();
	}

	private static void testCollectToArray() {
		List<String> fruits = Arrays.asList("apple", "banana", "pear", "kiwi", "orange");
		String[] filteredFruits = fruits.stream()
				.filter(s -> s.contains("a"))
				.toArray(String[]::new);

		// prints: [apple, banana, pear, orange]
		System.out.println(Arrays.toString(filteredFruits));
	}

	private static void testAverage() {
		IntStream is = IntStream.of(10, 20, 30);
		double average = is.average().getAsDouble(); // average is 20.0
	}

	private static void testStreamToMap() {
		Stream<String> characters = Stream.of("A", "B", "C");
		Map<Integer, String> map = characters
				.collect(Collectors.toMap(element -> element.hashCode(), element -> element));
		System.out.println(map);
		// map = {65=A, 66=B, 67=C}

		// Collectors.toMap 第三个参数。用于 merge value(如果有相同key的时候)
		map = Stream.of("A", "B", "C", "A").collect(Collectors.toMap(element -> element.hashCode(), element -> element,
				(existingVal, newVal) -> (existingVal + newVal)));
		System.out.println(map);
		// {65=AA, 66=B, 67=C}
	}

	private static void testConvertOptionalToStream() {
		Optional<String> op1 = Optional.empty();// not present
		Optional<String> op2 = Optional.of("Hello");
		Optional<String> op3 = Optional.of("World");

		List<String> result = Stream.of(op1, op2, op3).filter(Optional::isPresent).map(Optional::get)
				.collect(Collectors.toList());

		System.out.println(result); // [Hello World]
	}

	public static <K, V> Function<K, Map.Entry<K, V>> entryMapper(Function<K, V> mapper) {
		return (k) -> new AbstractMap.SimpleEntry<>(k, mapper.apply(k));
	}
}
