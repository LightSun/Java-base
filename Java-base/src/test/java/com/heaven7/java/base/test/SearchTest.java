package com.heaven7.java.base.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.heaven7.java.base.util.SearchUtils;

import junit.framework.TestCase;

public class SearchTest extends TestCase{
	
	public static void main(String[] args) {
		/*System.out.println(0xff );             //255
		System.out.println( (0xff + 5) >> 8 ); //1
		System.out.println( (0xff + 1) >> 8 ); //1
		System.out.println(Math.pow(2, 8));    //256
		System.out.println(((int)Math.pow(2, 8)) >> 8); //1
*/		
		//System.out.println(Integer.MAX_VALUE);
		//System.out.println(Long.MAX_VALUE);
		//System.out.println(5 + 0x10000000);
		int val = 5 +          0x10000000 ;
		//高位的值
		/*System.out.println((val & 0xf0000000) == 0x10000000);
		System.out.println( val & 0x0fffffff);
		System.out.println(5);
		System.out.println(0x5ffff >> 16);
		System.out.println(0x15ffff >> 16);
		System.out.println(0x8ffff >> 16);
		System.out.println(0xffff);
		System.out.println(0xfff >> 16);
		System.out.println( 0x01ffffff );
		System.out.println(1 << 24);*/
		
		int baseKey = 5 ;
		int count = 10 << 16;
		int tag = 3 << 24;
		int key = baseKey + count + tag;
		
		key = 7;
		System.out.println("count = " + count);
		System.out.println("tag = " + tag);
		System.out.println(key & 0x0000ffff);
		System.out.println( (key & 0x00ff0000) >> 16);
		System.out.println( (key & 0xff000000) >> 24);
		System.out.println( ( 0xff));
		
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		System.out.println();
	}

	
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
