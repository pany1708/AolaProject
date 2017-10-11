1. eg:

AtomicInteger atomicInt = new AtomicInteger(0);
ExecutorService executor = Executors.newFixedThreadPool(2);
IntStream.range(0, 1000).forEach(i -> executor.submit(atomicInt::incrementAndGet));
executor.shutdown();
System.out.println(atomicInt.get());


2. Executors和executor,ExecutorService之间的联系与区别：
  1) Executors 类提供工厂方法用来创建不同类型的线程池。
  2) ExecutorService 接口继承了 Executor 接口，是 Executor 的子接口
