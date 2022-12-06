package com.heaven7.java.base.util;

import java.io.*;

/**
 * the command helper.
 * @author heaven7
 * @since 1.2.8
 */
public class CmdHelper {

    private final String[] cmds;
    private final String cmd_log;

    public CmdHelper(String... cmds) {
        this.cmds = cmds;
        StringBuilder sb = new StringBuilder();
        for(int i = 0 , size = cmds.length ; i < size ; i ++){
            sb.append(cmds[i]);
            if(i != size -1){
                sb.append(" ");
            }
        }
        cmd_log = sb.toString();
    }

    public String getCmdActually(){
        return cmd_log;
    }
    public boolean execute(){
        return execute(new InheritIoCallback());
    }
    public boolean execute(Callback callback){
        System.out.println("start run: " + cmd_log);
       // PerformanceHelper helper = new PerformanceHelper();
       // helper.begin();
        BufferedReader reader = null;
        boolean success = false;
        try {
            ProcessBuilder pb = new ProcessBuilder(cmds);
            pb.redirectErrorStream(true);
            callback.beforeStartCmd(this, pb);
            Process process = pb.start();
            process.waitFor();
            success = process.exitValue() == 0;
            System.out.println(cmd_log + "  ,exitValue = " + process.exitValue());

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            callback.onStart(this);
            String line;
            while( (line = reader.readLine()) != null){
                callback.collect(this, line);
            }
            callback.onEnd(this);
        }catch (Exception e){
            callback.onFailed(this, e);
            return false;
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                   // e.printStackTrace();
                }
            }
           // helper.print("CmdHelper_execute");
        }
        return success;
    }
    public boolean execute2(Callback callback){
        System.out.println("start run: " + cmd_log);
      //  PerformanceHelper helper = new PerformanceHelper();
      //  helper.begin();
        boolean success = false;
        try {
            final ProcessBuilder pb = new ProcessBuilder(cmds);
            pb.redirectErrorStream(true);
            callback.beforeStartCmd(this, pb);
            Process process = pb.start();
            callback.onStart(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader reader = null;
                    try{
                        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        while( (line = reader.readLine()) != null){
                            callback.collect(CmdHelper.this, line);
                        }
                        callback.onEnd(CmdHelper.this);
                    }catch(Exception e){
                        callback.onFailed(CmdHelper.this, e);
                        e.printStackTrace();
                    }finally {
                        IOUtils.closeQuietly(reader);
                    }
                }
            }).start();
            process.waitFor();
            success = process.exitValue() == 0;
            System.out.println(cmd_log + "  ,exitValue = " + process.exitValue());

        }catch (Exception e){
            callback.onFailed(this, e);
            return false;
        }finally {
        //    helper.print("CmdHelper_execute");
        }
        return success;
    }
    public interface Callback{
        void collect(CmdHelper helper, String line);
        void onFailed(CmdHelper helper, Exception e);
        void onStart(CmdHelper helper);

        void onEnd(CmdHelper helper);

        void beforeStartCmd(CmdHelper helper, ProcessBuilder pb);
    }
    public static class LogCallback implements Callback{
        final StringBuilder sb = new StringBuilder();
        @Override
        public void collect(CmdHelper helper, String line) {
            sb.append(line);
        }
        @Override
        public void onFailed(CmdHelper helper, Exception e) {
            DefaultPrinter.getDefault().warn("LogCallback", "onFailed", e);
        }
        @Override
        public void onStart(CmdHelper helper) {
            sb.delete(0, sb.length());
        }
        @Override
        public void onEnd(CmdHelper helper) {
            System.out.println(sb.toString());
        }
        @Override
        public void beforeStartCmd(CmdHelper helper, ProcessBuilder pb) {

        }
    }
    public static class InheritIoCallback implements Callback{
        @Override
        public void collect(CmdHelper helper, String line) {

        }
        @Override
        public void onFailed(CmdHelper helper, Exception e) {
            DefaultPrinter.getDefault().warn("InhertIoCallback", "onFailed", e);
        }
        @Override
        public void onStart(CmdHelper helper) {

        }
        @Override
        public void onEnd(CmdHelper helper) {

        }
        @Override
        public void beforeStartCmd(CmdHelper helper, ProcessBuilder pb) {
             pb.inheritIO();
        }
    }
    public static class InheritIoAndRedirectCallback implements Callback{
        private final BufferedWriter writer;

        public InheritIoAndRedirectCallback(BufferedWriter writer) {
            this.writer = writer;
        }
        public InheritIoAndRedirectCallback(String file) {
            File _file = new File(file);
            if(_file.getParentFile() != null && !_file.getParentFile().exists()){
                _file.getParentFile().mkdirs();
            }
            _file.delete();
            try {
                this.writer = new BufferedWriter(new FileWriter(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public void collect(CmdHelper helper, String line) {
            try {
                writer.write(line);
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public void onFailed(CmdHelper helper, Exception e) {
            DefaultPrinter.getDefault().warn("InhertIoCallback", "onFailed", e);
        }
        @Override
        public void onStart(CmdHelper helper) {

        }
        @Override
        public void onEnd(CmdHelper helper) {
            try {
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void beforeStartCmd(CmdHelper helper, ProcessBuilder pb) {
            pb.inheritIO();
        }
    }
}