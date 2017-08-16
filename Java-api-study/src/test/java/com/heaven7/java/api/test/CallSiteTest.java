package com.heaven7.java.api.test;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.sql.ResultSet;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * start jdk7
 * 
 * @author heaven7
 *
 */
public class CallSiteTest {

	static void test() throws Throwable {
		// THE FOLLOWING LINE IS PSEUDOCODE FOR A JVM INSTRUCTION
		// InvokeDynamic[#bootstrapDynamic].baz("baz arg", 2, 3.14);
	}

	private static void printArgs(Object... args) {
		System.out.println(java.util.Arrays.deepToString(args));
	}

	private static final MethodHandle printArgs;

	static {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		Class thisClass = lookup.lookupClass(); // (who am I?)
		try {
			printArgs = lookup.findStatic(thisClass, "printArgs", MethodType.methodType(void.class, Object[].class));
			System.out.println("find printArgs");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//?
	private static CallSite bootstrapDynamic(MethodHandles.Lookup caller, String name, MethodType type) {
		// ignore caller and name, but match the type:
		System.out.println("bootstrapDynamic");
		return new ConstantCallSite(printArgs.asType(type));
	}

	public static void main(String[] args) {
		invokeWithoutReflect();
	}

	/**
	 * 和反射相比好处是：
	 * 
	 * 调用 invoke() 已经被JVM优化，类似直接调用一样。 性能好得多，类似标准的方法调用。 当我们创建MethodHandle
	 * 对象时，实现方法检测，而不是调用invoke() 时。
	 */
	private static void invokeWithoutReflect() {
		JComboBox combo = new JComboBox();
		MethodHandle handle = null;
		try {
			handle = MethodHandles.lookup().findVirtual(JComboBox.class, "setModel",
					MethodType.methodType(void.class, ComboBoxModel.class));
			handle.invoke(combo, new DefaultComboBoxModel());
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}

}
