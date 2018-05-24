1. Condition与ReentrantLock的关系就类似于synchronized与Object.wait()/signal();

2.
public static ReentrantLock lock = new ReentrantLock();
public static Condition condition = lock.newCondition();

3. 
