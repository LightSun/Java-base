package com.heaven7.java.base.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * smart reference . this class can make the object switch between the weak reference and strong reference.
 * @author heaven7
 *
 * @param <T> the type of reference
 * @see #tryStrongReference()
 * @see #tryWeakReference()
 * @since 1.0.8
 */
public class SmartReference<T> {

	private WeakReference<T> mWeakRef;
	private T mT;
	
	/**
	 * create the smart reference for target object.
	 * @param t the object to reference.
	 */
	public SmartReference(T t){
		Throwables.checkNull(t);
		if( t instanceof Reference){
			throw new UnsupportedOperationException("the object can't be any other Reference(WeakReference, SoftReference and etc.)");
		}
		if(shouldWeakReference(t)){
			mWeakRef = new WeakReference<T>(t);
		}else{
			mT = t;
		}
	}
	
	/**
	 * get the reference object. may be null if it is weak reference and has been recycled.
	 * @return the reference object.
	 * @see #tryWeakReference()
	 */
	public final T get(){
		T t ;
		if(mT == null){
			t = mWeakRef != null ? mWeakRef.get() : null;
		}else{
			t = mT;
		}
		if(t != null && shouldDestroyReference(t)){
			mWeakRef = null;
			mT = null;
			return null;
		}
		return t;
	}

	/**
	 * indicate the reference object is alive or not, that means has been killed by gc).
	 * @return true if the reference object is alive , false otherwise.
	 */
	public final boolean isAlive(){
		return isStrongAlive() || isWeakAlive();
	}
	/**
	 * indicate the reference object is weakly alive or not.
	 * @return true if it is weakly alive. false otherwise.
	 */
	public final boolean isWeakAlive(){
		return mWeakRef !=null && mWeakRef.get() != null;
	}
	/**
	 * indicate the reference object is strong alive or not.
	 * @return true if it is strong alive. false otherwise.
	 */
	public final boolean isStrongAlive(){
		return mT != null;
	}
	
	/**
	 * try strong reference the object. if it is already strong reference or is recycled by gc. return false.
	 * @return true if cast the reference object to strong success.
	 */
	public boolean tryStrongReference(){
		if(mT == null && mWeakRef != null){
			//should not be null.
			T t = mWeakRef.get();
			if(t == null){
				return false;
			}
			this.mT = t; 
			mWeakRef = null;
			return true;
		}
		return false;
	}
	/**
	 * try weak reference the object. if it is already weak reference or is recycled by gc. return false.
	 * @return true if try weak reference the object success. false otherwise.
	 */
	public boolean tryWeakReference(){
		if(mT != null){
			mWeakRef = new WeakReference<T>(mT);
			mT = null;
			return true;
		}
		return false;
	}

	/**
	 * indicate the object should be weak reference or not.
	 * this method will called only once in constructor. 
	 * @param t the object to reference.
	 * @return true if should weak reference the object.
	 * {@link #SmartReference(Object)}
	 */
	protected boolean shouldWeakReference(T t) {
		return false;
	}
	/**
	 * indicate should destroy the reference of object or not. this method is called by {@linkplain #get()}.
	 * @param t the object
	 * @return true if should destroy it. false otherwise.
	 * @see #get()
	 * @since 1.0.9
	 */
	protected boolean shouldDestroyReference(T t) {
		return false;
	}
	
}
