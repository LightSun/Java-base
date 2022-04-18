package com.java.heaven7.api.demo;

public class DeadLockTest {

	public static void main(String[] args) throws InterruptedException {
		final DeadLock dl = new DeadLock();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				dl.methodA();
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					dl.method2();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t1.setName("First");
		t2.setName("Second");
		t1.start();
		t2.start();
	}
}

class DeadLock {

	final Object mLock1 = new Object();
	final Object mLock2 = new Object();

	public void methodA() {
		System.out.println("methodA wait for mLock1  " + Thread.currentThread().getName());
		synchronized (mLock1) {
			System.out.println("methodA mLock1 acquired   " + Thread.currentThread().getName());
			try {
				Thread.sleep(100);
				method2();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void method2() throws InterruptedException {
		System.out.println("method2 wait for mLock2  " + Thread.currentThread().getName());
		synchronized (mLock2) {
			System.out.println("method2  mLock2 acquired   " + Thread.currentThread().getName());
			Thread.sleep(100);
			method3();
		}
	}

	public void method3() throws InterruptedException {
		System.out.println("method3  wait mLock1  " + Thread.currentThread().getName());
		synchronized (mLock1) {
			System.out.println("method3   mLock1 acquired  " + Thread.currentThread().getName());
		}
	}
}