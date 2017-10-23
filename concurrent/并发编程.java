1. [推荐的学习Blog](http://www.cnblogs.com/xingele0917/p/4317577.html)

2. 学习的关键点:
Executor
ExecutorService
ScheduledExecutorService
Future
CountDownLatch
CyclicBarrier
Semaphore
ThreadFactory
BlockingQueue
DelayQueue
Locks
Phaser

3. 线程安全的实现:

1) 互斥同步： synchronized, ReentrantLock[阻塞同步]  【重量级锁】

2) 非阻塞同步： CAS

3) 无同步方案: ThreadLocal

4. 锁的状态:

无状态锁 ——> 偏向锁 ——> 轻量级锁 ——> 重量级锁
