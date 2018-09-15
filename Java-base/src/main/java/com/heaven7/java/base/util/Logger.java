package com.heaven7.java.base.util;

import com.heaven7.java.base.anno.NeedAndroidImpl;

import static com.heaven7.java.base.util.Logger.ANDROID_NAME;

/**
 * {@link #v(String, String, String)} i like
 *
 * @author heaven7
 * @since 1.1.3
 */
@NeedAndroidImpl(ANDROID_NAME)
public class Logger {

    static final String ANDROID_NAME = "org.heaven7.android.base.impl.PrinterImpl";

    private static int sLOG_LEVEL = 6;// default all

    public static final int VERBOSE = 5; /* the lowest */
    public static final int DEBUG = 4;
    public static final int INFO = 3;
    public static final int WARNING = 2;
    public static final int ERROR = 1;
    /**default true(only called internal)*/
    public static boolean IsDebug = true;

    private static final Printer sPrinter;

    static {
        switch (Platforms.getSystemType()) {
            case Platforms.WINDOWS:
            case Platforms.LINUX:
            case Platforms.MAC:
                sPrinter = DefaultPrinter.getDefault();
                break;

            case Platforms.ANDROID:
                try {
                    sPrinter = (Printer) Class.forName(ANDROID_NAME).newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;

            default:
                throw new UnsupportedOperationException("system type = " + Platforms.getSystemType());
        }
    }

    /** enable or disable debug
     * @param debug if debug */
    public static void setDebug(boolean debug) {
        IsDebug = debug;
        if (debug)
            setLevel(VERBOSE);
        else
            setLevel(INFO);
    }

    public static void setLevel(int lowestLevel) {
        switch (lowestLevel) {
            case VERBOSE:
                sLOG_LEVEL = 6;
                break;

            case DEBUG:
                sLOG_LEVEL = 5;
                break;

            case INFO:
                sLOG_LEVEL = 4;

                break;

            case WARNING:
            case ERROR:
                sLOG_LEVEL = 3;
                break;

            default:
                throw new IllegalArgumentException("caused by log lowestLevel="
                        + lowestLevel);
        }
    }

    // verbose
    public static void v(String tag, String msg) {
        if (sLOG_LEVEL > VERBOSE) {
           // Log.v(tag, msg);
            sPrinter.verbose(tag, "", msg);
        }
    }

    public static void v(String tag, String methodTag, String msg) {
        if (sLOG_LEVEL > VERBOSE) {
           // Log.v(tag, "called [ " + methodTag + "() ]: " + msg);
            sPrinter.verbose(tag, methodTag, msg);
        }
    }

    // debug
    public static void d(String tag, String msg) {
        if (sLOG_LEVEL > DEBUG) {
           // Log.d(tag, msg);
            sPrinter.debug(tag, "", msg);
        }
    }

    public static void d(String tag, String methodTag, String msg) {
        if (sLOG_LEVEL > DEBUG) {
           // Log.d(tag, "called [ " + methodTag + "() ]: " + msg);
            sPrinter.debug(tag, methodTag, msg);
        }
    }

    // info
    public static void i(String tag, String msg) {
        if (sLOG_LEVEL > INFO) {
          //  Log.i(tag, msg);
            sPrinter.info(tag, "", msg);
        }
    }

    public static void i(String tag, String methodTag, String msg) {
        if (sLOG_LEVEL > INFO) {
           // Log.i(tag, "called [ " + methodTag + "() ]: " + msg);
            sPrinter.info(tag, methodTag, msg);
        }
    }

    // warning
    public static void w(String tag, String msg) {
        if (sLOG_LEVEL > WARNING) {
          //  Log.w(tag, msg);
            sPrinter.warn(tag, "", msg);
        }
    }

    public static void w(String tag, String methodTag, String msg) {
        if (sLOG_LEVEL > WARNING) {
          //  Log.w(tag, "called [ " + methodTag + "() ]: " + msg);
            sPrinter.warn(tag, methodTag, msg);
        }
    }

    public static void w(String tag, Throwable throwable) {
        if (sLOG_LEVEL > WARNING) {
          //  Log.w(tag, throwable);
            sPrinter.warn(tag, "", throwable);
        }
    }

    // error
    public static void e(String tag, String msg) {
        if (sLOG_LEVEL > ERROR) {
          //  Log.e(tag, msg);
            sPrinter.error(tag, "", msg);
        }
    }

    public static void e(String tag, String methodTag, String msg) {
        if (sLOG_LEVEL > ERROR) {
           // Log.e(tag, "called [ " + methodTag + "() ]: " + msg);
            sPrinter.error(tag, methodTag, msg);
        }
    }

}

