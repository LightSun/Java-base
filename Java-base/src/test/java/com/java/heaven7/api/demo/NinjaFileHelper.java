package com.java.heaven7.api.demo;


import com.heaven7.java.base.util.ResourceLoader;

import java.util.List;

public class NinjaFileHelper {

    public interface Convertor{
        String convertFilePath(String in);
    }

    static class NinjaFileModule{
        List<String> defines;
        List<String> includeDirs;
        List<String> files;
        List<NinjaFileModule> dependencyLibs;
    }

    static class NinjaReader{

        public NinjaFileModule read(String file){
            NinjaFileModule module = new NinjaFileModule();
            List<String> list = ResourceLoader.getDefault().loadFileAsStringLines(null, file);
            String defs = list.get(0);
            String includes = list.get(1);
            //TODO
            return module;
        }
    }
    static class NinjaToolchainReader{

    }

}
