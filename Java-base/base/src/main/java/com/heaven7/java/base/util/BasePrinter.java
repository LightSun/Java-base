package com.heaven7.java.base.util;

/**
 * @author heaven7
 * @since 1.1.3
 */
public abstract class BasePrinter implements Printer {

    @Override
    public void println(String x) {
        System.out.println(x);
    }
    @Override
    public void warn(String tag, String method, Throwable t) {
        warn(tag, method, Throwables.getStackTraceAsString(t));
    }

    @Override
    public void error(String tag, String method, Throwable t) {
        error(tag, method, Throwables.getStackTraceAsString(t));
    }
}
