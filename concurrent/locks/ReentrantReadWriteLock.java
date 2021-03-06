1. ReentrantLock是排它锁, 同一个时刻只允许一个线程进行访问.

   ReentrantReadWriteLock：共享锁,多个读线程和一个写线程

2. 读写锁：分为读锁和写锁，多个读锁不互斥，读锁与写锁互斥，这是由jvm自己控制的，你只要上好相应的锁即可。如果你的代码只读数据，可以
很多人同时读，但不能同时写，那就上读锁；如果你的代码修改数据，只能有一个人在写，且不能同时读取，那就上写锁。总之，读的时候上读锁，
写的时候上写锁！

3. ReadLock可以被多个线程持有并且在作用时排斥任何的WriteLock，而WriteLock则是完全的互斥。

4. LogParticipationHandler{读写锁的应用+hashMap}

5. volatile只保证可见性,在1写N读的情况下足够了.
   N写N读,还要保证数据一致性而又减少并行度的损失,ReentrantReadWriteLock.
