1. 实现的接口:  Class[] interfaces = clazz.getInterfaces();

2. 修饰符对应的类： java.lang.reflect.Modifier;

3. 包类: java.lang.Package;

4. 构造器类: java.lang.reflect.Constructor;

5. 函数类: java.lang.reflect.Method;

   method.setAccessible(true);

6. 属性类:  java.lang.reflect.Field;

   field.setAccessible(true);

7. 注解类: java.lang.annotation.Annotation

8. 数组:  java.lang.reflect.Array;

9. 修改访问属性: 继承了AccessibleObejct,可以通过反射来调用非共有的属性/方法.
   setAccessible(true);  用于设置访问对象的标志, 反射对象在使用时可以取消java语言的访问检查.

   Method、Field和Constructor类共同继承了AccessibleObject类.

10. 
