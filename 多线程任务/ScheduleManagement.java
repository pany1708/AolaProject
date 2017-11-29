package com.altratek.altraserver.extensions.schedulers;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.altratek.altraserver.extensions.util.DateUtil;
import com.altratek.altraserver.extensions.util.Logger;

public class ScheduleManagement {
	private static final ScheduleManagement instance = new ScheduleManagement();
	private ScheduledExecutorService scheduler;
	private static AtomicInteger threadIdGen = new AtomicInteger();
	public static ScheduleManagement getInstance() {
		return instance;
	}

	private ScheduleManagement() {
		/*
		 * 一台虚拟机仅启动一个线程,绑定多个任务需要按顺序执行, 当任务执行时间较长时,延迟较大, 如果实时要求高,可考虑开一个线程池;
		 */
		scheduler = Executors.newScheduledThreadPool(5, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, "scheduler-" + threadIdGen.getAndIncrement());
				t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						Logger.error("Throwable in scheduler thread:" + t, e);
					}
				});
				return t;
			}
		});
		((ScheduledThreadPoolExecutor)scheduler).setRemoveOnCancelPolicy(true);//当一个任务被cancle的时候，将被移除出队列
	}

	/*
	 * 周期任务 或 单次任务
	 */
	public Future<?> register(Runnable job, JobContext jobContext) {
		long period = jobContext.getPeriod();
		Future<?> futrue;
		if (period > 0) {
			futrue = scheduler.scheduleAtFixedRate(job, jobContext.getDelay(), jobContext.getPeriod(), jobContext.getTimeUnit());
		} else {
			futrue = scheduler.schedule(job, jobContext.getDelay(), jobContext.getTimeUnit());
		}
		return futrue;
	}

	/**
	 * 用schedule里的线程立即异步执行一个任务
	 *
	 * @param job
	 * @return
	 */
	public void execute(Runnable job) {
		scheduler.execute(job);
	}

	/**
	 * 用schedule里的线程定时执行一个任务，如果给定的时间已过，则这个任务不会执行
	 *
	 * @param job
	 *            要执行的任务
	 * @param HHmmss
	 *            表示当天的启动时间，按时分秒给出，格式为"HH:mm:ss"
	 * @return 时间已过则返回null
	 */
	public Future<?> schedule(Runnable job, String HHmmss) {
		long delay = DateUtil.getTodayMillisecond(HHmmss) - System.currentTimeMillis();
		return scheduleDelay(job, delay);
	}

	public Future<?> scheduleDelay(Runnable job, long delayMs) {
		if (delayMs < 0) {
			return null;
		} else {
			return scheduler.schedule(job, delayMs, TimeUnit.MILLISECONDS);
		}
	}

	public Future<?> scheduleRepeatedly(Runnable job, long delayMs, long periodMs) {
		Future<?> future;
		if (periodMs > 0) {
			future = scheduler.scheduleAtFixedRate(job, delayMs, periodMs, TimeUnit.MILLISECONDS);
		} else {
			future = scheduleDelay(job, delayMs);
		}
		return future;
	}

	public void shutdown() {
		if (!scheduler.isShutdown()) {
			scheduler.shutdown();
		}
	}
}
