1.ThreadLocal类的使用：
  Java中的ThreadLocal类允许我们创建只能被同一个线程读写的变量.
2. private ThreadLocal myThreadLocal = new ThreadLocal();
  说明:实例了一个ThreadLocal对象,我们只需要实例化对象一次,并且也无需知道它被哪个线程实例化.虽然所有的线程都能够访问到这个
ThreadLocal实例,但是每一个线程却只能访问到自己通过ThreadLocal的set()设置的值.即使是2个不同的线程在同一个ThreadLocal对象
上设置了不同的值,依然无法访问到对方的值.


3. myThreadLocal.set("A thread local value"); //写入值
   String threadLocalValue = (String)myThreadLocal.get(); //读取值
   值的都是Object对象.

4.为ThreadLocal指定泛型类型,这样每次使用get的时候返回值就不用强制转型了.

5. 本文介绍 ScheduledExecutorService 在Java1.5以后才出现的定时任务.
STManager   366


6. ConcurrentHashMap：
   Act170929LegendPanGuChallenge (254)


7. 
