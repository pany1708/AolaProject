1. 接口
2. 代替传统的线程间通信,用await()替换wait()，用signal()替换notify()，用signalAll()替换notifyAll()。
   ——为什么方法名不直接叫wait()/notify()/nofityAll()？因为Object的这几个方法是final的，不可重写！
3. 注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
4. Condition，Condition 将 Object的通信方法（wait、notify 和 notifyAll）分解成截然不同的对象，
   以便通过将这些对象与任意 Lock 实现组合使用.
5. Condition的强大之处在于它可以为多个线程间建立不同的Condition， 使用synchronized/wait()只有一个阻塞队列，notifyAll会唤起所有
   阻塞队列下的线程，而使用lock/condition，可以实现多个阻塞队列，signalAll只会唤起某个阻塞队列下的阻塞线程。
6. 生产者和消费者模型
