package com.heaven7.java.api.test.callsite;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;

import junit.framework.TestCase;

public class MutableCallSiteTest extends TestCase {


	public void test1() throws Throwable {
		MutableCallSite name = new MutableCallSite(MethodType.methodType(String.class));
		MethodHandle MH_name = name.dynamicInvoker();

		MethodType MT_str1 = MethodType.methodType(String.class);
		MethodHandle MH_upcase = MethodHandles.lookup().findVirtual(String.class, "toUpperCase", MT_str1);
		MethodHandle worker1 = MethodHandles.filterReturnValue(MH_name, MH_upcase);

		name.setTarget(MethodHandles.constant(String.class, "Rocky"));
		assertEquals("ROCKY", (String) worker1.invokeExact());

		name.setTarget(MethodHandles.constant(String.class, "Fred"));
		assertEquals("FRED", (String) worker1.invokeExact());
		// (mutation can be continued indefinitely)
	}

	
}
