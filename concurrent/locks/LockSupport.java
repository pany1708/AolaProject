1. 线程阻塞工具类LockSupport

2. 阻塞原语 park() 和 unpark()，用来挂起和恢复线程。没有得到锁的线程将会被挂起。

3. 提供了基本的线程同步原语。LockSupport实际上是调用了Unsafe类里的函数

4. Lock应该是是ReentrantLock之类的显式锁，都是基于AQS实现的，而AQS的底层都是基于LockSupport实现的.
