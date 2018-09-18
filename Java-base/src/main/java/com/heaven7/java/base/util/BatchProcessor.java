package com.heaven7.java.base.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * the processor which used to process batch tasks.
 * @author heaven7
 * @since 1.1.4
 */
public abstract class BatchProcessor {

    private final AtomicInteger mCount = new AtomicInteger();
    private final AtomicBoolean mMarkDone = new AtomicBoolean();
    private final AtomicBoolean mDoing = new AtomicBoolean();

    /**
     * add task count. often called before start async task and end task.
     * @param delta the delta count . can < 0.
     */
    public final void addCount(int delta){
        int cur = mCount.get();
        do {
            if(mCount.compareAndSet(cur, cur + delta)){
                break;
            }
        }while (true);
    }

    /** get current count . which often is the tasks */
    public final int getCount(){
        return mCount.get();
    }

    /**
     * mark batch task start.
     */
    public void markStart(){
        if(!mDoing.compareAndSet(false, true)){
            throw new IllegalStateException("already doing");
        }
    }
    /**
     * mark batch task end. call this will auto check all task done.
     */
    public void markEnd(){
        if(!mMarkDone.compareAndSet(false, true)){
            throw new IllegalStateException("markEnd() already called.");
        }
        checkDone();
    }

    /**
     * called on tasks end.
     * @param delta the delta count of tasks are end.
     */
    public void onTasksEnd(int delta){
        Throwables.checkNonPositiveValue(delta);
        addCount(-delta);
        checkDone();
    }

    /**
     * check all task is done or not.
     * when {@linkplain #markEnd()} called and task count = 0. the all task is done.
     */
    protected void checkDone(){
        if(mMarkDone.get() && mCount.get() == 0){
            onDone();
            reset();
        }
    }

    /**
     * reset state.
     */
    protected void reset(){
        mCount.set(0);
        mMarkDone.set(false);
        mDoing.set(false);
    }

    /**
     * called on all batch task done
     */
    protected abstract void onDone();
}
