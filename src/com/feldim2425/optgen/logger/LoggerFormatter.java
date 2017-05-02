package com.feldim2425.optgen.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


public class LoggerFormatter extends Formatter {

	public LoggerFormatter() {
		super();
	}

	@Override
	public String format(final LogRecord record) {
		final StringBuilder form = new StringBuilder();
		form.append('[').append(calcDate(record.getMillis())).append("] [").append(record.getLevel().getName())
				.append("] [").append(record.getLoggerName()).append("] : ").append(record.getMessage());
		final Throwable thrown = record.getThrown();
		if (thrown != null) {
			form.append('\n');
			final StringWriter wr = new StringWriter();

			try (PrintWriter writer = new PrintWriter(wr)) {
				thrown.printStackTrace(writer);
				writer.flush();
			}

			form.append(wr.toString());
		}

		form.append('\n');

		return form.toString();
	}

	private String calcDate(final long millisecs) {
		final SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
		final Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}
}
