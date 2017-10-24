1. 使用lambda表达式代替匿名类.

2. lambda表达式仅能放入如下的代码：
  1) 预定义使用了@Functional注释的函数式接口,自带一个抽象函数的方法.
  2) Single Abstract Method (SAM类型),代表只包含一个抽象方法的接口，也可以包含多个默认方法、类方法，但只能声明一个抽象方法。
  3) Runnable, Comparable, Callable, java.util.function包内的接口.

3. lambda表达式使用方法引用.

4. lambda内部可以使用静态、非静态和局部变量，这称为lambda内的变量捕获。
  只能读而不能写.

5.
