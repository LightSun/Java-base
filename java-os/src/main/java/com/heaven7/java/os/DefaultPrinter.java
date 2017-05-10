package com.heaven7.java.os;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DefaultPrinter implements Printer{

	@Override
	public void println(String x) {
		System.out.println(x);
	}

	@Override
	public void warn(String tag, String msg, Throwable t) {
		System.err.println(tag + " >>> " + msg + toString(t));
	}

	@Override
	public void warn(String tag, String method, String msg) {
		System.err.println(tag + "__warn__ >>> called " + method + "(): " + msg);
	}
	
	@Override
	public void info(String tag, String method, String msg) {
		System.out.println(tag + "__info__ >>> called " + method + "(): " + msg);
	}

	@Override
	public void debug(String tag, String method, String msg) {
		System.out.println(tag + "__debug__ >>> called " + method + "(): " + msg);
	}
	
	public static String toString(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		t.printStackTrace(pw);
		Throwable cause = t.getCause();
		while (cause != null) {
			cause.printStackTrace(pw);
			cause = cause.getCause();
		}
		pw.flush();
		String data = sw.toString();
		pw.close();
		return data;
	}

}
