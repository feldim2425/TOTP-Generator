package com.feldim2425.optgen;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public final class Scheduler {

	private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	public static ScheduledFuture<?> runLater(Runnable run, long delay) {
		return executor.schedule(run, delay, TimeUnit.MILLISECONDS);
	}

	public static Future<?> runAsync(Runnable run) {
		return executor.submit(run);
	}

	public static ScheduledFuture<?> repeat(Runnable run, long startDelay, long sequenceDelay) {
		return executor.scheduleWithFixedDelay(run, startDelay, sequenceDelay, TimeUnit.MILLISECONDS);
	}

	public static void stop(boolean now) {
		if (!now) {
			executor.shutdown();
			try {
				executor.awaitTermination(3, TimeUnit.SECONDS);
			}
			catch (InterruptedException e) {
			}
		}
		if (!executor.isShutdown()) {
			executor.shutdownNow();
		}
	}

	private Scheduler() {

	}
}
