1. o.getClass().getMethods();  返回类的方法, eclipse.提示的实现.

2. Java 反射机制主要提供了以下功能在运行时判断任意一个对象所属的类。
在运行时构造任意一个类的对象。
在运行时判断任意一个类所具有的成员变量和方法。
在运行时调用任意一个对象的方法
反射的常用类和函数:Java反射机制的实现要借助于4个类：Class，Constructor，Field，Method；

3. Class.forName("类的全称"); 动态加载

编译时加载是静态加载: new创建对象，是静态类;
运行时加载是动态加载

4. 基本数据类型 + void  都有类类型

5. ClassUtil

6. 方法的反射

 1)如何获取一个方法.

 2)方法反射的操作
   method.invoke(对象,参数列表);

7. 成员变量的反射

8. 构造函数的反射

9. Java类加载机制
