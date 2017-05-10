package com.heaven7.java.os;

/**
 * Simple interface for printing text, allowing redirection to various targets.
 * {@link DefaultPrinter}
 */
public interface Printer {
	/**
	 * Write a line of text to the output. There is no need to terminate the
	 * given string with a newline.
	 */
	void println(String x);

	void warn(String tag, String msg, Throwable t);

	void info(String tag, String method, String msg);
	
	void debug(String tag, String method, String msg);

	void warn(String tag, String method, String msg);
}