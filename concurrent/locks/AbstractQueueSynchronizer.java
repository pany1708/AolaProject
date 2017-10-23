1. 队列同步器: AbstractQueueSynchronizer (同步器), 是构建锁或者其它同步组件的基础框架.

2. 分为: 独占式和共享式(ReentrantLock).

3. 内部依赖一个FIFO来完成同步状态的管理.

4. 各种锁和同步器的通用组件是AbstractQueueSynchronizer.

5. 
