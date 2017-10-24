1. 相关的类都有: java.util.concurrent包下:
   Callable<V>, Future<V>, FutureTask<v>;
   Callable<V>:接口,SAM：V call() throws Exception;
   Future<V>:接口
   FutureTask<V>: RunnableFuture的实现类

2. FutureTask implements RunnableFuture;
   RunnableFuture extends Runnable, Future<V>;  SAM: void run();

3. java.lang包下: 函数接口: Runnable;

4. Callable是带有返回类型的Runnable.

5. Future是对Runnable和Callable任务执行结果取消,查询.
   FutureTask是Future接口的一个唯一实现类.

6. Callable产生结果,Future拿到结果.
   Callable被线程执行之后可以返回值, 这个值可以被Future拿到
   ===》 Future可以拿到异步执行的结果

7. FutureTask最终都是执行Callable类型的任务,会将Runnable任务转换成Callable.[Executors完成]

8. FutureTask实现了两个接口，Runnable和Future，所以它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值.

9. Future模式用来实现异步计算:
   
