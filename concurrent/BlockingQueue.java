1. BlockingQueue(阻塞队列),一个支持2个附加操作的队列.
1)   本质上还是Queue,添加了如下的功能:
    A. 队列为空时获取数据导致当前线程等待.
    B. 队列为空时,导致添加到队列的线程等待.

2)  BlockingQueue提供了4中不同的方式供用户获取队列元素或添加元素到队列，这4种类型的方法导致4种不同的行为：
     1：抛异常；2：返回特殊值，false或null；3：阻塞当前线程；4：超时阻塞

     方法\处理方式	抛出异常	 返回特殊值	 一直阻塞	  超时退出
     插入方法	      add(e)	  offer(e)	  put(e)	  offer(e,time,unit)
     移除方法      	remove()	poll()	    take()	  poll(time,unit)
     检查方法	     element()	peek()	    不可用	   不可用

3)  JDK7提供了7个阻塞队列, 分别是:
     ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。
     LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。
     PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。
     DelayQueue：一个使用优先级队列实现的无界阻塞队列。  是一个支持延时获取元素的无界阻塞队列
     SynchronousQueue：一个不存储元素的阻塞队列。
     LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。
     LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。

4)   BlockingQueue的经典用途是 生产者-消费者模式


2. DelayQueue是一个BlockingQueue，其特化的参数是Delayed

3. LinkedTransferQueue: 采用的一种预占模式,
