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
