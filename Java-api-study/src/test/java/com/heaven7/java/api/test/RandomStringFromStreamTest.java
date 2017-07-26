package com.heaven7.java.api.test;

import java.security.SecureRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 通过Stream 生成随机string
 */
public class RandomStringFromStreamTest {

	private static final SecureRandom rng = new SecureRandom(SecureRandom.getSeed(20));

	// returns true for all chars in 0-9, a-z and A-Z
	public boolean useThisCharacter(char c) {
		// check for range to avoid using all unicode Letter (e.g. some chinese
		// symbols)
		return c >= '0' && c <= 'z' && Character.isLetterOrDigit(c);
	}
	
	public String generateRandomString(long length){
	    //Since there is no native CharStream, we use an IntStream instead
	    //and convert it to a Stream<Character> using mapToObj.
	    //We need to specify the boundaries for the int values to ensure they can safely be cast to char
	    Stream<Character> randomCharStream = rng
	    		.ints(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT)
	    		.mapToObj(i -> (char)i)
	    		.filter(c -> useThisCharacter(c))
	    		.limit(length);

	    //now we can use this Stream to build a String utilizing the collect method.
	    String randomString = randomCharStream.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
	    return randomString;
	}
	
	public static void main(String[] args) {
		RandomStringFromStreamTest test = new RandomStringFromStreamTest();
		IntStream.range(10, 20)
		         .mapToObj(test::generateRandomString)
		         .spliterator()
		         .forEachRemaining(System.out::println);
	}
}
/**
 * collection.stream()
Arrays.stream(array)
Stream.iterate(firstValue, currentValue -> nextValue)
Stream.generate(() -> value)
Stream.of(elementOfT[, elementOfT, ...])
Stream.empty()
StreamSupport.stream( iterable.spliterator(), false )
*/
