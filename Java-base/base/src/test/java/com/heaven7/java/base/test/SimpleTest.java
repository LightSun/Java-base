package com.heaven7.java.base.test;

import java.util.ArrayList;
import java.util.List;

public class SimpleTest {

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
		
	//	key = 7;
		System.out.println("count = " + count);
		System.out.println("tag = " + tag);
		System.out.println(key & 0x0000ffff); //ok
		System.out.println( (key & 0x00ff0000) >> 16); //ok
		System.out.println( (key & 0xff000000) >> 24);//ok
		System.out.println( ( 0xff));
		
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		System.out.println();
	}
}
