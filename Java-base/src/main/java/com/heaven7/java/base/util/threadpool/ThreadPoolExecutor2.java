package com.heaven7.java.base.util.threadpool;

import com.heaven7.java.base.util.Throwables;

import java.util.concurrent.*;

/**
 * relative to {@linkplain ThreadPoolExecutor} we handle the exception.
 * @author heaven7
 * @since 1.1.2
 */
public class ThreadPoolExecutor2 extends ThreadPoolExecutor {

    private boolean exceptionStrictly;

    public ThreadPoolExecutor2(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                               BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
    public ThreadPoolExecutor2(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                               BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }
    public ThreadPoolExecutor2(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                               BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }
    public ThreadPoolExecutor2(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                               BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
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
        return new FutureTask2<>(callable, exceptionStrictly);
    }
    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new FutureTask2<>(runnable, value, exceptionStrictly);
    }

    static class FutureTask2<V> extends FutureTask<V>{

        private boolean exceptionStrictly;

        public FutureTask2(Callable<V> callable, boolean exceptionStrictly) {
            super(callable);
            this.exceptionStrictly = exceptionStrictly;
        }
        public FutureTask2(Runnable runnable, V result, boolean exceptionStrictly) {
            super(runnable, result);
            this.exceptionStrictly = exceptionStrictly;
        }
        @Override
        protected void done() {
            // super.done();
            try {
                if (!isCancelled()) get();
            } catch (ExecutionException e) {
                // Exception occurred, deal with it
                Throwables.handleExceptionSmartly(e, exceptionStrictly);
            } catch (InterruptedException e) {
                // Shouldn't happen, we're invoked when computation is finished
                throw new AssertionError(e);
            }
        }
    }

    public static class Builder{
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
        private TimeUnit timeUnit;
        private BlockingQueue<Runnable> workQueue;

        private ThreadFactory threadFactory;
        private RejectedExecutionHandler rejectedExecutionHandler;
        private boolean exceptionStrictly;

        public Builder setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
            return this;
        }
        public Builder setMaximumPoolSize(int maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
            return this;
        }

        public Builder setKeepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            this.keepAliveTime = keepAliveTime;
            assert timeUnit != null;
            return this;
        }

        public Builder setWorkQueue(BlockingQueue<Runnable> workQueue) {
            this.workQueue = workQueue;
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

        public ThreadPoolExecutor build(){
            if(maximumPoolSize <= 0 || keepAliveTime < 0 || corePoolSize <= 0){
                throw new IllegalStateException("param error");
            }
            if(workQueue == null){
                throw new NullPointerException();
            }
            ThreadPoolExecutor2 instance;
            if(rejectedExecutionHandler == null){
                if(threadFactory != null){
                    instance = new ThreadPoolExecutor2(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, workQueue, threadFactory);
                }else{
                    instance = new ThreadPoolExecutor2(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, workQueue);
                }
            }else{
                if(threadFactory == null){
                    instance = new ThreadPoolExecutor2(corePoolSize, maximumPoolSize, keepAliveTime,
                            timeUnit, workQueue, rejectedExecutionHandler);
                }else{
                    instance = new ThreadPoolExecutor2(corePoolSize, maximumPoolSize, keepAliveTime,
                            timeUnit, workQueue, threadFactory, rejectedExecutionHandler);
                }
            }
            instance.setExceptionStrictly(exceptionStrictly);
            return instance;
        }
    }
}
