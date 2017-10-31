1.  field.setAccessible(true);   //使private成员可以被访问,修改
    method.setAccessible(true); //使private方法可以被调用

Filed和Method都这么调用： 在他们的继承链上有个公共的类: java.lang.reflcet.AccessibleObject;
Java反射机制提供的setAccessible()方法可以取消Java的权限控制检查.注意不是改变方法或字段的访问权限;

它们也有自己的对应的方法:
    method.setMethodAccessor(true);
    filed没有提供对应的public方法.

2. getDeclaredFields();
   getDeclaredField(String);
   getFields();
   getField(String);

3. 
