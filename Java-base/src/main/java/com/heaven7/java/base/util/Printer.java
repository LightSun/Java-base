package com.heaven7.java.base.util;

/**
 * Simple interface for printing text, allowing redirection to various targets.
 * {@link DefaultPrinter}
 * @since 1.0.2
 */
public interface Printer {
	/**
	 * Write a line of text to the output. There is no need to terminate the
	 * given string with a newline.
	 * @param x  the msg to print
	 */
	void println(String x);

	/**
	 * print the 'info' message.
	 * @param tag the tag, often is the class name
	 * @param method the method name
	 * @param msg the extra message
	 */
	void info(String tag, String method, String msg);

	/**
	 * print the 'debug' message.
	 * @param tag the tag, often is the class name
	 * @param method the method name
	 * @param msg the extra message
	 */
	void debug(String tag, String method, String msg);

	/**
	 * print the log .
	 * @param tag the tag, often is the class name
	 * @param method the method name.
	 * @param t the extra throwable to print.
	 */
	void warn(String tag, String method, Throwable t);

	/**
	 * print the 'warn' message.
	 * @param tag the tag, often is the class name
	 * @param method the method name
	 * @param msg the extra message 
	 */
	void warn(String tag, String method, String msg);

	/**
	 * print the 'verbose' message.
	 * @param tag the tag, often is the class name
	 * @param method the method name
	 * @param msg the extra message
	 * @since 1.1.3
	 */
	void verbose(String tag, String method, String msg);

	/**
	 * print the 'verbose' message.
	 * @param tag the tag, often is the class name
	 * @param method the method name
	 * @param msg the extra message
	 * @since 1.1.3
	 */
	void error(String tag, String method, String msg);

	/**
	 * print the 'verbose' message.
	 * @param tag the tag, often is the class name
	 * @param method the method name
	 * @param t the throwable
	 * @since 1.1.3
	 */
	void error(String tag, String method, Throwable t);
	
}