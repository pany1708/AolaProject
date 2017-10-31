1. java.lang.reflect.Method;

2.  public Object invoke(Object obj, Object... args); // invoke调用

    使用:用反射得到的Method调用invoke.
    == obj.method(args);

3. Method用来描述类的单个方法<不包含构造方法>.

4. 使用final修饰函数需要注意点:
   1) 不允许子类重写,但是可以调用;
   2) 此方法的调用会转换成行内的形式:直接将方法插入到调用处,final方法体比较庞大是慎用.
