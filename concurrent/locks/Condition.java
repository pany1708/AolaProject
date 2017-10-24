1. 接口
2. 代替传统的线程间通信,用await()替换wait()，用signal()替换notify()，用signalAll()替换notifyAll()。
   ——为什么方法名不直接叫wait()/notify()/nofityAll()？因为Object的这几个方法是final的，不可重写！
3. 注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
4. Condition，Condition 将 Object的通信方法（wait、notify 和 notifyAll）分解成截然不同的对象，
   以便通过将这些对象与任意 Lock 实现组合使用.
5. Condition的强大之处在于它可以为多个线程间建立不同的Condition， 使用synchronized/wait()只有一个阻塞队列，notifyAll会唤起所有
   阻塞队列下的线程，而使用lock/condition，可以实现多个阻塞队列，signalAll只会唤起某个阻塞队列下的阻塞线程。
6. 生产者和消费者模型


7. Lock lock = new ReentrantLock();
   Condition condition = lock.newCondition();

8. 每一个Condition对象都包含着一个等待队列是一个FIFO的队列，队列的每一个节点都包含了一个线程引用，
   该线程就是在Condition对象上等待的线程，如果一个线程调用了await()方法，该线程就会释放锁、构造成节点进入等待队列并进入等待状态。

9. 条件变量Condition: 条件队列

10. 每一个Lock可以有任意数据的Condition对象，Condition是与Lock绑定的，所以就有Lock的公平性特性：如果是公平锁，线程为按照FIFO的顺
   序从Condition.await中释放，如果是非公平锁，那么后续的锁竞争就不保证FIFO顺序了.

11. 线程协作： 实现等待/通知模式.

12. 每个Condition对象都包含一个等待队列.FIFO
