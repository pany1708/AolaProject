线程和执行器
1.
   Runnable task = () -> {
      String threadName =  Thread.currentThread().getName();
      System.out.println(threadName);
   };

   new thread(task).run();


2.
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
        String threadName = Thread.currentThread().getName();
        System.out.println("Hello " + threadName);
    });

3. preferred way to shutdown executors:

    try {
       System.out.println("attempt to shutdown executor");
       executor.shutdown();
       executor.awaitTermination(5, TimeUnit.SECONDS);
    }
    catch (InterruptedException e) {
       System.err.println("tasks interrupted");
    }
    finally {
       if (!executor.isTerminated()) {
           System.err.println("cancel non-finished tasks");
       }
       executor.shutdownNow();
       System.out.println("shutdown finished");
    }


  shutdown和awaitTermination为接口ExecutorService定义的两个方法，一般情况配合使用来关闭线程池.
  1)shutdown方法：平滑的关闭ExecutorService，当此方法被调用时，ExecutorService停止接收新的任务并且等待已经提交的任务
     （包含提交正在执行和提交未执行）执行完成。当所有提交任务执行完毕，线程池即被关闭。

  2)awaitTermination方法：接收人timeout和TimeUnit两个参数，用于设定超时时间及单位。当等待超过设定时间时，
   会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。一般情况下会和shutdown方法组合使用。

4.
    ExecutorService executor = Executors.newFixedThreadPool(1);
    Future<Integer> future = executor.submit(task);

    System.out.println("future done? " + future.isDone());

    Integer result = future.get();

    System.out.println("future done? " + future.isDone());
    System.out.print("result: " + result);


5. ExecutorService的invokeAll()和InvokeAny()


6. ScheduledExecutorService: 定期多次执行常见任务.
   能够调度任务在经过一定时间后定期运行或一次运行

  1) 延时执行: ScheduledExecutorService executor.schedule(Runnable, delay, TimeUnit);

  2) 为了调度周期性执行的任务:
     A. scheduleAtFixedRate(): 能够以固定的时间速率来执行任务
     B. scheduleWithFixedDelay(): 等待时间段适用于任务的结束和下一个任务的开始之间.
