1. ExecutorService
2. ThreadPoolExecutor
3. Executors
4. AtomicInteger
5. ConcurrentHashMap
 putIfAbsent():   【absent： 缺少/缺席】
    if (!map.containsKey(key))
     return map.put(key, value);
    else
      return map.get(key);
ConcurrentHashMap的putIfAbsent用来做缓冲相当不错，多线程安全的

6. Future
7. volatile
8. LinkedBlockingQueue
9. AbstractQueue
10. ConcurrentLinkedQueue
