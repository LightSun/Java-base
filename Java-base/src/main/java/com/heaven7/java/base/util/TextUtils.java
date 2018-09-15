package com.heaven7.java.base.util;

import com.heaven7.java.base.anno.Nullable;

/**
 * @since 1.1.3
 */
public class TextUtils {

    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * get the relative path for prefix path. eg:  absPath = 'e:/data/xxx/aaa',  prefix ='e:/data/', so result is 'xxx/aaa'
     *
     * @param absPath    the absolute path
     * @param prefixPath the prefix path of abspath
     * @return the relative path
     */
    public static String getRelativePathForPrefix(String absPath, String prefixPath) {
        int index = absPath.indexOf(prefixPath);
        if (index == -1) {
            throw new IllegalArgumentException("can't getRelativePath for absPath = "
                    + absPath + " ,prefixPath = " + prefixPath);
        }
        String result = absPath.substring(index + prefixPath.length());
        if (result.startsWith("/")) {
            result = result.substring(1);
        } else if (result.startsWith("\\")) {
            result = result.substring(1);
        }
        return result;
    }

    /**
     * get the relative path for suffix path. eg:  absPath = 'e:/data/xxx/aaa',  subPath ='xxx', so result is 'xxx/aaa'
     *
     * @param absPath    the absolute path
     * @param suffixPath the suffix path of abspath
     * @return the relative path
     */
    public static String getRelativePathForSuffix(String absPath, String suffixPath) {
        int index = absPath.indexOf(suffixPath);
        if (index == -1) {
            throw new IllegalArgumentException("can't getRelativePath for absPath = "
                    + absPath + " ,subPath = " + suffixPath);
        }
        String result = absPath.substring(index);
        if (result.startsWith("/")) {
            result = result.substring(1);
        } else if (result.startsWith("\\")) {
            result = result.substring(1);
        }
        return result;
    }

    /**
     * indicate the path is relative path or absolute path.
     * @param path the path to judge
     * @return true if is relative path
     */
    public static boolean isRelativePath(String path){
        if(path.startsWith("/")){
            return false;
        }
        if(Platforms.isWindows()) {
            for (char ch : ALPHABET) {
                if (path.startsWith(ch + ":")) {
                    return false;
                }
            }
        }
        return true;
    }
}
