package com.heaven7.java.base.test;

import com.heaven7.java.sync.SyncRunnable;

public class SyncRunnableTest {

	//use scene: multiple task wait one task done.
	public static void main(String[] args) {
		Runnable task = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					System.out.println(i);
				}
			}
		};

		final SyncRunnable sr = new SyncRunnable(task);
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("begin call waitForComplete()");
				sr.waitForComplete();
				System.out.println("done waitForComplete()");
			}
		}).start();
		;
		new Thread(sr).start();
	}

}
