package com.heaven7.java.base.util;

import java.util.concurrent.ThreadFactory;

/**
 * the thread proxy.
 * @author heaven7
 * @since 1.1.9
 */
public abstract class ThreadProxy implements Disposable{

    /**
     * start a new thread with the runner. note one thread-proxy bind one thread,
     * call multi times have nothing effect.
     * @param runnable the task to run on the thread
     */
    public abstract void start(Runnable runnable);


    public static ThreadProxy create(ThreadFactory factory){
        return new ThreadProxyImpl(factory);
    }

    private static class ThreadProxyImpl extends ThreadProxy{
        private final ThreadFactory mFactory;
        private volatile Thread mThread;

        public ThreadProxyImpl(ThreadFactory factory) {
            this.mFactory = factory;
        }
        public void start(Runnable runnable){
            if(mThread == null){
                mThread = mFactory.newThread(runnable);
                mThread.start();
            }
        }
        @Override
        public void dispose() {
            if(mThread != null) {
                mThread.interrupt();
                mThread = null;
            }
        }
    }
}
