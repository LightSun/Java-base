package com.heaven7.java.base.util;

/**
 * the default printer.
 * @author heaven7
 * @since 1.0.2
 * @see Printer
 */
public class DefaultPrinter extends BasePrinter implements Printer{
	
	private static DefaultPrinter sPrinter;
	
	/**
	 * get an instance of DefaultPrinter.
	 * @return default printer
	 */
	public static DefaultPrinter getDefault(){
		return sPrinter !=null ? sPrinter : (sPrinter = new DefaultPrinter());
	}
	@Override
	public void warn(String tag, String method, String msg) {
		System.err.println(tag + "__WARN__ >>> called " + method + "(): " + msg);
	}

	@Override
	public void verbose(String tag, String method, String msg) {
		System.out.println(tag + "__VERBOSE__ >>> called " + method + "(): " + msg);
	}

	@Override
	public void error(String tag, String method, String msg) {
		System.out.println(tag + "__ERROR__ >>> called " + method + "(): " + msg);
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
