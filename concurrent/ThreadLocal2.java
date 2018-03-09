1. ThreadLocal为变量在每个线程中都创建了一个副本, 那么每个线程可以访问自己内部的副本变量.

2. 提供的接口
public T get() { }
public void set(T value) { }
public void remove() { }
protected T initialValue() { };

都是用于处理当前线程中变量的副本.
initialValue一般在使用时进行重写,是一个延迟加载的方法.

3. 
