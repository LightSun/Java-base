package com.heaven7.java.base.util;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * this class help we read text file as line and line .
 * Created by heaven7
 * @since 1.1.3
 */
public final class TextReadHelper<Line>{

    private final Callback<Line> mCallback;

    public TextReadHelper(Callback<Line> mCallback) {
        this.mCallback = mCallback;
    }

    public List<Line> read(Object context, String url) throws LoadException {
        List<Line> results = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = mCallback.open(context, url);
            String line;
            while((line = reader.readLine()) != null){
                Line l1 = mCallback.parse(line);
                if(l1 != null){
                    results.add(l1);
                }
            }
        } catch (IOException e) {
            throw new LoadException(e);
        }finally {
            IOUtils.closeQuietly(reader);
        }
        return results;
    }
    public static abstract class Callback<Line>{
        /**
         * open the link to a reader. default is read as a file. support read assets on android.
         * @param context the context
         * @param link the link. may be absolute file name, relative path , or a url.
         * @return the buffer reader.
         * @see ResourceLoader
         * @throws IOException if an I/O error occurs
         */
        public BufferedReader open(Object context, String link) throws IOException{
            return new BufferedReader(new InputStreamReader(ResourceLoader.getDefault().loadFileAsStream(context, link)));
        }
        /**
         * parse the line into a object.
         * @param line the line str
         * @return the result. can be null.
         */
        public abstract Line parse(String line);
    }

    public static class LoadException extends RuntimeException {
        public LoadException() {
        }
        public LoadException(String message) {
            super(message);
        }
        public LoadException(String message, Throwable cause) {
            super(message, cause);
        }
        public LoadException(Throwable cause) {
            super(cause);
        }
    }


}
