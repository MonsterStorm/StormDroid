package com.cst.stormdroid.net;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread Pool
 * @author MonsterStorm
 * @version 1.0
 */
public class SDThreadPool {
	/**
	 * core threads size
	 */
	private static final int CORE_POOL_SIZE = 5;
	/**
	 * max threads
	 */
	private static final int MAX_POOL_SIZE = 100;
	/**
	 * keep alive time (Max time that a thread wait in the queue, at this time, if has no task to execute, then the thread will quit), default 1min
	 */
	private static final int KEEP_ALIVE_TIME = 60000;
	
	/**
	 * Blocking Queue, only create thread when core thread are occupied and blocking queue is full
	 */
	private BlockingQueue<Runnable> workQueue;

	/**
	 * Thread Pool
	 */
	private ThreadPoolExecutor mExecutor;

//	private ExecutorService mExecutorService;
	
	/**
	 * Thread Factory
	 */
	private ThreadFactory threadFactory;

	/**
	 * public constructor
	 */
	public SDThreadPool() {
		workQueue = new ArrayBlockingQueue<Runnable>(20);
		threadFactory = new ThreadFactory() {
			private final AtomicInteger mCount = new AtomicInteger(1);

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "SDThreadPool thread:" + mCount.getAndIncrement());
			}
		};
		mExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, threadFactory);
		//fixed count excutor
//		mExecutorService = Executors.newFixedThreadPool(CORE_POOL_SIZE, threadFactory);
	}
	
	/**
	 * pull a thread from the poll and execute runnable
	 * @param runnable
	 */
	public Future<?> submit(Runnable runnable) {
		return mExecutor.submit(runnable);
	}
}
