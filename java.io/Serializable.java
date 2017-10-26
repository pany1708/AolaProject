1. 序列化:  [理解和总结](http://xiebh.iteye.com/blog/121311)

  保存内存中的对象状态,并且可以把保存的对象状态再读出来.

2. 应用场景:
  1) 把内存的对象状态保存到文件或者数据库.
  2) 使用套接字在网络上传输对象.
  3) 通过RMI传输对象.

3. eg:
  Foo myFoo = new Foo();
  myFoo.setWidth(70);
  myFoo.setHeight(40);

  FileOutputStream fs = new FileOutputStream("foo.ser"); // 保存到文件foo.ser;
  ObjectOutputStream os = new ObjectOutputStream(fs);
  os.writeObject(myFoo);
  os.close();

4. 序列化时：只会对对象的状态进行保存,而不管对象的方法.

5. 反序列化
  //反序列化,将该对象恢复(存储到字节数组)
  ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
  s = (String)ois2.readObject();
  p = (Person)ois2.readObject();
  System.out.println(s + p);

6.  private static final long serialVersionUID = 8673264195747942595L;

    serialVersionUID 序列化时为了保持版本的兼容性,即使版本升级之后之后反序列化仍然保持对象的唯一性;
    1) 默认的1L;
    2) 指纹算法生成的一个64位

    如果serialVersionUID不匹配, InvalidClassException则抛出:  】

    【java序列化的高级认识】(https://www.ibm.com/developerworks/cn/java/j-lo-serial/index.html)

7. java.io的一个接口, 标记接口, 标记可以序列化.

8. 序列化保存的是对象的状态，静态变量属于类的状态，因此 序列化并不保存静态变量。

9. Transient 关键字的作用是控制变量的序列化，在变量声明前加上该关键字，可以阻止该变量被序列化到文件中，在被反序列化后，transient 变量的值被设为初始值，如 int 型的是 0，
   对象型的是 null。

10. 序列化加密:

   在序列化过程中，虚拟机会试图调用对象类里的 writeObject 和 readObject 方法，进行用户自定义的序列化和反序列化，如果没有这样的方法，则默认调用是 ObjectOutputStream的
   defaultWriteObject 方法以及 ObjectInputStream 的 defaultReadObject 方法。用户自定义的 writeObject 和 readObject 方法可以允许用户控制序列化的过程.
