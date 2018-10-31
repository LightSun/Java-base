package com.heaven7.java.base.util;

import java.util.Properties;

/**
 * @author heaven7
 * @since 1.1.3
 */
public class Platforms {

    public static final byte WINDOWS = 1;
    public static final byte MAC     = 2;
    public static final byte LINUX  = 3;
    public static final byte ANDROID  = 4;
    public static final byte UNKNOWN  = 5;
    private static final byte SYSTEM_TYPE;

    static {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");

        if (os.contains("Windows")) {
            SYSTEM_TYPE = WINDOWS;
        } else if (os.contains("OS X")) {
            SYSTEM_TYPE = MAC;
        }else{
            String vendor = prop.getProperty("java.vm.vendor");
            if(!Predicates.isEmpty(vendor) && vendor.contains("Android")){
                SYSTEM_TYPE = ANDROID;
            }else if(os.contains("Linux")){
                SYSTEM_TYPE = LINUX;
            }else {
                SYSTEM_TYPE = UNKNOWN;
            }
        }
    }
    public static byte getSystemType(){
        return SYSTEM_TYPE;
    }
    public static boolean isLinux() {
        return SYSTEM_TYPE == LINUX;
    }
    public static boolean isWindows() {
        return SYSTEM_TYPE == WINDOWS;
    }
    public static boolean isAndroid() {
        return SYSTEM_TYPE == ANDROID;
    }
    public static boolean isMac() {
        return SYSTEM_TYPE == MAC;
    }
    public static String getNewLine() {
        return getSystemType() == WINDOWS ? "\r\n" : "\n";
    }
}
