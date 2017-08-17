package com.heaven7.java.api.test.callsite;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import junit.framework.TestCase;

/**
 * http://blog.csdn.net/zhangrongchao_/article/details/41603887.
 * include: MethdTypeTest.  MethodHandle, MethodHandles.Lookup 
 * 
 * 从待转换的源类型S到目标类型T匹配成功的基本原则如下：
 * 
 * 1）可以通过Java的类型转换来完成，一般是从子类转换成父类，接口的实现类转换成接口，比如从String类转换到Object类。
 * 
 * 2）可以通过基本类型的转换来完成，只能进行类型范围的扩大，比如从int类型转换到long类型。
 * 
 * 3）可以通过基本类型的自动装箱和拆箱机制来完成，比如从int类型到Integer类型。
 * 
 * 4）如果S有返回值类型，而T的返回值是void，S的返回值会被丢弃。
 * 
 * 5）如果S的返回值是void，而T的返回值是引用类型，T的返回值会是null。
 * 
 * 6）如果S的返回值是void，而T的返回值是基本类型，T的返回值会是0。
 * 
 * @author heaven7
 *
 */
public class MethdTypeTest extends TestCase {

	public static void main(String[] args) {
		
		MethdTypeTest  test  = new MethdTypeTest();
		try {
			invokeExact();
			testinvokeWithArguments();
			multipleBindTo();
			testPrimitiveBind();
			lookupSpecial(new TestfindSpecialDemo());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	static class TestfindSpecialDemo{
		private void privateMethod(){
			System.out.println("privateMethod() is called.");
		}
	}

	// 查找类中特殊方法的方法句柄的示例(private or 非public). 必须要有访问权限才可以。否则异常
	public static void lookupSpecial(TestfindSpecialDemo demo) throws NoSuchMethodException, IllegalAccessException, Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findSpecial(TestfindSpecialDemo.class, "privateMethod",
				MethodType.methodType(void.class), TestfindSpecialDemo.class);
		mh.bindTo(demo).invoke();
	}
	
	private void privateMethod(){
		System.out.println("privateMethod() is called.");
	}

	// 查找构造方法、一般方法和静态方法的方法句柄的示例
	public void lookupMethod() throws NoSuchMethodException, IllegalAccessException {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		// 构造方法
		lookup.findConstructor(String.class, MethodType.methodType(void.class, byte[].class));
		// String.substring
		lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class, int.class));
		// String.format
		lookup.findStatic(String.class, "format", MethodType.methodType(String.class, String.class, Object[].class));
	}

	// 基本类型参数的绑定方式
	public static void testPrimitiveBind() throws Throwable {
		MethodHandle mh = MethodHandles.lookup().findVirtual(String.class, "substring",
				MethodType.methodType(String.class, int.class, int.class));
		mh = mh.asType(mh.type().wrap());
		mh = mh.bindTo("Hello World").bindTo(3);
		System.out.println(mh.invoke(5)); // 值为“lo”
	}

	// 多次参数绑定的示例
	public static void multipleBindTo() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(String.class, "indexOf",
				MethodType.methodType(int.class, String.class, int.class));
		mh = mh.bindTo("Hello").bindTo("l");
		System.out.println(mh.invoke(2)); // 值为2
	}

	// 参数绑定的基本用法
	public void bindTo() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));
		int len = (int) mh.invoke("Hello"); // 值为5
		mh = mh.bindTo("Hello World"); // 预先绑定接收者.动态方法
		len = (int) mh.invoke(); // 值为11
	}

	public void asFixedArity() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethdTypeTest.class, "varargsMethod",
				MethodType.methodType(void.class, String.class, int[].class));
		mh = mh.asFixedArity(); // 转换后， 在调用方法句柄的时候，就只能使用数组来进行参数传递
		mh.invoke(this, "Hello", new int[] { 2, 4 });
	}

	public void asSpreader() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethdTypeTest.class, "toBeSpreaded",
				MethodType.methodType(void.class, String.class, int.class, int.class, int.class));
		mh = mh.asSpreader(int[].class, 3);
		mh.invoke(this, "Hello", new int[] { 3, 4, 5 });
	}

	public void asCollector() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethdTypeTest.class, "normalMethod",
				MethodType.methodType(void.class, String.class, int.class, int[].class));
		mh = mh.asCollector(int[].class, 2); // 只有2个会被收集到可变参数数组。
		mh.invoke(this, "Hello", 2, 3, 4); // 3,4
	}

	// used by asFixedArity
	public void varargsMethod(String arg1, int... args) {
	}

	// 被 asVarargsCollector 调用的方法
	public void normalMethod(String arg1, int arg2, int[] arg3) {

	}

	// used for asSpreader
	public void toBeSpreaded(String arg1, int arg2, int arg3, int arg4) {
	}

	public void asVarargsCollector() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethdTypeTest.class, "normalMethod",
				MethodType.methodType(void.class, String.class, int.class, int[].class));
		mh = mh.asVarargsCollector(int[].class); // 转化为可变参数
		mh.invoke(this, "Hello", 2, 3, 4, 5);
	}

	/**
	 * MethodHandle: invokeWithArguments 可以被反射. invoke 和 invokeExact 不可以
	 * 
	 * @throws Throwable
	 */
	public static void testinvokeWithArguments() throws Throwable {
		System.out.println("=============== start testinvokeWithArguments() ==================");
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, int.class, int.class);
		MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
		Object result = mh.invokeWithArguments("Hello World", 1, 3);
		System.out.println(result);
	}

	// 使用invokeExact方法调用方法句柄
	// invokeExact方法在调用的时候要求严格的类型匹配，方法的返回值类型也是在考虑范围之内的
	// 与invokeExact所要求的类型精确匹配不同的是，invoke方法允许更加松散的调用方式
	public static void invokeExact() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, int.class, int.class);
		MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
		// equal to: "Hello World".substring(1, 3)
		String str = (String) mh.invokeExact("Hello World", 1, 3); // 如果是static方法，则直接传参数就可以
		System.out.println(str);

		type = MethodType.methodType(String.class, int.class);
		mh = lookup.findStatic(String.class, "valueOf", type);
		System.out.println((String) mh.invokeExact(5)); // 不加强制转换会报错
	}

	// 一次性修改MethodType中的返回值和所有参数的类型的示例
	// wrap 和 unwrap都是基于基本类型的
	public void wrapAndGeneric() {
		// (int,double)Integer
		MethodType mt = MethodType.methodType(Integer.class, int.class, double.class);
		// (Integer,Double)Integer
		MethodType wrapped = mt.wrap();
		// (int,double)int
		MethodType unwrapped = mt.unwrap();
		// (Object,Object)Object
		MethodType generic = mt.generic();
		// (int,double)Object
		MethodType erased = mt.erase(); // 除开基本类型，其他类型都会被擦除
	}

	// 对MethodType中的返回值和参数类型进行修改的示例
	public void changeMethodType() {
		// (int,int)String
		MethodType mt = MethodType.methodType(String.class, int.class, int.class);
		// (int,int,float)String
		mt = mt.appendParameterTypes(float.class);
		// (int,double,long,int,float)String
		mt = mt.insertParameterTypes(1, double.class, long.class);// 第一个参数是。参数插入的位置
		// (int,double,int,float)String
		mt = mt.dropParameterTypes(2, 3);
		// (int,double,String,float)String
		mt = mt.changeParameterType(2, String.class);
		// (int,double,String,float)void
		mt = mt.changeReturnType(void.class);
	}

	// 使用方法类型在字节代码中的表示形式来创建MethodType
	public void generateMethodTypesFromDescriptor() {
		ClassLoader cl = this.getClass().getClassLoader();
		// 类似 MethodType.methodType(String.class, String.class)。
		String descriptor = "(Ljava/lang/String;)Ljava/lang/String;";
		MethodType mt1 = MethodType.fromMethodDescriptorString(descriptor, cl);
	}

	// 生成通用MethodType
	public void generateGenericMethodTypes() {
		MethodType mt1 = MethodType.genericMethodType(3);
		MethodType mt2 = MethodType.genericMethodType(2, true);
	}

	// MethodType 简单例子
	public void generateMethodTypes() {
		// like: String.length()
		MethodType mt1 = MethodType.methodType(int.class);
		// String.concat(String str)
		MethodType mt2 = MethodType.methodType(String.class, String.class);
		// String.getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
		MethodType mt3 = MethodType.methodType(void.class, int.class, int.class, char[].class, int.class);
		// String.startsWith(String prefix)
		MethodType mt4 = MethodType.methodType(boolean.class, mt2);
	}
}
