1. 可以看作是一个计数器.

2. 一个很好的例子:
    CountDownLatch的一个非常典型的应用场景是：有一个任务想要往下执行，但必须要等到其他的任务执行完毕后才可以继续往下执行。
假如我们这个想要继续往下执行的任务调用一个CountDownLatch对象的await()方法，其他的任务执行完自己的任务后调用同一个
CountDownLatch对象上的countDown()方法，这个调用await()方法的任务将一直阻塞等待，直到这个CountDownLatch对象的计数值减到0为止。

[一个典型的应用场景](http://zapldy.iteye.com/blog/746458)

await(): 是个阻塞的方法.

3. 同步辅助类, 共享锁.

4. 这个时候就可以使用CountDownLatch。CountDownLatch最重要的方法是countDown()和await()，
    前者主要是倒数一次，后者是等待倒数到0，如果没有到达0，就只有阻塞等待了。

5. CountDownLatch是在java5倍引入的,跟它一起被引入的并发工具类还有 CyclicBarrier, Semaphore, ConcurrentHashMap, BlockingQueue.

----------------------------------------------------------------------------------------------------------
1. 辅助类: 比如 CountDownLatch，CyclicBarrier和Semaphore.

   CountDownLatch: 计时器<倒计时器>

2. CyclicBarrier: 回环珊栏<循环,珊栏> 同步屏障

CyclicBarrier好比一扇门，默认情况下关闭状态，堵住了线程执行的道路，直到所有线程都就位，门才打开，让所有线程一起通过。

通过它可以实现让一组线程等待至某个状态之后再全部同时执行。叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用。
我们暂且把这个状态就叫做barrier，当调用await()方法之后，线程就处于barrier了。
1)


3. Semaphore:信号量
Semaphore可以控同时访问的线程个数,通过acquire()获取一个许可,如果没有就等待,而release()释放一个许可.
1) acquire()
2) release()


4. 　下面对上面说的三个辅助类进行一个总结：

　　1) CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：

　　　　CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；

　　　　而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；

　　　　另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。

　　2) Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
