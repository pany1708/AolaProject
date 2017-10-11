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


/**
 * ThreadPoolExecutor类的使用方法
 * 实现高并发：在线程类中的run（）方法内设置Thread.sleep（long delta）； delta取值为：（并发开始时间戳 - 线程开始时间戳）
 * Created by Administrator on 2016/11/19.
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) {

        //设置核心池大小
        int corePoolSize = 5;

        //设置线程池最大能接受多少线程
        int maximumPoolSize = 20;

        //当前线程数大于corePoolSize、小于maximumPoolSize时，超出corePoolSize的线程数的生命周期
        long keepActiveTime = 200;

        //设置时间单位，秒
        TimeUnit timeUnit = TimeUnit.SECONDS;

        //设置线程池缓存队列的排队策略为FIFO，并且指定缓存队列大小为5
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5);

        //创建ThreadPoolExecutor线程池对象，并初始化该对象的各种参数
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit,workQueue);

        //往线程池中循环提交线程
        for (int i = 0; i < 15; i++) {
            //创建线程类对象
            MyTask myTask = new MyTask(i);
            //开启线程
            executor.execute(myTask);
            //获取线程池中线程的相应参数
            System.out.println("线程池中线程数目：" +executor.getPoolSize() + "，队列中等待执行的任务数目："+executor.getQueue().size() + "，已执行完的任务数目："+executor.getCompletedTaskCount());
        }
        //待线程池以及缓存队列中所有的线程任务完成后关闭线程池。
        executor.shutdown();
    }
}
/**
 *线程类
 */
class MyTask implements Runnable {
    private int num;

    public MyTask(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        System.out.println("正在执行task " + num );
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task " + num + "执行完毕");
    }

    /**
     * 获取（未来时间戳-当前时间戳）的差值，
     * 也即是：（每个线程的睡醒时间戳-每个线程的入睡时间戳）
     * 作用：用于实现多线程高并发
     * @return
     * @throws ParseException
     */
    public long getDelta() throws ParseException {
        //获取当前时间戳
        long t1 = new Date().getTime();
        //获取未来某个时间戳（自定义，可写入配置文件）
        String str = "2016-11-11 15:15:15";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long t2 = simpleDateFormat.parse(str).getTime();
        return t2 - t1;
    }
}
