package com.heaven7.java.sync;

import java.util.concurrent.Callable;

/**
 * * a synchronized Callable , that can call {@linkplain #waitForResult()} to
 * wait for the target Callable run done(either call {@linkplain #run()} or {@linkplain #call()}).
 * <h1>often used for concurrent, be applicable for run(1)-wait(1/1+), that
 * means one thread to run .multi threads to wait.
 * 
 * @author heaven7
 *
 * @param <R>
 *            the result type.
 */
public final class SyncCallable<R> implements Callable<R>, Runnable {

	private final Callable<R> mCallable;
	private boolean mComplete;
	private R mResult;

	public SyncCallable(Callable<R> mCallable) {
		super();
		this.mCallable = mCallable;
	}

	@Override
	public void run() {
		try {
			mResult = mCallable.call();
		} catch (Exception e) {
			throw new SyncException("cell sync failed caused by...", e);
		}
		synchronized (this) {
			mComplete = true;
			notifyAll();
		}
	}

	@Override
	public R call() throws Exception {
		mResult = mCallable.call();
		synchronized (this) {
			mComplete = true;
			notifyAll();
		}
		return mResult;
	}

	/**
	 * call this to make current thread blocked until the target runnable run
	 * done.
	 * <h2>But you must be careful that can't call this in main thread.</h2>
	 */
	public R waitForResult() {
		synchronized (this) {
			while (!mComplete) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}
		return mResult;
	}

}
