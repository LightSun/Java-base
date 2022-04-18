package com.heaven7.java.base.util;

import com.heaven7.java.base.anno.Nullable;

import java.lang.reflect.*;

public final class ReflectUtils {

    /**
     * create a array type for target component class
     * @param clazz the component class
     * @return the array type
     * @since 1.2.1
     */
    public static Class<?> arrayType(Class<?> clazz) {
        return Array.newInstance(clazz, 0).getClass();
    }

    /**
     * get the constructor for target class and types
     * @param clazz the class
     * @param paramTypes the parameter type
     * @param <T> the object type
     * @return the constructor
     * @since 1.2.1
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> constructor(Class<?> clazz, Class<?>...paramTypes){
        try {
            Constructor<?> c = clazz.getConstructor(paramTypes);
            c.setAccessible(true);
            return (Constructor<T>) c;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get the method for target class and name
     * @param clazz the class
     * @param name the method name
     * @param paramTypes the parameter
     * @return the method
     *  @since 1.2.1
     */
    public static Method method(Class<?> clazz,String name,Class<?>...paramTypes){
        try {
            Method m = clazz.getMethod(name, paramTypes);
            m.setAccessible(true);
            return m;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    public static Object callVirtualMethod(Object receiver, String methodName, Object... args){
        Method[] methods = receiver.getClass().getMethods();
        for(Method m : methods){
            if(m.getName().equals(methodName)){
                try {
                    return m.invoke(receiver, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    //ignore
                }
            }
        }
        throw new ReflectException("can't invoke virtual method (" +
                methodName + ") error for object("+ receiver + ")");
    }
    public static Object callStaticMethod(Class<?> clazz, String methodName, Object... args){
        Method[] methods = clazz.getMethods();
        if(Predicates.isEmpty(methods)){
            throw new ReflectException("can't evaluate expression caused by can't find static method (" +
                    methodName + ") for class("+ clazz.getName() + ")");
        }
        for(Method m : methods){
            if(m.getName().equals(methodName)){
                try {
                    return m.invoke(null, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    //ignore
                }
            }
        }
        throw new ReflectException("can't invoke static method (" +
                methodName + ") error for class("+ clazz.getName() + ")");
    }

    public static Object getVirtualFieldValue(@Nullable Object receiver, String fieldName) {
        Field f = field(receiver.getClass(), fieldName);
        if(f == null){
            throw new ReflectException("can't evaluate expression caused by can't find field (" +
                    fieldName + ") for object("+ receiver + ")");
        }
        try {
            f.setAccessible(true);
            return f.get(receiver);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }
    public static Object getStaticFieldValue(Class<?> clazz, String fieldName) {
        Field f = field(clazz, fieldName);
        if(f == null){
            throw new ReflectException("can't evaluate expression caused by can't find field (" +
                    fieldName + ") for class("+ clazz.getName() + ")");
        }
        try {
            f.setAccessible(true);
            return f.get(null);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }
    public static void setVirtualFieldValue(String fieldName, Object val,@Nullable Object receiver) {
        Field f = field(receiver.getClass(), fieldName);
        if(f == null){
            throw new ReflectException("can't evaluate expression caused by can't find field (" +
                    fieldName + ") for object("+ receiver + ")");
        }
        try {
            f.setAccessible(true);
            f.set(receiver, val);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }
    public static void setStaticFieldValue(Class<?> clazz, String fieldName, Object val) {
        Field f = field(clazz, fieldName);
        if(f == null){
            throw new ReflectException("can't evaluate expression caused by can't find field (" +
                    fieldName + ") for class("+ clazz.getName() + ")");
        }
        try {
            f.setAccessible(true);
            f.set(null, val);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * get the field for target class and name
     * @param clazz the class
     * @param fieldName the field name
     * @return the field
     *  @since 1.2.1
     */
    public static Field field(Class<?> clazz, String fieldName) {
        Class<?> target = clazz;
        do{
            try {
                return target.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                target = target.getSuperclass();
                if(target == null || target.getName().startsWith(".java") || target.getName().startsWith(".android")){
                    return null;
                }
            }
        }while (true);
    }

    public static class ReflectException extends RuntimeException{
        public ReflectException() {
        }
        public ReflectException(String message) {
            super(message);
        }
        public ReflectException(String message, Throwable cause) {
            super(message, cause);
        }
        public ReflectException(Throwable cause) {
            super(cause);
        }
    }
}
