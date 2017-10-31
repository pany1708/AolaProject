1. 代理的是接口,不是类,不会抽象类.

2. 实现的设计模式: 适配器(Adapter) 或者 修饰器(Decorator).

3. 静态代理和动态代理：

 静态代理:
       实现自己的Proxy类实现接口. <更像是面向接口编程>
 动态代理:
    java.lang.reflect.Proxy
    java.lang.reflect.InvocationHandler;
    java.lang.reflect.Method;
    实现Proxy类实现接口InvocationHandler,重写invoke().
