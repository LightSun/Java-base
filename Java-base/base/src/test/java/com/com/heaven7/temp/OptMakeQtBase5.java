package com.com.heaven7.temp;

import com.heaven7.java.base.util.CmdBuilder;
import com.heaven7.java.base.util.CmdHelper;
import com.heaven7.java.base.util.ResourceLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OptMakeQtBase5 {

    public void start(){
       // String[] cmds = new CmdBuilder().strs("make -j8").toCmd();
        String[] cmds = new CmdBuilder().strs("make").toCmd();
        String file = "qtbase_build.txt";
        boolean ret = new CmdHelper(cmds).execute(new CmdHelper.InheritIoCallback() {
            @Override
            public void beforeStartCmd(CmdHelper helper, ProcessBuilder pb) {
                super.beforeStartCmd(helper, pb);
                pb.directory(new File("C:\\Users\\Administrator\\Downloads\\qtbase-everywhere-src-5.15.2"));
            }
        }, file);
        if(!ret){
            if(!new File(file).exists()){
                System.out.println("cmd failed. and no file output.");
                return;
            }
            List<String> lines = ResourceLoader.getDefault().loadFileAsStringLines(null, file);
            if(lines.isEmpty()){
                System.out.println("cmd failed. and no output.");
                return;
            }
            String moc_path = "C:\\Users\\Administrator\\Downloads\\qtbase-everywhere-src-5.15.2\\bin\\moc.exe";
            String moc_path2 = "'C:\\Users\\Administrator\\Downloads\\qtbase-everywhere-src-5.15.2\\bin\\moc.exe'";
            List<String> needLines = new ArrayList<>();
            for (String str : lines) {
                if(str.startsWith(moc_path) || str.startsWith(moc_path2)){
                    needLines.add(str);
                }
            }
            System.out.println("---- compile error ---->");
            for (int i = 0; i < needLines.size(); i++) {
                System.out.println(needLines.get(i));
            }
        }
    }

    public static void main(String[] args) {
        new OptMakeQtBase5().start();
    }
}
