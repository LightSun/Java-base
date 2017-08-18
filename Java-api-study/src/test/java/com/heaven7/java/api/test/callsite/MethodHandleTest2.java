package com.heaven7.java.api.test.callsite;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * http://blog.csdn.net/zhangrongchao_/article/details/41603887
 */
public class MethodHandleTest2 {

	public static void main(String[] args) {

		MethodHandleTest2 test = new MethodHandleTest2();

		try {
			lookupFieldAccessor();
			dropArguments();
			insertArguments();
			filterArguments();
			foldArguments();
			test.catchExceptions();
			filterReturnValue();
			test.invoker();
			invokerTransform();
			test.useMethodHandleProxy();
			testAccessControl();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void useSwitchPoint() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class, int.class, int.class);
		MethodHandle mhMax = lookup.findStatic(Math.class, "max", type);
		MethodHandle mhMin = lookup.findStatic(Math.class, "min", type);
		//like guard: if ...else ...
		SwitchPoint sp = new SwitchPoint();
		MethodHandle mhNew = sp.guardWithTest(mhMin, mhMax);
		mhNew.invoke(3, 4); // 值为3
		SwitchPoint.invalidateAll(new SwitchPoint[] { sp });
		mhNew.invoke(3, 4); // 值为4
	}

	public static void testAccessControl() throws Throwable {
		System.out.println("============== start testAccessControl() ==============");
		new AccessControl().accessControl().invoke();
	}

	public static class AccessControl {
		private void privateMethod() {
			System.out.println("PRIVATE");
		}

		public MethodHandle accessControl() throws Throwable {
			MethodHandles.Lookup lookup = MethodHandles.lookup();
			MethodHandle mh = lookup.findSpecial(AccessControl.class, "privateMethod",
					MethodType.methodType(void.class), AccessControl.class);
			mh = mh.bindTo(this);
			return mh;
		}
	}

	public void doSomething() {
		System.out.println("WORK");
	}

	/**
	 * MethodHandleProxies 相当于把某方法。绑定到runnable实现中
	 * 
	 * @throws Throwable
	 */
	public void useMethodHandleProxy() throws Throwable {
		System.out.println("============== start useMethodHandleProxy() ==============");

		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethodHandleTest2.class, "doSomething", MethodType.methodType(void.class));
		mh = mh.bindTo(this);
		// 相当于把某方法。绑定到runnable实现中
		Runnable runnable = MethodHandleProxies.asInterfaceInstance(Runnable.class, mh);
		Thread t = new Thread(runnable);
		t.start();
		t.join(); // wait t finish
	}

	// invoker和exactInvoker对方法句柄变换的影响
	public static void invokerTransform() throws Throwable {
		System.out.println("============== start invokerTransform() ==============");
		// substring: string(string, int, int)
		MethodType typeInvoker = MethodType.methodType(String.class, String.class, int.class, int.class);
		MethodHandle invoker = MethodHandles.exactInvoker(typeInvoker);

		MethodHandles.Lookup lookup = MethodHandles.lookup();
		// toUpperCase: string()
		MethodHandle mhUpperCase = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class));
		invoker = MethodHandles.filterReturnValue(invoker, mhUpperCase);

		// string(string, int, int)
		MethodType typeFind = MethodType.methodType(String.class, int.class, int.class);
		MethodHandle mh1 = lookup.findVirtual(String.class, "substring", typeFind);
		/**
		 * 先调用substring(...) 再to uppercase.
		 */
		String result = (String) invoker.invoke(mh1, "Hello", 1, 4); // 值为“ELL”
		System.out.println(result);
	}

	/**
	 * 7 , 特殊方法句柄 invoker方法的使用示例
	 * 
	 * @throws Throwable
	 */
	public void invoker() throws Throwable {
		System.out.println("============== start invoker() ==============");
		MethodType typeInvoker = MethodType.methodType(String.class, Object.class, int.class, int.class);
		MethodHandle invoker = MethodHandles.invoker(typeInvoker);

		MethodType typeFind = MethodType.methodType(String.class, int.class, int.class);

		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh1 = lookup.findVirtual(String.class, "substring", typeFind);
		MethodHandle mh2 = lookup.findVirtual(MethodHandleTest2.class, "testMethod", typeFind);
		String result = (String) invoker.invoke(mh1, "Hello", 2, 3);
		System.out.println(result);

		result = (String) invoker.invoke(mh2, this, 2, 3);
		System.out.println(result);
	}

	public String testMethod(int i1, int i2) {
		return i1 + "__" + i2;
	}

	// filterReturnValue方法的使用示例. 对返回值进行修改
	public static void filterReturnValue() throws Throwable {
		System.out.println("============== start filterReturnValue() ==============");
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mhSubstring = lookup.findVirtual(String.class, "substring",
				MethodType.methodType(String.class, int.class));
		MethodHandle mhUpperCase = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class));
		// mhUpperCase先进行预处理
		MethodHandle mh = MethodHandles.filterReturnValue(mhSubstring, mhUpperCase);
		String str = (String) mh.invoke("Hello World", 5); // 输出 WORLD
		System.out.println(str);
	}

	/**
	 * guardWithTest相当于 if(predicate MethodHandle){ true MethodHandle }else{
	 * false MethodHandle }
	 */
	public void guardWithTest() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mhTest = lookup.findStatic(MethodHandleTest2.class, "guardTest",
				MethodType.methodType(boolean.class));
		MethodType type = MethodType.methodType(int.class, int.class, int.class);

		MethodHandle mhTarget = lookup.findStatic(Math.class, "max", type);
		MethodHandle mhFallback = lookup.findStatic(Math.class, "min", type);
		MethodHandle mh = MethodHandles.guardWithTest(mhTest, mhTarget, mhFallback);
		int value = (int) mh.invoke(3, 5); // 值随机为3或5
		System.out.println(value);
	}

	public static boolean guardTest() {
		return Math.random() > 0.5;
	}

	// catchException方法的使用示例
	public int handleException(Exception e, String str) {
		System.out.println("str = " + str);
		System.out.println(e.getMessage());
		return 0;
	}

	// test catch exception
	public void catchExceptions() throws Throwable {
		System.out.println("============== start catchExceptions() ==============");
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType typeTarget = MethodType.methodType(int.class, String.class);
		// Interger.parseInt
		MethodHandle mhParseInt = lookup.findStatic(Integer.class, "parseInt", typeTarget);
		// int(Exception, String)
		MethodType typeHandler = MethodType.methodType(int.class, Exception.class, String.class);

		MethodHandle mhHandler = lookup.findVirtual(MethodHandleTest2.class, "handleException", typeHandler)
				.bindTo(this);
		/**
		 * mhParseInt 要调用的主句柄 NumberFormatException 要捕获的异常类型 mhHandler 表示 调用
		 * mhParseInt出异常后调用的 方法句柄 desc: 捕获后 将异常作为 第一个参数，
		 * ‘hello’作为第2个参数传递到mhHandler ps: 原始方法句柄和异常处理方法句柄的返回值类型必须是相同的，
		 */
		MethodHandle mh = MethodHandles.catchException(mhParseInt, NumberFormatException.class, mhHandler);
		mh.invoke("Hello");
	}

	// permuteArguments方法的使用示例. see also in rephract (github):
	// ArgumentsBinderTest#testPermute
	public void permuteArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class, int.class, int.class);
		MethodHandle mhCompare = lookup.findStatic(Integer.class, "compare", type);
		// 数组： [0] = 3, [1] = 4
		int value = (int) mhCompare.invoke(3, 4); // 值为-1
		// 1,0变换后变成 4，3
		MethodHandle mhNew = MethodHandles.permuteArguments(mhCompare, type, 1, 0);
		value = (int) mhNew.invoke(3, 4); // 值为1
		// 1,1变换后变成 4, 4
		MethodHandle mhDuplicateArgs = MethodHandles.permuteArguments(mhCompare, type, 1, 1);
		value = (int) mhDuplicateArgs.invoke(3, 4); // 值为0
	}

	// foldArguments方法的使用示例
	public static int targetMethod(int arg1, int arg2, int arg3) {
		System.out.println("arg1 = " + arg1 + ", arg2 = " + arg2 + " ,arg3 = " + arg3);// 4,3,4
		return arg1;
	}

	public static void foldArguments() throws Throwable {
		System.out.println("============== start foldArguments() ==============");
		/**
		 * call process: 1,Math.max(3,4) =4 (预处理) 2， add 4 to the head , so now
		 * is 4,3,4 ps: 如果参数预处理的返回值是void，则不会添加新的参数
		 */
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType typeCombiner = MethodType.methodType(int.class, int.class, int.class);
		MethodHandle mhCombiner = lookup.findStatic(Math.class, "max", typeCombiner);

		MethodType typeTarget = MethodType.methodType(int.class, int.class, int.class, int.class);
		MethodHandle mhTarget = lookup.findStatic(MethodHandleTest2.class, "targetMethod", typeTarget);
		MethodHandle mhResult = MethodHandles.foldArguments(mhTarget, mhCombiner);
		int value = (int) mhResult.invoke(3, 4);
		System.out.println(value);
	}

	// filterArguments方法的使用示例
	/**
	 * filterArguments的作用是可以对方法句柄调用时的参数进行预处理，
	 * 再把预处理的结果作为实际调用时的参数。预处理的过程是通过其他的方法句柄来完成的。(只有类型匹配)
	 * 
	 * @throws Throwable
	 */
	public static void filterArguments() throws Throwable {
		System.out.println("============== start filterArguments() ==============");
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class, int.class, int.class);
		MethodHandle mhGetLength = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));

		MethodHandle mhTarget = lookup.findStatic(Math.class, "max", type);
		// filterArguments 使得可以进行预处理(替换参数)
		MethodHandle mhNew = MethodHandles.filterArguments(mhTarget, 0, mhGetLength, mhGetLength);
		// 相当于求 2个string.length后的最大值
		int value = (int) mhNew.invoke("Hello", "New World");
		System.out.println(value); // 9
	}

	// insertArguments方法的使用示例
	public static void insertArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, String.class);
		MethodHandle mhOld = lookup.findVirtual(String.class, "concat", type);

		String value = (String) mhOld.invoke("Hello", "World");
		System.out.println(value);

		MethodHandle mhNew = MethodHandles.insertArguments(mhOld, 1, " --");
		value = (String) mhNew.invoke("Hello"); // 值为“Hello--”
		System.out.println(value);
	}

	/**
	 * dropArguments方法的使用示例 public void dropArguments() throws Throwable {
	 * MethodHandles.Lookup lookup = MethodHandles.lookup(); MethodType type =
	 * MethodType.methodType(String.class, int.class, int.class); MethodHandle
	 * mhOld = lookup.findVirtual(String.class, "substring", type); String value
	 * = (String) mhOld.invoke("Hello", 2, 3); MethodHandle mhNew =
	 * MethodHandles.dropArguments(mhOld, 0, float.class, String.class); value =
	 * (String) mhNew.invoke(0.5f, "Ignore", "Hello", 2, 3); }
	 * 
	 * @throws Throwable
	 */
	public static void dropArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, int.class, int.class);
		MethodHandle mhOld = lookup.findVirtual(String.class, "substring", type);
		String value = (String) mhOld.invoke("Hello", 2, 3);
		System.out.println(value);
		MethodHandle mhNew = MethodHandles.dropArguments(mhOld, 0, float.class, String.class);
		value = (String) mhNew.invoke(0.5f, "Ignore", "Hello", 2, 3); // 这里。。前2个参数会被忽略
		System.out.println(value);
	}

	// MethodHandles类的identity, constant
	/**
	 * MethodHandles类中的identity方法和constant方法的作用类似于在开发中用到的“空对象（Null
	 * object）”模式的应用。 在使用方法句柄的某些场合中，如果没有合适的方法句柄对象，可能不允许直接用null来替换，
	 * 这个时候可以通过这两个方法来生成简单无害的方法句柄对象作为替代。
	 */
	public void identity() throws Throwable {
		MethodHandle mh = MethodHandles.identity(String.class);
		String value = (String) mh.invoke("Hello"); // 值为"Hello"

		// const
		mh = MethodHandles.constant(String.class, "Hello");
		value = (String) mh.invoke(); // 值为"Hello"
	}

	// 获取和设置数组中元素的值的方法句柄的使用示例
	public void arrayHandles() throws Throwable {
		int[] array = new int[] { 1, 2, 3, 4, 5 };
		MethodHandle setter = MethodHandles.arrayElementSetter(int[].class);
		setter.invoke(array, 3, 6);
		MethodHandle getter = MethodHandles.arrayElementGetter(int[].class);
		int value = (int) getter.invoke(array, 3); // 值为6
	}

	// 通过反射API获取方法句柄的示例
	public void unreflect() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		Constructor constructor = String.class.getConstructor(byte[].class);
		lookup.unreflectConstructor(constructor);

		Method method = String.class.getMethod("substring", int.class, int.class);
		lookup.unreflect(method);

		Method privateMethod = Sample.class.getDeclaredMethod("privateMethod");
		lookup.unreflectSpecial(privateMethod, Sample.class);

		Field field = Sample.class.getField("name");
		lookup.unreflectGetter(field);
		lookup.unreflectSetter(field);
	}

	// 查找类中的静态域和一般域对应的获取和设置的方法句柄的示例
	public static void lookupFieldAccessor() throws NoSuchFieldException, IllegalAccessException {
		// 对field 进行操作.
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		lookup.findGetter(Sample.class, "name", String.class);
		lookup.findSetter(Sample.class, "name", String.class);
		lookup.findStaticGetter(Sample.class, "value", int.class);
		lookup.findStaticSetter(Sample.class, "value", int.class);
	}

	public static class Sample {
		public static int value;
		public String name;

		private void privateMethod() {

		}
	}
}
