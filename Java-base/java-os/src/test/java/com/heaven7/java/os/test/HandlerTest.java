package com.heaven7.java.os.test;

import com.heaven7.java.os.*;

import junit.framework.TestCase;

public class HandlerTest extends TestCase{

	private final Object mLock = new Object();
	private Handler mHandler;
	private Thread mThread;
	private HandlerThread mHt;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		initChildThread();
		//initHandlerThread();
		//testMainLooper();
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

	public void testMainLooper(){
		//main thread bug. made block for ever...
		Looper.prepareMainLooper();
		Looper.loop();
		System.out.println("testMainLooper");
	}
	
	//BUG
	public void test1(){
    mHandler.getLooper()
        .getQueue()
        .addIdleHandler(
            new MessageQueue.IdleHandler() {
              @Override
			  public boolean queueIdle() {
                System.out.println("idle now ...");
               // mHandler.obtainMessage(5, "from queueIdle-----").sendToTarget();
                return true;
              }
            });
		for (int i = 0 ; i < 3 ; i ++){
			final String msg = "msg___" + i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					mHandler.obtainMessage(5, msg).sendToTarget();
				}
			}).start();
		}
		Message msg = mHandler.obtainMessage(5, "---------");
		mHandler.sendMessageDelayed(msg, 5000);
		System.out.println("test1 mark done");
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
