1. lock接口,提供了比synchronized可获得更广泛的锁定操作,以更优雅的方式处理线程同步问题.

2. sychronized一样的同步效果，需要注意的是，用sychronized修饰的方法或者语句块在代码执行完之后锁自动释放，而用Lock需要我们手动释放
   锁，所以为了保证锁最终被释放(发生异常情况)，要把互斥区放在try内，释放锁放在finally内>

3. 　1）Lock不是Java语言内置的，synchronized是Java语言的关键字，因此是内置特性。Lock是一个类，通过这个类可以实现同步访问
     2) synchronized是在JVM层面上实现的，不但可以通过一些监控工具监控synchronized的锁定，而且在代码执行时出现异常，JVM会自动释
         放锁定，但是使用Lock则不行，lock是通过代码实现的，要保证锁定一定会被释放，就必须将unLock()放到finally{}中
     3)在资源竞争不是很激烈的情况下，Synchronized的性能要优于ReetrantLock，但是在资源竞争很激烈的情况下，
      Synchronized的性能会下降几十倍，


4. 死锁：两个或两个以上的线程在争夺资源的过程中，发生的一种相互等待的现象.
   同步: 效率低, 容易产生死锁．


5. 因此当通过lockInterruptibly()方法获取某个锁时，如果不能获取到，只有进行等待的情况下，是可以响应中断的。
   而用synchronized修饰的话，当一个线程处于等待某个锁的状态，是无法被中断的，只有一直等待下去。


6. locks包的结构:  [网址](http://www.importnew.com/18320.html)

   1) lock接口
   2) ReentrantLock: 可重入锁 -- 是唯一实现lock的类
   3) ReadWriteLock: 接口
   4) ReentrantReadWriteLock: 实现了ReadWriteLock.
      内部类:
          ReentrantReadWriteLock.WriteLock
          ReentrantReadWriteLock.ReadLock
  5) Condition:

   lock必须手动维护锁.


7. 锁的相关概念：

  1) 可重入锁：synchronized和ReentrantLock都是可重入锁.
     锁的分配机制: 基于线程的分配,而非是基于方法的调用.

  2) 可中断锁: 可以响应中断的锁.
    synchronized就不是可中断锁，而Lock是可中断锁.lockInterruptibly()的用法时已经体现了Lock的可中断性。

   3) 公平锁: 已请求线程的顺序来获取锁. fair / unfair
     synchronized就是非公平锁，它无法保证等待的线程获取锁的顺序。
     而对于ReentrantLock和ReentrantReadWriteLock，它默认情况下是非公平锁，但是可以设置为公平锁。

   4) 读写锁:
      读写锁将资源的访问分成了2个锁,一个读锁和一个写锁.
      读锁-->共享锁
      写锁-->排它锁

   5) 独占锁: synchronized   ----- 共享锁

   6) 乐观锁 【CAS: compare and set/compare】----- 悲观锁


8. 其它:

1) synchronized 锁的粒度有点大,不能响应中断.

2) 当通过lockInterruptibly()方法获取某个锁时，如果不能获取到，那么只有进行等待的情况下，才可以响应中断的。
   与 synchronized 相比，当一个线程处于等待某个锁的状态，是无法被中断的，只有一直等待下去。

3) private Lock lock = new ReentrantLock();  // 注意这个地方:lock被声明为成员变量
   将lock设置为局部变量时:每个线程执行到lock.lock()处获取的是不同的锁，所以就不会对临界资源形成同步互斥访问


9. 非阻塞算法:

  一个线程的失败/挂起不应该影响其它线程的失败/挂起的算法.


10. ConcurrentHashMap:
   在jdk6,7,8版本中的实现差别很大,不再采用锁分段技术.

   jdk8中彻底放弃了锁分段技术,核心是CAS操作.

   
