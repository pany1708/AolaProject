1. ReentrantLock: 通过自定义的AQS(AbstractQueueSynchronizer)来实现锁的获取和释放.

2. 可重入的独占锁     AQS队列同步器

3. ReentrantLock 锁能够支持一个线程对资源的重复加锁----- 互斥同步

4. 可以实现公平锁, 等待可中断, 锁绑定多个条件

5. 拥有和synchronized相同的并发性和内存语义, 激烈竞争的性能更佳.
   提供了可轮询的锁请求.
   ReentrantLock还提供了Condition，对线程的等待和唤醒等操作更加灵活，一个ReentrantLock可以有多个Condition实例，所以更有扩展性.

6. 一种独占锁的实现

7. ReentrantLock是作为一种可选择的高级功能.
   具有 可重入, 可中断, 可限时, 公平锁等特点.

  1)可重入:
    可以反复的得到相同的一把锁,ReentrantLock有一个与锁相关的获取计数器,再次获得锁,那么获取计数器+1.

  2)与synchronized不同的是，ReentrantLock对中断是有响应的.synchronized一旦尝试获取锁就会一直等待直到获取到锁。

  3)可限时
    超时不能获得锁，就返回false，不会永久等待构成死锁
    使用lock.tryLock(long timeout, TimeUnit unit)来实现可限时锁，参数为时间和单位

  4)公平锁
    一般意义上的锁是不公平的，不一定先来的线程能先得到锁，后来的线程就后得到锁。不公平的锁可能会产生饥饿现象。

    公平锁的意思就是，这个锁能保证线程是先来的先得到锁。虽然公平锁不会产生饥饿现象，但是公平锁的性能会比非公平锁差很多。
    public ReentrantLock(boolean fair)

    public static ReentrantLock fairLock = new ReentrantLock(true);

  5)它拥有与 synchronized 相同的并发性和内存语义.

8. ReentrantLock中有3个内部类，分别是Sync、FairSync和NonfairSync。
   Sync是AQS的抽象类,使用独占锁.
   tryAcquire方法由它的两个FairSync(公平锁)和NonfairSync(非公平锁)实现。
