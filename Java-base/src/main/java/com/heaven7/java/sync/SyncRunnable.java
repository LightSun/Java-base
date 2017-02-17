package com.heaven7.java.sync;

/**
 * a synchronized runnable , that can call {@linkplain #waitForComplete()} to wait for 
 * the target runnable run done. 
 * <h1>often used for concurrent, be applicable for run(1)-wait(1/1+), that means one thread to run .multi threads to wait.
 * 
 * @author heaven7
 *
 */
public final class SyncRunnable implements Runnable{
	
	private final Runnable mTarget;
	private boolean mComplete;

	public SyncRunnable(Runnable target) {
		mTarget = target;
	}

	@Override
	public void run() {
		mTarget.run();
		synchronized (this) {
			mComplete = true;
			notifyAll();
		}
	}

	/**
	 * call this to make current thread blocking until the target runnable run done. 
	 * <h2>And you must be careful that can't call this in main thread.</h2>
	 */
	public void waitForComplete() {
		synchronized (this) {
			while (!mComplete) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
}
