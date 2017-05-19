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
	 * print the log .
	 * @param tag the tag, often is the class name
	 * @param msg the other message msg to print
	 * @param t the extra throwable to print.
	 */
	void warn(String tag, String msg, Throwable t);

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
	 * print the 'warn' message.
	 * @param tag the tag, often is the class name
	 * @param method the method name
	 * @param msg the extra message 
	 */
	void warn(String tag, String method, String msg);
	
}