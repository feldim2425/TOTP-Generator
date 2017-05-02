package com.feldim2425.optgen.logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.tuple.Pair;


public class InternalLogger extends Handler {

	private static final LoggerFormatter		FORMATTER	= new LoggerFormatter();
	private static List<Pair<Level, String>>	lines		= Collections.synchronizedList(new ArrayList<>());
	private static List<ILogListener>			listeners	= Collections.synchronizedList(new ArrayList<>());

	@Override
	public void publish(LogRecord record) {
		if (record.getLevel().intValue() >= Level.CONFIG.intValue()) {
			lines.add(Pair.of(record.getLevel(), FORMATTER.format(record)));
			listeners.forEach((l) -> l.onLogLine(record.getLevel(), FORMATTER.format(record)));
		}
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {

	}

	public static List<Pair<Level, String>> getLines() {
		return new ArrayList<>(lines);
	}

	public static void addListener(ILogListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public static void removeListener(ILogListener listener) {
		listeners.remove(listener);
	}

	public interface ILogListener {

		void onLogLine(Level level, String line);
	}
}
