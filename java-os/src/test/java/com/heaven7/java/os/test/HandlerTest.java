package com.heaven7.java.os.test;

import com.heaven7.java.os.Handler;
import com.heaven7.java.os.HandlerThread;
import com.heaven7.java.os.Looper;
import com.heaven7.java.os.Message;

import junit.framework.TestCase;

public class HandlerTest extends TestCase{

	private final Object mLock = new Object();
	private Handler mHandler;
	private Thread mThread;
	private HandlerThread mHt;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		//initChildThread();
		initHandlerThread();
	}

	private void initHandlerThread() {
		mHt = new HandlerThread("ht");
		mHt.start();
		mHandler = new Handler(mHt.getLooper(), new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				System.out.println("HandlerTest ok: " + msg);
				return false;
			}
		});
	}

	private void initChildThread() throws InterruptedException  {
		mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("---------");
				Looper.prepare();
				mHandler = new Handler(Looper.myLooper(), new Handler.Callback() {
					@Override
					public boolean handleMessage(Message msg) {
						System.out.println("HandlerTest ok: " + msg);
						mThread.interrupt();
						return false;
					}
				});
				synchronized (mLock) {
					mLock.notifyAll();
				}
				Looper.loop();
				System.out.println("looper unexpect end...");
			}
		});
		mThread.start();
		synchronized (mLock) {
			mLock.wait();
		}
	}
	
	private void testMainLooper(){
		//main thread bug. made block for ever...
		/*Looper.prepareMainLooper();
		Handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				System.out.println("HandlerTest: " + msg);
				return false;
			}
		});
		Looper.loop();*/
	}
	
	//BUG
	public void test1(){
		mHandler.obtainMessage(5).sendToTarget();
		System.out.println("test1 done");
	}
}
