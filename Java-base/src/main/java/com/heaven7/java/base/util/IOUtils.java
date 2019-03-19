package com.heaven7.java.base.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.1.3
 * @author heaven7
 */
public class IOUtils {
    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * read stream as bytes.
     * @param in the input
     * @return the data as bytes
     * @throws IOException
     * @since 1.1.7
     */
    public static byte[] read(InputStream in) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
       do {
            int count = in.read(buffer);
            if(count == -1){
                break;
            }
            baos.write(buffer, 0, count);
        }while (true);
       return baos.toByteArray();
    }
    /**
     * read file as string
     * @param path the file path. absolute path
     * @return the string content.
     */
    public static String readFileAsString(String path){
        Reader reader = null;
        try {
            reader = new FileReader(path);
            return readString(reader);
        }catch (IOException e){
            throw new RuntimeException(e);
        }finally {
            IOUtils.closeQuietly(reader);
        }
    }

    /**
     * read the reader as string
     * @param r the reader
     * @return the content as string
     * @throws IOException If an I/O error occurs
     */
    public static String readString(Reader r) throws IOException {
        BufferedReader br = r instanceof BufferedReader ? (BufferedReader) r : new BufferedReader(r);

        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * read the reader as string lines
     * @param r the reader
     * @return the string lines
     * @throws IOException If an I/O error occurs
     * @since 1.1.3.6
     */
    public static List<String> readStringLines(Reader r) throws IOException {
        BufferedReader br = r instanceof BufferedReader ? (BufferedReader) r : new BufferedReader(r);
        List<String> lines = new ArrayList<>();

        String str;
        while ((str = br.readLine()) != null) {
            lines.add(str);
        }
        return lines;
    }

    /**
     * read string and close reader
     * @param r the reader
     * @return the string context
     * @throws IOException If an I/O error occurs
     */
    public static String readStringThenClose(Reader r) throws IOException {
        BufferedReader br = r instanceof BufferedReader ? (BufferedReader) r : new BufferedReader(r);
        try{
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        }finally{
            closeQuietly(br);
        }
    }

    /**
     * close the data
     * @param closeable the object which can be close
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                // Ignore.
            }
        }
    }

    /**
     * copy the input stream to out.
     * @param input the input stream
     * @param output the output stream
     * @return the count of read.
     * @throws IOException
     */
    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
