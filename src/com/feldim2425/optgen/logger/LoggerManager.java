package com.feldim2425.optgen.logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;


public final class LoggerManager {

	private static Logger logger;

	public static void initLogger() {
		logger = Logger.getLogger("TOTP-Generator");
		final Logger rootLogger = Logger.getLogger("");
		final Handler[] handlers = rootLogger.getHandlers();
		if (handlers[0] instanceof ConsoleHandler) {
			rootLogger.removeHandler(handlers[0]);
		}
		rootLogger.addHandler(new LogHandler());
		rootLogger.addHandler(new InternalLogger());
		/* 
		@formatter:off 
		try {
		 
			final FileHandler logFileHandler = new FileHandler(newLogFile());
			logFileHandler.setFormatter(new LoggerFormatter());
			rootLogger.addHandler(logFileHandler);
		}
		catch (SecurityException | IOException e) {
			logger.log(Level.WARNING, "Could not attach Logfile-Hanlder", e);
		}
		@formatter:on
		*/

	}

	/*
	 @formatter:off
	 private static String newLogFile() {
		final DateFormat format = new SimpleDateFormat("yy-MM-dd_HH-mm", Locale.US);
		final File logDir = new File(StreamChatRoBot.getInstance().getWorkDirectory(), "logs");

		if (!logDir.exists()) {
			logDir.mkdir();
		}

		final String name = "log_" + format.format(System.currentTimeMillis());
		File logFile = new File(logDir, name + ".txt");

		int index = 0;
		while (logFile.exists()) {
			logFile = new File(logDir, name + "_" + index + ".txt");
			index++;
		}
		return logFile.getAbsolutePath();
	}
	@formatter:on
	*/

	public static Logger getLogger() {
		return logger;
	}

	private LoggerManager() {
		// EMPTY
	}
}
