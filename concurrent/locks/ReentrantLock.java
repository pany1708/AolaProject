1. ReentrantLock: 通过自定义的AQS(AbstractQueueSynchronizer)来实现锁的获取和释放.

2. 可重入的独占锁     AQS队列同步器

3. ReentrantLock 锁能够支持一个线程对资源的重复加锁----- 互斥同步

4. 可以实现公平锁, 等待可中断, 锁绑定多个条件

5. 拥有和synchronized相同的并发性和内存语义, 激烈竞争的性能更佳.
   提供了可轮询的锁请求.
   ReentrantLock还提供了Condition，对线程的等待和唤醒等操作更加灵活，一个ReentrantLock可以有多个Condition实例，所以更有扩展性.

6. 一种独占锁的实现
