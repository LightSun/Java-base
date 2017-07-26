package com.heaven7.java.api.test;

import java.util.stream.IntStream;

public class SteamsForMathematicalTest {

	public static void main(String[] args) {
		
		test1();
	}

	//stream 应用于数学公式.精确计算
	private static void test1() {
		double sum = Math.sqrt(12) * 
	            IntStream.rangeClosed(0, 100)
	                     .mapToDouble(k -> Math.pow(-3, -1 * k) / (2 * k + 1))
	                     .sum();	
		System.out.println(sum);//MATH.PI 
		
		sum = IntStream.rangeClosed(0, 100) 
		  .mapToObj(k -> Math.pow(-3, -1 * k) * Math.sqrt(12) / (2 * k + 1))
		  .mapToDouble(k -> k.doubleValue())
		  .sum();
		System.out.println(sum);//MATH.PI 
	}
	
}
