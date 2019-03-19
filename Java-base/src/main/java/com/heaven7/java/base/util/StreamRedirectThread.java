package com.heaven7.java.base.util;

import java.io.*;

/**
 * StreamRedirectThread is a thread which copies it's input to
 * it's output and terminates when it completes.
 *
 * @author heaven7
 * @since 1.1.7
 */
public class StreamRedirectThread extends Thread {

    private static final int BUFFER_SIZE = 2048;
    protected final Reader in;
    protected final Writer out;

    /**
     * Set up for copy.
     * @param name  Name of the thread
     * @param in    Stream to copy from
     * @param out   Stream to copy to
     */
    public StreamRedirectThread(String name, InputStream in, OutputStream out) {
        super(name);
        this.in = new InputStreamReader(in);
        this.out = new OutputStreamWriter(out);
       // setPriority(Thread.MAX_PRIORITY-1);
    }

    @Override
    public void run() {
        onRedirectStart();
        boolean success = false;
        try {
            char[] cbuf = new char[BUFFER_SIZE];
            int count;
            while ((count = in.read(cbuf, 0, BUFFER_SIZE)) >= 0) {
                out.write(cbuf, 0, count);
            }
            out.flush();
            success = true;
        } catch(IOException exc) {
            success = false;
            System.err.println("Child I/O Transfer - " + exc);
        }finally {
            onRedirectEnd(success);
        }
    }
    protected void onRedirectStart(){

    }

    /**
     * called on redirect end.
     * @param success true if success . false if an io error occurs.
     */
    protected void onRedirectEnd(boolean success) {

    }
}
