1. getMethods(): 返回类及其父类(实现接口的方法),所有的public方法.

2. getDeclaredMethods():类内声明的所有的方法,包含了private,public..

3. 2个侧重点不同,2使用的较多．

4. public Method getMethod(String name, Class<?>... parameterTypes);
   这个函数的是返回知道的方法.


5. API:

  1) Object.getClass(): 对应任意一个java对象均可以通过此方法获得对象的类型。
  2) Cless类是Reflect的核心类.

  3) getName(): 类的完整名字
  4) getFields(): 类的public类型的属性
  5) getDeclaredFields()：获得类的所有属性
  6) getMethods()：获得类的public类型的方法
  7) getDeclaredMethods()：获得类的所有方法

  8) getMethod(String name, Class[] parameterTypes)：获得类的特定方法，name参数指定方法的名字，parameterTypes 参数指定方法的参数类型；
  9) getConstructors()：获得类的public类型的构造方法；
  10) getConstructor(Class[] parameterTypes)：获得类的特定构造方法，parameterTypes 参数指定构造方法的参数类型

  11) newInstance(): 通过类的不带参数的构造方法创建这个类的一个对象.

    Constractor.newInstance(): 可以调用指定的构造函数.

  12) getName(): 全名
  13) getSimpleName(): 类名

  14) getModifiers(): 返回int

    Modifier.isAbstract(int modifiers);
    Modifier.isFinal(int modifiers);
    Modifier.isInterface(int modifiers);
    Modifier.isNative(int modifiers);
    Modifier.isPrivate(int modifiers);
    Modifier.isProtected(int modifiers);
    Modifier.isPublic(int modifiers);
    Modifier.isStatic(int modifiers);
    Modifier.isStrict(int modifiers);
    Modifier.isSynchronized(int modifiers);
    Modifier.isTransient(int modifiers);
    Modifier.isVolatile(int modifiers);

   参见 java.lang.reflect.Modifier;

6. 获得Class:
    1) obj.getClass():
    2) class.getSuperClass():
       继承链的顶端是Object,到了最顶端: Class<?> SuperCls = cls.getSuperclass();
       SuperCls == null;
    3)每个class都有一个相应的Class对象,当我们编写一个类,编译完成后生成的.class文件中,就会产生一个Class对象，用于表示这个类的类型信息。
    4)运用.class的方式来获取Class实例，对于基本数据类型的封装类，还可以采用.TYPE来获取相对应的基本数据类型的Class实例
    5) class.forName("完整的类名"); // 包含了路径.

7. java类文件编译之后,会在类中添加一个静态属性,是Class类的实例,用于描述类型信息.

8. Class类来表示运行时类或者接口的信息.

9. 数组共享一个Class对象.

10. 所有的类都有一个静态属性.class;

11. clazz.isAssignableFrom(tmpClass)  ===>>> clazz是否是tmpClass的父类;
    作用是判定clazz表示的类或接口是否同tmpClass指定的类表示的类或接口相同

12. clazz.isPrimitive(): 判定指定的 Class 对象是否表示一个基本类型.
    有九种预定义的 Class 对象，表示八个基本类型和 void。这些类对象由 Java 虚拟机创建，与其表示的基本类型同名，即 boolean、byte、char、short、int、long、float 和 double.

13. 
