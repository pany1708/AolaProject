1. AtomicInteger是对int类型的封装，AtomicInteger类中的这两个方法能保证对内存中的int值的操作都是原子性的，换句话说就能保证
   一个线程在对int操作的过程中不会被另一个线程打断，从而使得两个线程不会发生前面文章中出现的指令交叉执行的现象.

2. AtomicBoolean、AtomicInteger、AtomicLong、AtomicReference是对volatile修饰的单一值进行封装。

3. 特别地，AtomicBoolean底层使用int存储，用1表示true，用0表示false，因为在Java中boolean类型的字节长度是不确定的，
   单个的boolean编译时会被映射为int类型，boolean数组编译时才会被映射为byte类型的数组。用1表示true，用0表示false。

  这里没有提供byte、short、float、double、char的包装类，Java官方文档给出的建议是使用已有的AtomicInteger和AtomicLong来自己
实现相应的包装类。比如：
    用AtomicInteger来存储byte数据，进行相应的强制转换即可；
    用AtomicInteger来存储float数据，并使用Float.floatToRawIntBits(float)和Float.intBitsToFloat(int)方法进行转换；
    用AtomicLong来存储float数据，并使用Double.doubleToRawLongBits(double)和Double.longBitsToDouble(long)进行转换。

4. AtomicIntegerArray、AtomicLongArray、AtomicReferenceArray是对数组类型的值进行原子性操作的封装。
   这三个类通过在方法中传入索引作为参数来访问数组中的元素，AtomicIntegerArray使用int[]存储，AtomicLongArray使用long[]存储，
   AtomicReferenceArray使用T []泛型数组存储。

5. AtomicIntegerFieldUpdater、AtomicLongFieldUpdater、AtomicReferenceFieldUpdater是对类对象的某个字段进行原子操作。
   这三个类都是抽象类，但是它们都提供了一个工厂方法newUpdater(Class<U> tclass, String fieldName)来创建实例。

6. AtomicMarkableReference、AtomicStampedReference是对AtomicReference类的扩展。这两个类的区别在于AtomicMarkableReference
   使用boolean与引用类型进行关联，你可以使用这个boolean标识引用数据是否被删除；而AtomicStampedReference使用integer于引用类型
   进行关联，你可以使用这个integer代表引用数据更新的版本数值。

7. 在Java8增加了： DoubleAccumulator、DoubleAdder、LongAccumulator、LongAdder这四个类用于累积计数。

8. 【推荐】 http://blog.csdn.net/Holmofy/article/details/73743279

6.2
  AtomicMarkableReference、AtomicStampedReference 解决ABA问题:

  AtomicStampedReference: 解决自旋CAS【循环CAS】的ABA问题,  有邮戳的, 在AtomicReference类的再一次包装

  AtomicMarkableReference 有标记的

  二者使用的计数器不同,
