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


5. 饭序列化
  //反序列化,将该对象恢复(存储到字节数组)
  ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
  s = (String)ois2.readObject();
  p = (Person)ois2.readObject();
  System.out.println(s + p);