package com.heaven7.java.math.column;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * the list column help we resolve the common type
 *
 * @param <T> the element type
 */
public abstract class BaseListColumn<T> implements IListColumn {

    private static final Map<Type, TypeResolver> sResolverMap = new HashMap<>();
    private static final Map<Type, Object> sContextMap = new HashMap<>();

    private Type mType;

    static{
        sResolverMap.put(String.class, new StringTypeResolver());
        sResolverMap.put(Integer.class, new IntegerTypeResolver());
        sResolverMap.put(Float.class, new FloatTypeResolver());
        sResolverMap.put(Double.class, new DoubleTypeResolver());

        sResolverMap.put(Long.class, new LongTypeResolver());
        sResolverMap.put(Short.class, new ShortTypeResolver());
        sResolverMap.put(Byte.class, new ByteTypeResolver());
        sResolverMap.put(Boolean.class, new BooleanTypeResolver());
        sResolverMap.put(Character.class, new CharacterTypeResolver());
    }

    public static void registerContext(Type type, Object context) {
        sContextMap.put(type, context);
    }

    public static Object getContext(Type type) {
        return sContextMap.get(type);
    }

    public static void registerTypeResolver(Type type, TypeResolver resolver) {
        sResolverMap.put(type, resolver);
    }

    public static TypeResolver getTypeResolver(Type type) {
        return sResolverMap.get(type);
    }

    @Override
    public void setElementType(Type type) {
        this.mType = type;
    }

    public Type getElementType(){
        if (mType == null) {
           /* mType = new TypeToken<T>() {
            }.getType();*/
            mType = getElementType0();
        }
        return mType;
    }

    protected abstract Type getElementType0();

    protected TypeResolver getTypeResolver() {
        Type elementType = getElementType();
        TypeResolver resolver = sResolverMap.get(elementType);
        if (resolver == null) {
            throw new UnsupportedOperationException();
        }
        return resolver;
    }

    protected Object getContext() {
        return sContextMap.get(getElementType());
    }

    @SuppressWarnings("unchecked")
    public T toElement(String str) {
        Type elementType = getElementType();
        TypeResolver resolver = sResolverMap.get(elementType);
        if (resolver == null) {
           // System.err.println(">>> unsupport: type = " + elementType.toString() + " ,try to use string.type");
            throw new UnsupportedOperationException("" + elementType.toString());
           // resolver = sResolverMap.get(String.class);
        }
        return (T) resolver.resolve(sContextMap.get(elementType), str);
    }
    /**
     * the type resolver.
     */
    public static abstract class TypeResolver {
        /**
         * resolve the
         *
         * @param context the context
         * @param str     the string to resolve
         * @return the object element after resolver
         */
        public abstract Object resolve(Object context, String str);

        /**
         * compute the size
         *
         * @param context the context
         * @param ele     the object to compute
         * @return the size
         */
        public long sizeOf(Object context, Object ele) {
            return 0;
        }

        /**
         * called by asIntColumn.
         *
         * @param context the context
         * @param ele     the ele object
         * @return the int value. or null for default value. default null.
         */
        public Integer getInt(Object context, Object ele) {
            return null;
        }

        /**
         * called by asFloatColumn.
         *
         * @param context the context
         * @param ele     the ele object
         * @return the float value. or null for default value. default null.
         */
        public Float getFloat(Object context, Object ele) {
            return null;
        }
    }

    private static class StringTypeResolver extends TypeResolver {
        @Override
        public Object resolve(Object context, String str) {
            return str;
        }
        @Override
        public long sizeOf(Object context, Object ele) {
            return ele != null ? ele.toString().getBytes().length + 50: 0;
        }
        @Override
        public Integer getInt(Object context, Object ele) {
            if(ele != null){
                try{
                    return Integer.parseInt(ele.toString());
                }catch(NumberFormatException e){
                }
            }
            return null;
        }
        @Override
        public Float getFloat(Object context, Object ele) {
            if(ele != null){
                try{
                    return Float.parseFloat(ele.toString());
                }catch(NumberFormatException e){
                }
            }
            return null;
        }
    }
    private static abstract class NumberTypeResolver extends TypeResolver {
        @Override
        public Integer getInt(Object context, Object ele) {
            if(ele != null){
                return ((Number)ele).intValue();
            }
            return null;
        }
        @Override
        public Float getFloat(Object context, Object ele) {
            if(ele != null){
                return ((Number)ele).floatValue();
            }
            return null;
        }
    }
    private static class IntegerTypeResolver extends NumberTypeResolver{
        @Override
        public Object resolve(Object context, String str) {
            try {
                return Integer.parseInt(str);
            }catch(Exception e){
                return null;
            }
        }
        @Override
        public long sizeOf(Object context, Object ele) {
            return ele != null ? 16 : 0;
        }
    }
    private static class FloatTypeResolver extends NumberTypeResolver{
        @Override
        public Object resolve(Object context, String str) {
            try {
                return Float.parseFloat(str);
            }catch(Exception e){
                return null;
            }
        }
        @Override
        public long sizeOf(Object context, Object ele) {
            return ele != null ? 16 : 0;
        }
    }
    private static class DoubleTypeResolver extends NumberTypeResolver{
        @Override
        public Object resolve(Object context, String str) {
            try {
                return Double.parseDouble(str);
            }catch(Exception e){
                return null;
            }
        }
        @Override
        public long sizeOf(Object context, Object ele) {
            return ele != null ? 24 : 0;
        }
    }
    private static class ByteTypeResolver extends NumberTypeResolver{
        @Override
        public Object resolve(Object context, String str) {
            try {
                return Byte.parseByte(str);
            }catch(Exception e){
                return null;
            }
        }
        @Override
        public long sizeOf(Object context, Object ele) {
            return ele != null ? 16 : 0;
        }
    }
    private static class ShortTypeResolver extends NumberTypeResolver{
        @Override
        public Object resolve(Object context, String str) {
            try {
                return Short.parseShort(str);
            }catch(Exception e){
                return null;
            }
        }
        @Override
        public long sizeOf(Object context, Object ele) {
            return ele != null ? 16 : 0;
        }
    }
    private static class BooleanTypeResolver extends NumberTypeResolver{
        @Override
        public Object resolve(Object context, String str) {
            try {
                return Boolean.parseBoolean(str);
            }catch(Exception e){
                return null;
            }
        }
        @Override
        public long sizeOf(Object context, Object ele) {
            return ele != null ? 16 : 0;
        }
    }
    private static class CharacterTypeResolver extends NumberTypeResolver{
        @Override
        public Object resolve(Object context, String str) {
            try {
                return str.charAt(0);
            }catch(Exception e){
                return null;
            }
        }
        @Override
        public long sizeOf(Object context, Object ele) {
            return ele != null ? 16 : 0;
        }
    }
    private static class LongTypeResolver extends NumberTypeResolver{
        @Override
        public Object resolve(Object context, String str) {
            try {
                return Long.parseLong(str);
            }catch(Exception e){
                return null;
            }
        }
        @Override
        public long sizeOf(Object context, Object ele) {
            return ele != null ? 24 : 0;
        }
    }
}
