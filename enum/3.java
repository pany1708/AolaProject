1. 实例有限且固定的类===>>>枚举类. [一个类的对象时有限且固定]

2. 枚举类的所有实例都是使用 public static final 修饰的类变量来保存.

3. 如果有必要,可以提供一些静态方法,其它类可以根据特定参数来获取与之匹配的实例.

4. 使用enum的类默认继承了 java.lang.Enum类, 其中java.lang.Enum类实现了java.lang.Serializable和java.lang.Comparable两个接口.

5. 枚举类的所有实例必须在枚举类的第一行显式列出，否则这个枚举类永远都不能产生实例。

6. 如果通过字符串匹配枚举类实例时, 使用valueOf()建议使用try-catch捕获异常.

   使用前必须校验,

7. 可以通过扩展枚举类

//是否包含枚举项
public static boolean contains(String name){
  //所有的枚举值
  Season[] season = values();
 //遍历查找
 for(Season s : season){
     if(s.name().equals(name)){
         return true;
     }
 }
 return false;
}

8. 使用枚举类的优点:

1) 大大加强了程序的可读性、易用性和可维护性;
2) 相对于普通的常量会占用更多的内存;
   这些常量还有关联属性或者行为等，那么强烈推荐使用枚举类型,使用枚举类型的性能几乎是使用静态类的16倍.
3) 推荐使用枚举单例来实现单例模式，可以使用枚举策略来简化策略模式.

9. 枚举类实例对象之间的比较: 可以通过==来,Enum重写了equals()方法.

10. 枚举类实现单例模式:

1) 单元素枚举实现单例模式成为最佳的方法. 【枚举单例模式】

class Resource{
}

public enum SomeThing {
    INSTANCE;
    private Resource instance;
    SomeThing() {
        instance = new Resource();
    }
    public Resource getInstance() {
        return instance;
    }
}
上面的类Resource是我们要应用单例模式的资源,具体可以表现为网络连接,数据库连接,线程池等.
