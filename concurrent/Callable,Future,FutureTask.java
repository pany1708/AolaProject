1. [Java并发编程：Callable、Future和FutureTask](http://www.cnblogs.com/dolphin0520/p/3949310.html)

2. 那么怎么使用Callable呢？一般情况下是配合ExecutorService来使用的,
   ExecutorService有若干个submit();

3. Executor就是Runnable和Callable的调度容器，Future就是对于具体的Runnable或者Callable任务的执行结果进行
取消、查询是否完成、获取结果、设置结果操作。get方法会阻塞，直到任务返回结果(Future简介).

4. Future接口的唯一的实现FutureTask.

5. 得到Future的2种手段：

  1)  ExecutorService executor = Executors.newCachedThreadPool();
      Future<Integer> result = executor.submit(new MyRunnable());

  2)  ExecutorService executor = Executors.newCachedThreadPool();
      FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>());

6. Future cancel():


7. 
