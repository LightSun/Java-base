package com.heaven7.java.base.util;

import com.heaven7.java.base.anno.NeedAndroidImpl;
import com.heaven7.java.base.anno.Platform;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import static com.heaven7.java.base.util.ResourceLoader.ANDROID_NAME;

/**
 * @author heaven7
 * @since 1.1.3
 */
@NeedAndroidImpl(ANDROID_NAME)
public abstract class ResourceLoader {

    static final String ANDROID_NAME = "org.heaven7.android.base.impl.ResourceLoaderImpl";

    private static final ResourceLoader sInstance;

    static {
        String className;
        switch (Platforms.getSystemType()) {
            case Platforms.WINDOWS:
            case Platforms.LINUX:
            case Platforms.MAC:
                className = PcResourceLoader.class.getName();
                break;

            case Platforms.ANDROID:
                className = ANDROID_NAME;
                break;

            default:
                throw new UnsupportedOperationException("system type = " + Platforms.getSystemType());
        }
        try {
            sInstance = (ResourceLoader) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResourceLoader getDefault() {
        if (sInstance == null) {
            throw new IllegalStateException("platform not support now.");
        }
        return sInstance;
    }

    /**
     * load file as stream . the file path may be absolute or relative.
     *
     * @param context the context.
     * @param path    the path
     * @return the content string
     */
    public abstract InputStream loadFileAsStream(Object context, String path) throws IOException;

    /**
     * indicate the file is exist or not.
     * @param context the context
     * @param path the path . may be relative or absolute
     * @return true if the file exist.
     * @since 1.1.3.2
     */
    public final boolean isFileExists(Object context, String path){
        InputStream in = null;
        try {
            in = loadFileAsStream(context, path);
            return in != null;
        }catch (IOException e){
            return false;
        }finally {
            IOUtils.closeQuietly(in);
        }
    }
    /**
     * load file as string , this often used for config file.
     *
     * @param context the context.
     * @param path    the path
     * @return the content string
     */
    public String loadFileAsString(Object context, String path) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(loadFileAsStream(context, path));
            return IOUtils.readString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public InputStream loadUrl(String path) {
        try {
            URL url = new URL(path);
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties loadFileAsProperties(Object context, String path) {
        InputStream in = null;
        try {
            in = loadFileAsStream(context, path);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
        return loadProperties(in, "load file failed. path = " + path);
    }

    public static Properties loadProperties(InputStream in, String exceptionMsg) {
        Properties prop = new Properties();
        try {
            prop.load(in);
            Object value;
            for (Map.Entry<Object, Object> en : prop.entrySet()) {
                value = en.getValue();
                if (value instanceof String) {
                    en.setValue(((String) value).trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(exceptionMsg);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return prop;
    }

    @Platform
    static class PcResourceLoader extends ResourceLoader {
        @Override
        public InputStream loadFileAsStream(Object context, String path) throws IOException{
            if (TextUtils.isRelativePath(path)) {
                return ResourceLoader.class.getClassLoader().getResourceAsStream(path);
            } else {
                 return new FileInputStream(path);
            }
        }
    }
}
