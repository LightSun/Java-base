package com.heaven7.java.base.util.threadpool;

import com.heaven7.java.base.util.Throwables;

import java.util.concurrent.*;

/**
 * @author heaven7
 * @since 1.1.2
 */
public class ScheduledThreadPoolExecutor2 extends ScheduledThreadPoolExecutor {

    private boolean exceptionStrictly;

    public ScheduledThreadPoolExecutor2(int corePoolSize) {
        super(corePoolSize);
    }

    public ScheduledThreadPoolExecutor2(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    public ScheduledThreadPoolExecutor2(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    public ScheduledThreadPoolExecutor2(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }


    public boolean isExceptionStrictly() {
        return exceptionStrictly;
    }
    public void setExceptionStrictly(boolean exceptionStrictly) {
        this.exceptionStrictly = exceptionStrictly;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Throwables.handleExceptionSmartly(t, exceptionStrictly);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new ThreadPoolExecutor2.FutureTask2<>(callable, exceptionStrictly);
    }
    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new ThreadPoolExecutor2.FutureTask2<>(runnable, value, exceptionStrictly);
    }

    public static class Builder{
        private int corePoolSize;
        private ThreadFactory threadFactory;
        private RejectedExecutionHandler rejectedExecutionHandler;
        private boolean exceptionStrictly;

        public Builder setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
            return this;
        }
        public Builder setThreadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            return this;
        }
        public Builder setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
            this.rejectedExecutionHandler = rejectedExecutionHandler;
            return this;
        }
        public Builder setExceptionStrictly(boolean exceptionStrictly) {
            this.exceptionStrictly = exceptionStrictly;
            return this;
        }
        public ScheduledThreadPoolExecutor build(){
            ScheduledThreadPoolExecutor2 instance;
            if(rejectedExecutionHandler == null){
                instance = new ScheduledThreadPoolExecutor2(corePoolSize);
            }else{
                if(threadFactory == null){
                    instance = new ScheduledThreadPoolExecutor2(corePoolSize, rejectedExecutionHandler);
                }else{
                    instance = new ScheduledThreadPoolExecutor2(corePoolSize, threadFactory, rejectedExecutionHandler);
                }
            }
            instance.setExceptionStrictly(exceptionStrictly);
            return instance;
        }
    }

}
