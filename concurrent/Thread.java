1. Thread.sleep(100); // 使用TimeUnit来代替

2. sleep: 只是暂时让出CPU的执行权,而不释放锁.而wait则需要释放锁.

3. wait(): 在唤醒之后,则是需要竞争锁的.

4. Thread.yield(): 暂停当前线程,线程让步,不能指定暂停的时间,而且也不能保证当前的线程马上停止. 同优先级的线程之间能适当的轮转执行.

5. join(): 异步变为同步：父线程等待子线程执行完之后在执行.实质是join(0);
   join的实质还是内部调用了wait()来阻塞当前线程,却没有对应的notify()方法,原因是Thread的start方法中做了相应的处理，所以当join的线
   程执行完成以后，会自动唤醒主线程继续往下执行。

6. 同步的理解：
   通过人为的控制和调度,保证共享资源的多线程访问成为线程安全,来保证结果的准确.

7. interrupte():
   线程会不时的检测中断表示位,以判断线程是否应该被中断,中断只会影响到wait,sleep,join状态,被打断的线程抛出InterruptedException,
同时会清除线程的中断状态
   获取锁的过程中不能被中断的.

   中断是个状态,interrupt()方法只是将这个状态置为true而已.
   所以说正常运行的程序不去检测状态，就不会终止，而wait等阻塞方法会去检查并抛出异常。如果在正常运行的程序中添加
while(!Thread.interrupted()) ，则同样可以在中断后离开代码体.
   Thread.interrupted()检查当前线程是否发生中断，返回boolean.

关于Thread的3个interrupt, interrupted, isInterrupted
（1）interrupt：置线程的中断状态
（2）isInterrupted：线程是否中断
（3）interrupted：返回线程的上次的中断状态，并清除中断状态  static.


8. 线程基础关系:

sleep(): 方法是Thread类里面的，主要的意义就是让当前线程停止执行，让出cpu给其他的线程，但是不会释放对象锁资源以及监控的状态，当指定的时间到了之后又会自动恢复运行状态.

wait(): 方法是Object类里面的，主要的意义就是让线程放弃当前的对象的锁，进入等待此对象的等待锁定池，
      只有针对此对象调动notify方法后本线程才能够进入对象锁定池准备获取对象锁进入运行状态.

9. Java为我们提供了一个机制，用来捕获并处理在一个线程对象中抛出的未检测异常，以避免程序终止。我们可以通过UncaughtExceptionHandler来实现这种机制。

thread.setUncaughtExceptionHandler((t, e) -> System.out.println(t.getName() + "出现异常，异常信息：" + e));
