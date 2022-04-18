package com.heaven7.java.base.util;

import java.util.concurrent.TimeUnit;

/**
 *  //compute, io. current, single, executor. main(android)
 * the scheduler class help we handle asynchronous or synchronous task.
 * 
 * @author heaven7
 * @since 1.1.7
 */
public interface Scheduler {

	Worker newWorker();

	interface Worker{
		/**
		 * schedule the task right now
		 * @param task the task to schedule
		 * @return  the future
		 */
		Disposable schedule(Runnable task);
		/**
		 * schedule the task by target delay.
		 *
		 * @param delay
		 *            the delay in mills
		 * @param task
		 *            the runnable task,
		 * @param unit the time unit
		 * @return  the future
		 */
		Disposable scheduleDelay(Runnable task, long delay, TimeUnit unit);

		/**
		 * schedule a task run periodically
		 * @param task the task.
		 * @param initDelay the init delay
		 * @param period the period
		 * @param unit the time unit
		 * @return  the future
		 */
		Disposable schedulePeriodically(Runnable task, long initDelay, long period, TimeUnit unit);
	}

}
