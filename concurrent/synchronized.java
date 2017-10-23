1. synchronized 作用:
   1) 直接作用于实例方法: 对当前实例加锁.
   2) 直接作用于静态方法: 对当前类加锁.[当前类的Class对象].
   3) synchronized(obj){}: 锁住的是obj这个对象,它的粒度不是在方法级别,而是特定的代码块.【重点】

2. synchronized 不保证原子性. 同步不等于原子性，但是同步机制可以用来实现原子性。

3. synchronized关键字不是方法签名的一部分,继承时是不会被自动继承的.

4. 直接作用于实例方法: 相当于:
   void synchronized myfunc();
   void myfunc(){
     synchronized (this){}
   }

5. synchronized(同步)的目的是为了防止多线程并发修改共享数据,导致的线程不安全.

6. 锁对象
  synchronized需要一个引用类型对象的实例来加锁.
  对象锁是在对象实例数据上,不是在对象的引用上.

7. synchronized(obj): obj不能是Integer等类型,java的自动装箱和拆箱.

  原因是： Integer的实现原理是: private final int value; // Integer里的唯一属性

    Integer改变时,意味着生成了新对象,锁的就不是同一个对象了.

8. synchronized锁住的是括号里的对象，而不是代码. 【重点】

9. 减小锁的粒度，使代码更大程度的并发.

10. synchronized(Object.class)实现了全局锁的效果.

11. 搞清楚synchronized锁定的是哪个对象，就能帮助我们设计更安全的多线程程序。

12. JUC Lock的出现是为了能在java中灵活的实现各种不同策略的锁.
    要绕过synchronized而自己实现锁机制.
    lock是自带的同步器实现的AbstractQueuedSynchronizer（AQS同步器）。sync是jvm内部实现的.

13. 在lock对象上加锁，一个是在synchronized 指定的对象上加锁.

14. [浅谈java中的锁](http://zhwbqd.github.io/2015/02/13/lock-in-java.html)

15.  synchonrize的最佳实践

  synchronize关键字主要有下面5种用法：
  在方法上进行同步, 分为(1)instance method/(2)static method,
  private final Object mutex = new Object();
在内部块上进行同步, 分为(3)synchronize(this), (4)synchonrize(XXX.class), (5)synchonrize(mutex)

  synchronized(X.class) 使用类对象作为monitor. 同一时间只有一个线程可以能访问块中资源.

  作为修饰符加在方法声明上, synchronized修饰非静态方法时表示锁住了调用该方法的堆对象, 修饰静态方法时表示锁住了这个类在方法区中的类对象.

  synchronized(this)和synchronized(mutex) 都是对象锁, 同一时间每个实例都保证只能有一个实例能访问块中资源.

  synchronized 的对象最好选择引用不会变化的对象（例如被标记为final,或初始化后永远不会变).

16. 同步是两个对象之间的关系，而阻塞是一个对象的状态。
1)异步和同步是针对多个线程或者是进程，一个线程在处理事情的时候，另外一个线程必须
  等待前一个线程处理完。这个就是同步，而异步是不必等待。可以去做其他事情
2) 阻塞非阻塞是针对单个线程在处理请求的等待方式。 阻塞就是等待一直等待调用返回结果。
非阻塞就是调用马上返回。
