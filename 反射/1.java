1. java中有2中对象,实例对象和class对象.

2. class对象是没办法用new得到的,它是jvm生成用来保存对应类信息的.[当一个类被编译成.class字节码,编译器同时为我们创建了一个class对象并将它保存在.class文件中].

3. 编译文件的同时会产生一个对应的Class对象.

4. jvm的类加载机制: 在需要的时候将.class文件和对应的Class对象加载到内存中.

5. Class对象是jvm用来保存对象实例对象的相关信息的,所有的Class对象都是Class类的实例.

6. Class对象的获取:

1)
   Dog dog = new Dog();
   Class clazz = dog.getClass();

2)
  Class clazz = Class.forName("Dog");

3) 类字面常量
  Class clazz = Dog.class;

7. 引出了反射: [Class对象的使用和反射]
      在运行状态下, 对于任意一个类, 都能够知道这个类的所有属性和方法;对于任意一个对象,都能够调用它的任意一个方法和属性；这种动态获取的信息以及动态调用对象的方法的
功能称为java语言的反射机制。

8. jvm中类的加载时机:
  1.遇到new、getstatic、putstatic或invokestatic这4条字节码指令时，如果类没有进行过初始化，则需要先触发其初始化。生成这4条指令最常见的Java代码场景是：
          使用new关键字实例化对象时、读取或者设置一个类的静态字段（被final修饰、已在编译器把结果放入常量池的静态字段除外）时、以及调用一个类的静态方法的时候。
  2.使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没有进行过初始化，则需要先触发其初始化。
  3.当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要触发父类的初始化。
  4.当虚拟机启动时，用户需要指定一个执行的主类（包含main()方法的类），虚拟机会先初始化这个类。

  对于这四种触发类进行初始化的场景，在java虚拟机规范中限定了“有且只有”这四种场景会触发。这四种场景的行为称为对类的主动引用，
除此以外的所有引用类的方式都不会触发类的初始化，称为被动引用。


  说明:
    当初始化一个对象数组的时候，也不会触发类的初始化。

9. Class对象是存放在堆区的，不是方法区。类的元数据（元数据并不是类的Class对象。Class对象是加载的最终产品，类的方法代码，变量名，方法名，
   访问权限，返回值等等都是在方法区的）才是存在方法区的。

10. Class类没有公共的构造方法，Class对象是在类加载的时候由Java虚拟机以及通过调用类加载器中的 defineClass 方法自动构造的，因此不能显式地声明一个Class对象。

11. 加载 --->>>  链接 --->>> 初始化

12. 所有的包装类都有一个字段TYPE,指向对应的基本类型的class对象.
    Integer.TYPE == int.class;  用.class来创建对Class对象的引用时，不会自动地初始化该Class对象;

13. 编译时常量: static final [这类在编译的时候会被放入到常量池]

14. 一旦类被加载到了内存中,获得的class对象都是指向了同一个java堆上的Class对象的引用.

15. 对应任意一个Class对象: 它的唯一性由这个类本身和它的类加载器.

16. ClassLoader: ClassLoader就是用来动态加载class文件到内存;
    1) BootStrap ClassLoader：称为启动类加载器，是Java类加载层次中最顶层的类加载器，负责加载JDK中的核心类库; [包含在jvm内核中] [引导类加载器]
    2) Extension ClassLoader：称为扩展类加载器，负责加载Java的扩展类库,默认加载JAVA_HOME/jre/lib/ext/目下的所有jar;
    3) App ClassLoader：称为系统类加载器，负责加载应用程序classpath目录下的所有jar和class文件.
     负责加载用户类路径（classpath）上的指定类库，我们可以直接使用这个类加载器.
     一般情况，如果我们没有自定义类加载器默认就是用这个加载器.

17. ClassLoader加载类的原理:  ClassLoader使用的是双亲委托模型来搜索类的.

18. jvm提供的默认的类加载器,只能加载指定目录下jar和class,自定义类加载器.
