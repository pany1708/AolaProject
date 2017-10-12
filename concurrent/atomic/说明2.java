1. java.util.concurrent.atomic:
   CAS(比较和交换),是现代CPU直接支持的原子指令,币同步锁更快.

2. LongAdder, LongAccumulator

   LongAdder要比AtomicLong拥有更高的吞吐量，但会耗费更多的内存空间。

    LongAccumulator是LongAdder的功能增强版。LongAdder的API只有对数值的加减，而LongAccumulator提供了自定义的函数操作。


3.
    1) AtomicInteger/AtomicLong/AtomicBoolean/AtomicReference是关于对变量的原子更新，

    2) AtomicIntegerArray/AtomicLongArray/AtomicReferenceArray是关于对数组的原子更新

    3) AtomicIntegerFieldUpdater<T>/AtomicLongFieldUpdater<T>/AtomicReferenceFieldUpdater<T,V>是基于反射的原子更新字段的值

4. 
