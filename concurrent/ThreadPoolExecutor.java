1. ThreadPoolExecutor是Executors类的底层实现.

2. 推荐的写法:
  Executors的工厂方法Executors.newCachedThreadPool(): 无界线程池,可以进行自动线程回收,
  newFixedThreadPool(): 固定大小线程池.
  newSingleThreadExecutor(): 单个后台线程    后2个使用的场景预定义的设置.

3. 这个是核心的线程池的实现:
   线程的创建和切换的代价都比较大, 实现线程的复用——线程池;
   线程池的好处：
      1)降低资源的消耗: 创建和销毁
      2)提供响应速度: 任务立即执行
      3)提供线程的可管理性: 线程池可以统一管理,分配,调优和监控.

java的线程池支持主要通过ThreadPoolExecutor来实现

4. 
