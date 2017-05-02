package com.feldim2425.optgen.utils;

import java.util.concurrent.atomic.AtomicBoolean;

import com.sun.javafx.application.PlatformImpl;


/**
 * I know that I should create an Application-Class. But I decided to use JavaFX
 * after writing the Main class and I want to manage the Program (Thread,
 * Startup, Exit,...) myself.<br>
 * <br>
 * This Class is a &quot;hack&quot; for the Application API to use the API
 * without Application <br>
 */
@SuppressWarnings("restriction")
public final class JavaFXAdapter {

	private static AtomicBoolean started = new AtomicBoolean();

	/**
	 * Start JavaFX Platform
	 */
	public static void startFX() {
		if (!started.get()) {
			PlatformImpl.startup(() -> started.set(true));
			PlatformImpl.setImplicitExit(false);
		}
	}

	/**
	 * Stop JavaFX Platform
	 */
	public static void exitFX() {
		if (started.get()) {
			PlatformImpl.exit();
		}
	}

	/**
	 * Check if the current Thread is the JavaFX Application Thread
	 * 
	 * @return true if the current Thread is the Application Thread
	 */
	public static boolean isFXThread() {
		return started.get() && PlatformImpl.isFxApplicationThread();
	}

	/**
	 * @return true if the JavaFX Platform has Started
	 */
	public static boolean hasStarted() {
		return started.get();
	}

	/**
	 * Execute Runnable in the JavaFX Application Thread and wait until the
	 * run() method has returned
	 * 
	 * @param run
	 *            Runnable to execute
	 */
	public static void runAndWait(Runnable run) {
		PlatformImpl.runAndWait(run);
	}

	/**
	 * Execute Runnable in the JavaFX Application Thread
	 * 
	 * @param run
	 *            Runnable to execute
	 */
	public static void runLater(Runnable run) {
		PlatformImpl.runLater(run);
	}

	private JavaFXAdapter() {

	}
}
