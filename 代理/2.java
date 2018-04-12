1. java.lang.reflect.Proxy:
   public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h);
      ClassLoader loader ：类加载器，一般用被代理对象的类加载器;
      Class[] interfaces : 被代理对象的接口的Class对象数组,Class<?>[] getInterfaces(); new class[] { tarClass.class }
      InvocationHandler h : 调用处理器;

     参数loader 指定动态代理类的类加载器，参数interfaces 指定动态代理类需要实现的所有接口，参数handler 指定与动态代理类关联的 InvocationHandler 对象.

2. 动态生成代理类，反射获取类.

3. java.lang.reflect.InvocationHandler:
   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

   该方法负责集中处理动态代理类上的所有方法调用:
      第一个参数既是代理类实例;
      第二个参数是被调用的方法对象;
      第三个方法是调用参数。调用处理器根据这三个参数进行预处理或分派到委托类实例上发射执行.

4. 代理:

  1) 动态代理的： 程序运行时运用反射机制动态创建的.
     动态代理类的字节码在程序运行时由java反射机制动态生成的.

5. 使用 Proxy.newProxyInstance()方法创建动态代理。 newProxyInstance()方法有三个参数：
   1) 类加载器（ClassLoader）用来加载动态代理类.
   2) 一个要实现的接口的数组.
   3) 一个 InvocationHandler 把所有方法的调用都转到代理上.

   这个方法创建的动态代理对象包含一个 InvocationHandler 接口的的动态实现, 所有对proxy的调用都被转向到实现了 InvocationHandler 接口的 handler 上.

6.
public interface InvocationHandler{
  Object invoke(Object proxy, Method method, Object[] args)
         throws Throwable;
}
传入 invoke()方法中的 proxy 参数是实现要代理接口的动态代理对象, 通常你是不需要它的.
invoke()方法中的 Method 对象参数代表了被动态代理的接口中要调用的方法，从这个 method 对象中你可以获取到这个方法名字，方法的参数，参数类型等等信息。
Object 数组参数包含了被动态代理的方法需要的方法参数。注意：原生数据类型（如int，long等等）方法参数传入等价的包装对象（如Integer， Long等等）。

   Java 动态代理:
      http://wiki.jikexueyuan.com/project/java-reflection/java-dynamic.html

      http://ifeve.com/java-reflection-11-dynamic-proxies/

7. java反射指南:
   http://ifeve.com/java-reflection-tutorial/
