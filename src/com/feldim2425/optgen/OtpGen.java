package com.feldim2425.optgen;

import com.feldim2425.optgen.logger.LoggerManager;
import com.feldim2425.optgen.ui2.Dialog;
import com.feldim2425.optgen.utils.JavaFXAdapter;


public class OtpGen {

	public static final String	VERSION			= "0.4.0-beta";
	public static final String	R_DATE			= "30. December 2015";
	public static final String	RESOURCE_PATH	= "assets/otpgen";

	private static OtpGen INSTANCE;

	private ProgramState	state;
	private Thread			mainThread;

	public static void main(String[] args) {
		new OtpGen();
		while (INSTANCE.state == ProgramState.RUNNING) {
			INSTANCE.run();
		}
		INSTANCE.exitNormal();
		INSTANCE.exitForce();
		System.exit(0);
	}

	private OtpGen() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> exitForce()));
		INSTANCE = this;
		mainThread = Thread.currentThread();
		long startTime = System.currentTimeMillis();
		this.state = ProgramState.STARTING;

		LoggerManager.initLogger();

		LoggerManager.getLogger().info("Starting TOTP-Generator (" + VERSION + ")");
		LoggerManager.getLogger().info("© by feldim2425");

		JavaFXAdapter.startFX();
		LoggerManager.getLogger().info("Done! Started in " + (System.currentTimeMillis() - startTime) + " ms");
	}

	private void run() {
		try {

			Thread.sleep(500);
		}
		catch (InterruptedException e) {
			if (!state.isRunning) {
				return;
			}

			LoggerManager.getLogger().info("Stopping TOTP-Generator (Interrupt)");
			state = ProgramState.EXIT_WAITING;
		}
	}

	public ProgramState getState() {
		return state;
	}

	private void exitNormal() {
		Scheduler.stop(false);
	}

	private void exitForce() {
		if (state != ProgramState.EXIT_END) {
			state = ProgramState.EXIT_END;

			Scheduler.stop(true);
			JavaFXAdapter.exitFX();

			if (LoggerManager.getLogger() == null) {
				System.out.println("<NO LOGGER> Exit");
			}
			else {
				LoggerManager.getLogger().info("Exit");
			}
		}
	}

	public void exit(boolean async) {
		if (!state.canExit) {
			return;
		}

		Runnable run = () -> {
			if (Dialog.showYesNo("Quit?", "Do you really want to close this Program?")) {
				LoggerManager.getLogger().info("Stopping TOTP-Generator (User)");
				state = ProgramState.EXIT_WAITING;
				mainThread.interrupt();
			}
		};
		if (async) {
			JavaFXAdapter.runLater(run);
		}
		else {
			JavaFXAdapter.runAndWait(run);
		}
	}

	public static OtpGen getInstance() {
		return INSTANCE;
	}

	public static enum ProgramState {
		STARTING (false, true),
		RUNNING (true, true),
		EXIT_WAITING (false, false),
		EXIT_END (false, false);

		public final boolean	canExit;
		public final boolean	isRunning;

		private ProgramState(boolean canExit, boolean isRunning) {
			this.canExit = canExit;
			this.isRunning = isRunning;
		}
	}
}
