package com.heaven7.java.base.util;

/**
 * the default printer.
 * @author heaven7
 * @since 1.0.2
 * @see Printer
 */
public class DefaultPrinter implements Printer{
	
	private static DefaultPrinter sPrinter;
	
	/**
	 * get an instance of DefaultPrinter.
	 * @return default printer
	 */
	public static DefaultPrinter getDefault(){
		return sPrinter !=null ? sPrinter : (sPrinter = new DefaultPrinter());
	}
	
	@Override
	public void println(String x) {
		System.out.println(x);
	}

	@Override
	public void warn(String tag, String msg, Throwable t) {
		System.err.println(tag + " >>> " + msg + " >>> " + Throwables.getStackTraceAsString(t));
	}

	@Override
	public void warn(String tag, String method, String msg) {
		System.err.println(tag + "__WARN__ >>> called " + method + "(): " + msg);
	}
	
	@Override
	public void info(String tag, String method, String msg) {
		System.out.println(tag + "__INFO__ >>> called " + method + "(): " + msg);
	}

	@Override
	public void debug(String tag, String method, String msg) {
		System.out.println(tag + "__DEBUG__ >>> called " + method + "(): " + msg);
	}
	

}
