package com.heaven7.java.os.test;

import com.heaven7.java.os.Handler;
import com.heaven7.java.os.Looper;
import com.heaven7.java.os.Message;

import junit.framework.TestCase;

public class HandlerTest extends TestCase{

	private Handler Handler;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Looper.prepareMainLooper();
		Handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				System.out.println("HandlerTest: " + msg);
				return false;
			}
		});
		Looper.loop();
	}
	
	//BUG
	public void test1(){
		Handler.obtainMessage(5).sendToTarget();
		System.out.println("test1 done");
	}
}
