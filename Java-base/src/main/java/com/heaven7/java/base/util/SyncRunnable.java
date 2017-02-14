package com.heaven7.java.base.util;
/**
 * a synchronized runnable , that can call {@linkplain #waitForComplete()} to wait for 
 * the target runnable run done.
 * @author heaven7
 *
 */
public class SyncRunnable implements Runnable{
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
