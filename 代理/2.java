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
