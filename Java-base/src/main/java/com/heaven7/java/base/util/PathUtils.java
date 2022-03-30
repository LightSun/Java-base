package com.heaven7.java.base.util;

/**
 * help handle path.
 * @since 1.2.2
 */
public final class PathUtils {

    public static String getProjectPath() {
        java.net.URL url = PathUtils.class.getProtectionDomain().getCodeSource()
                .getLocation();
        String filePath;
        try {
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        java.io.File file = new java.io.File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }
}
