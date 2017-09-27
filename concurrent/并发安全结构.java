队列
    ConcurrentLinkedQueue, 非阻塞FIFO队列
    BlockingQueue, FIFO
        LinkedBlockingQueue, 无限空间
        ArrayBlockingQueue， 有限空间
        SynchronousQueue， 插入操作和移除操作必须对应
        PriorityBlockingQueue, 有优先级的fifo阻塞队列
        DelayedQueue, 延迟期满（Delayed对象）才能取出
    BlockingDeque, 满足LIFO操作的队列
        LinkedBlockingDeque



并发 Collection，非同步Collection,
    ConcurrentHashMap
    ConcurrentSkipListMap
    ConcurrentSkipListSet
    CopyOnWriteArrayList
    CopyOnWriteArraySet
