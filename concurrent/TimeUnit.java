1. TimeUnit提供了可读性更好的线程暂停操作, 通常用来替代Thread.sleep();

2. 时间之间的转换.

  Thread.sleep(10 * 60 * 1000);
  TimeUnit.minutes.sleep(10); // 底层还是调用了Thread.sleep()来实现.

3.
