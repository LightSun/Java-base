package com.heaven7.java.base.util;

import java.io.Serializable;
import java.util.Properties;

/**
 * the property bundle
 * @author heaven7
 * @since 1.0.3
 */
public class PropertyBundle extends Properties implements Cloneable, Serializable{

	private static final long serialVersionUID = -8811397251450482904L;

	public PropertyBundle() {
	}
	public PropertyBundle(Properties prop) {
		super(prop);
	}
    //short char
	public boolean getBoolean(String key) {
		return Boolean.valueOf(getProperty(key));
	}
	public int getInt(String key) throws NumberFormatException {
		return Integer.parseInt(getProperty(key));
	}
	public long getLong(String key) throws NumberFormatException {
		return Long.parseLong(getProperty(key));
	}
	public byte getByte(String key) throws NumberFormatException {
		return Byte.parseByte(getProperty(key));
	}
	public Double getDouble(String key) throws NumberFormatException {
		return Double.valueOf(getProperty(key));
	}
	public float getFloat(String key) throws NumberFormatException {
		return Float.valueOf(getProperty(key));
	}
	public String getString(String key) {
		return getProperty(key);
	}
	
	public PropertyBundle putBundle(String key, PropertyBundle bundle){
		Object val = put(key, bundle);
		try{
		    return (PropertyBundle)val;
		}catch(ClassCastException e){
			throw new IllegalStateException("wrong mapping for the key = " + key);
		}
	}
	public PropertyBundle getBundle(String key){
		Object val = get(key);
		try{
		    return (PropertyBundle)val;
		}catch(ClassCastException e){
			throw new IllegalStateException("wrong mapping for the key = " + key);
		}
	}
}
