package com.heaven7.java.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * the cmd builder
 * @since 1.2.8
 */
public class CmdBuilder {

    private final List<String> mCmds = new ArrayList<>();

    public CmdBuilder str(String str){
        mCmds.add(str);
        return this;
    }
    public CmdBuilder strs(List<String> strs){
        mCmds.addAll(strs);
        return this;
    }
    public CmdBuilder strs(String str){
        mCmds.addAll(Arrays.asList(str.split(" ")));
        return this;
    }
    public CmdBuilder strFmt(String fmt, Object...objs){
        mCmds.add(String.format(fmt, objs));
        return this;
    }
    public CmdBuilder strsFmt(String fmt, Object...objs){
        try{
            return strs(String.format(fmt, objs));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public CmdBuilder and(){
        mCmds.add("&&");
        return this;
    }
    public CmdBuilder or(){
        mCmds.add("|");
        return this;
    }
    public String[] toCmd(){
        return mCmds.toArray(new String[0]);
    }
}
