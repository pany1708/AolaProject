1. BlockingQueue(阻塞队列),一个支持2个附加操作的队列.  【线程容器】,
1)   本质上还是Queue,添加了如下的功能:
    A. 队列为空时获取数据导致当前线程等待.
    B. 队列为空时,导致添加到队列的线程等待.

2)  BlockingQueue提供了4中不同的方式供用户获取队列元素或添加元素到队列，这4种类型的方法导致4种不同的行为：
   1：抛异常；2：返回特殊值，false或null；3：阻塞当前线程；4：超时阻塞 【推荐3和4】
     请求的操作不能得到立即执行的话，每个方法的表现也不同。这些方法如下：
       方法\处理方式	抛出异常	 返回特殊值[true/false]	 一直阻塞	      超时退出,返回一个Boolean值
       插入方法	      add(e)	  offer(e)	               put(e)	      offer(e,time,unit)
       移除方法      	remove()	poll()	                 take()	      poll(time,unit)
       检查方法	     element()	peek()	                 不可用	      不可用

3)  JDK7提供了7个阻塞队列, 分别是:
     ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。 【最常用】
     LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。 【不会满】
     PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。 【基础是 java.util.PriorityQueue】
     DelayQueue：一个使用优先级队列实现的无界阻塞队列。是一个支持延时获取元素的无界阻塞队列 【对元素进行持有直到一个特定的延迟到期】
     SynchronousQueue：一个不存储元素的阻塞队列。 【size为0】
     LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。
     LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。

4)   BlockingQueue的经典用途是 生产者-消费者模式


2. DelayQueue是一个BlockingQueue，其特化的参数是Delayed

3. LinkedTransferQueue: 采用的一种预占模式,

4. 概述: 在新增的Concurrent包里,BlockingQueue很好的解决了多线程中的，如何高效的"传输"数据的问题.
  　BlockingQueue不光实现了一个完整队列所具有的基本功能，同时在多线程环境下，他还自动管理了多线间的自动等待于唤醒功能，
从而使得程序员可以忽略这些细节，关注更高级的功能。

  队列主要是2种,实现方式的区别:
  1) FIFO + LIFO

  多线程环境使用队列来共享数据.

  在多线程领域：所谓阻塞，在某些情况下会挂起线程（即阻塞），一旦条件满足，被挂起的线程又会自动被唤醒).

  PriorityBlockingQueue:   基于优先级的阻塞队列（优先级的判断通过构造函数传入的Compator对象来决定）
但需要注意的是PriorityBlockingQueue并不会阻塞数据生产者，而只会在没有可消费的数据时，阻塞数据的消费者。


  SynchronousQueue：一种没有缓冲的。【公平锁和非公平锁】.
