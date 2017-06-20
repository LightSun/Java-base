package com.heaven7.java.base.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.heaven7.java.base.util.SearchUtils;

import junit.framework.TestCase;

public class SearchTest extends TestCase{
	
	

	
	public void testEmpty(){
		List<Bean> list = new ArrayList<>();
		Comparator<Bean> comparator = new Comparator<SearchTest.Bean>() {
			@Override
			public int compare(Bean o1, Bean o2) {
				return o1.txt.compareTo(o2.txt);
			}
		};
		assertEquals(-1, SearchUtils.binarySearch(list, new Bean("b"), comparator));
	}
	
	public void testAnyType(){
		List<Bean> list = new ArrayList<>();
		list.add(new Bean("a"));
		list.add(new Bean("c"));
		list.add(new Bean("e"));
		list.add(new Bean("g"));
		list.add(new Bean("j"));
		list.add(new Bean("m"));
		Comparator<Bean> comparator = new Comparator<SearchTest.Bean>() {
			@Override
			public int compare(Bean o1, Bean o2) {
				return o1.txt.compareTo(o2.txt);
			}
		};
		
		int size = list.size();
		//exclude
		assertIndex(1, SearchUtils.binarySearch(list, 0, size, new Bean("b"), comparator));
		assertIndex(3, SearchUtils.binarySearch(list, 0, size, new Bean("f"), comparator));
		assertIndex(6, SearchUtils.binarySearch(list, 0, size, new Bean("o"), comparator));
	}
	
	public void testReverseIntArray(){
		int[] arr = new int []{9, 8 ,6, 4, 1};
		int index = SearchUtils.binarySearch(arr, 3);
		System.out.println("index = " + index); // -1
	}
	public void testintArray(){
		int[] arr = new int []{1,4,6,8,9};
		int len = arr.length;
		int index = SearchUtils.binarySearch(arr, 0, len, 5);
		assertIndex(2, -(index + 1 ));
		
		index = SearchUtils.binarySearch(arr, 0, len, 3);
		assertIndex(1, -(index + 1 ));
		
		index = SearchUtils.binarySearch(arr, 0, len, 7);
		assertIndex(3, -(index + 1 ));
		
		index = SearchUtils.binarySearch(arr, 0, len, 6);
		assertIndex(2, index);
	}
	
	void assertIndex(int expect, int target){
		if(target < 0){
			target = -(target + 1 );
		}
		assertEquals(expect, target);
	}
	
	static class Bean{
		public final String txt;

		public Bean(String txt) {
			super();
			this.txt = txt;
		}

		@Override
		public String toString() {
			return "Bean [txt=" + txt + "]";
		}
		
	}
}
