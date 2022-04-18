package com.heaven7.java.base.util;

import java.util.concurrent.Future;

/**
 * indicate this object can be dispose.
 * @author heaven7 
 * @since 1.1.7
 */
public interface Disposable {

    /**
     * the empty
     * @since 1.1.8
     */
    Disposable EMPTY = new Disposable() {
        @Override
        public void dispose() {

        }
    };
    /**
     * the failed: means schedule task failed.
     * @since 1.1.8
     */
    Disposable FAILED = new Disposable() {
        @Override
        public void dispose() {

        }
    };
    /**
     * dispose something. often used to release the resource at last. eg: when destroy.
     */
    void  dispose();

    class FutureDisposable implements Disposable{
        private Future<?> future;
        public FutureDisposable(Future<?> future) {
            this.future = future;
        }
        @Override
        public void dispose() {
            if(future != null){
                future.cancel(true);
                future = null;
            }
        }
    }

}
