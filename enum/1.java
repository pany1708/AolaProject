1. 使用enum关键字定义自己的类时,我们可以通过定义的enum调用其方法.
2. 编译器会自动在enum类中插入一些方法,eg:values();
3. enum由java.lang.Enum这个类实现的，在程序中定义的枚举类型，都会隐式继承此
--------------------------------------------------------------------------------------------------------------------------------------------
eg1:
enum Direction {
    LEFT, RIGHT, UP, DOWN
}
这个相当于: 调用 Enum(String name, int ordinal)：
new Enum("LEFT", 0);

Direction[] dirs = Direction.values();
--------------------------------------------------------------------------------------------------------------------------------------------
eg2:
public class Test {
    public enum Color {
        RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private Color(String name, int index) {
            this.name = name;
            this.index = index;
        }

        // 覆盖方法
        @Override
        public String toString() {
            return this.index + "_" + this.name;
        }
    }

    public static void main(String[] args) {
        System.out.println(Color.RED.toString());
    }
}
--------------------------------------------------------------------------------------------------------------------------------------------