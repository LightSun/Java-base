package com.heaven7.java.base.test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import com.heaven7.java.sync.SyncCallable;
/**
 */
public class SyncCallableTest {

	//use scene: multiple task wait one task done.
	public static void main(String[] args) {
		Callable<String> task = new Callable<String>() {
			
			final AtomicInteger index = new AtomicInteger(0);
			@Override
			public String call() {
				for (int i = 0; i < 10; i++) {
				//	System.out.println(i);
				}
				//System.out.println("raw Callable: index = " + index.get());
				return index.incrementAndGet() + "";
			}
		};

		SyncCallable<String> sr = new SyncCallable<String>(task);
		for(int i = 0; i < 5 ; i++){
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("begin call waitForResult(): index = " + index);
					String result = sr.waitForResult();
					System.out.println("done waitForResult(): index = "+ index + " ,result = " + result);
				}
			}).start();
		}
		
		try {
			String result = sr.call();
			System.out.println("call SyncCallable.call() done: result = "+ result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
