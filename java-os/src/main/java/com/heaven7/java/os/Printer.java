package com.heaven7.java.os;
/**
 * Simple interface for printing text, allowing redirection to various
 * targets.  Standard implementations are {@link android.util.LogPrinter},
 * {@link android.util.StringBuilderPrinter}, and
 * {@link android.util.PrintWriterPrinter}.
 */
public abstract class Printer {
    /**
     * Write a line of text to the output.  There is no need to terminate
     * the given string with a newline.
     */
	public abstract void println(String x);

	public abstract void warn(String tag, String msg, Throwable t);
	
	public abstract void info(String tag, String method, String msg);
}