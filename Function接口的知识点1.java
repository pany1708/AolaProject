1. 推荐的学习网址： http://www.java2s.com/Tutorials/Java/Java_Lambda/0050__Java_Lambda_Behaviour_Parameter.htm

2. 自己实现的一个函数式接口:
public class functionDemo1 {
	public static void main(String[] argv) {
		engine((x, y) -> x + y);
		engine((x, y) -> x * y);
		engine((x, y) -> x / y);
		engine((x, y) -> x % y);
	}

	private static void engine(Calculator calculator) {
		int x = 2, y = 4;
		int result = calculator.calculate(x, y);
		System.out.println(result);
	}

}

@FunctionalInterface
interface Calculator {
	int calculate(int x, int y);
}
有助于理解函数式接口的用法

3.使用取决于接口的定义


4. Comparator接口
@FunctionalInterface
public interface  Comparator<T> {
    int compare(T o1, T o2);
}


5. JDK8之前已有的函数接口
java.lang.Runnable
java.util.concurrent.Callable
java.util.Comparator


6. 新定义的函数式接口:
java.util.function中定义了几组类型的函数式接口以及针对基本数据类型的子接口。

Predicate -- 传入一个参数，返回一个bool结果， 方法为boolean test(T t)
Consumer -- 传入一个参数，无返回值，纯消费。 方法为void accept(T t)
Function -- 传入一个参数，返回一个结果，方法为R apply(T t)
Supplier -- 无参数传入，返回一个结果，方法为T get()
UnaryOperator -- 一元操作符， 继承Function,传入参数的类型和返回类型相同。
BinaryOperator -- 二元操作符， 传入的两个参数的类型和返回类型相同， 继承BiFunction

7. 声明异常
函数式接口的抽象方法可以声明 可检查异常(checked exception)。 在调用目标对象的这个方法时必须catch这个异常。
public class FunctionalInterfaceWithException {
	public static void main(String[] args) {
		InterfaceWithException target = i -> {};
		try {
			target.apply(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
@FunctionalInterface
interface InterfaceWithException {
	void apply(int i) throws Exception;
}


8. default 修饰的方法是默认方法.

9. 
