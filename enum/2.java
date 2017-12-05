1. 不能有 public  的构造函数;

2. Enum默认实现了java.lang.Comparable接口
   重写了 toString() ==== 对应的是 valueOf()

3. Enum还有一个oridinal的方法，这个方法返回枚举值在枚举类种的顺序，这个顺序根据枚举值声明的顺序而定. 0~

4.
public enum EnumTest {
    MON, TUE, WED, THU, FRI, SAT, SUN;
}

实质是:

new Enum<EnumTest>("MON",0);
