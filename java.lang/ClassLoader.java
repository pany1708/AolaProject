1. 类加载器(ClassLoader)用来加载Java类到JVM, Java 虚拟机使用 Java 类的方式如下：Java 源程序（.java 文件）在经过 Java 编译器编
   译之后就被转换成 Java 字节代码（.class 文件）。类加载器负责读取 Java 字节代码，并转换成 java.lang.Class类的一个实例.

2. java.lang.ClassLoader类的基本职责就是根据一个指定的类的名称，找到或者生成其对应的字节代码，然后从这些字节代码中定义出一个
   Java类,即 java.lang.Class类的一个实例。除此之外，ClassLoader还负责加载 Java 应用所需的资源，如图像文件和配置文件等。

3. java中的类大致分为三种：
  1)系统类;2)扩展类;3)由程序员自定义的类

4. 类装载方式,有两种:
  1.隐式装载,程序在运行过程中当碰到通过new等方式生成对象时,隐式调用类装载器加载对应的类到jvm中.
  2.显式装载,通过class.forname()等方法，显式加载需要的类.

5. Java中的类装载器实质上也是类，功能是把类载入jvm中，值得注意的是jvm的类装载器并不是一个，而是三个，层次结构如下：

  Bootstrap Loader  - 负责加载系统类

        |

      - - ExtClassLoader  - 负责加载扩展类

                |

               - - AppClassLoader  - 负责加载应用类


6. 
