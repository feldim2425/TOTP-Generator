package com.feldim2425.optgen.logger;

import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


public class LogHandler extends Handler {

	private static final LoggerFormatter FORMATTER = new LoggerFormatter();

	@Override
	public void publish(LogRecord record) {
		PrintStream stream;
		if (record.getLevel() == Level.WARNING || record.getLevel() == Level.SEVERE) {
			stream = System.err;
		}
		else {
			stream = System.out;
		}
		stream.print(FORMATTER.format(record));
		stream.flush();
	}

	@Override
	public void flush() {
		System.err.flush();
		System.out.flush();
	}

	@Override
	public void close() throws SecurityException {
	}
}
