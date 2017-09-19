1. atom包下多了一批原子处理类,主要用于高并发环境下的高效的处理同步.

2. 我们先来看看AtomicInteger给我们提供了什么接口:

public final int get() //获取当前的值
public final int getAndSet(int newValue)//获取当前的值，并设置新的值
public final int getAndIncrement()//获取当前的值，并自增
public final int getAndDecrement() //获取当前的值，并自减
public final int getAndAdd(int delta) //获取当前的值，并加上预期的值

3. 实现的逻辑: unsafe是java提供的获得对对象内存地址访问的类.
最大的好处就是可以避免多线程的优先级倒置和死锁情况的发生，提升在高并发处理下的性能。

4. http://tutorials.jenkov.com/java-util-concurrent/atomicinteger.html 【推荐

5. 结构划分:
java.util.concurrent.atomic中的类可以分成4组：
标量类（Scalar）：AtomicBoolean，AtomicInteger，AtomicLong，AtomicReference
数组类：AtomicIntegerArray，AtomicLongArray，AtomicReferenceArray
更新器类：AtomicLongFieldUpdater，AtomicIntegerFieldUpdater，AtomicReferenceFieldUpdater
复合变量类：AtomicMarkableReference，AtomicStampedReference

AtomicReference: 无锁的对象引用
AtomicStampedReference：（带有时间戳的对象引用）

原子变量类相当于一种泛化的volatile变量，能够支持原子的和有条件的读-改-写操作。

http://chenzehe.iteye.com/blog/1759884 【***********】

详解析:
1. 第1组:标量类-内部实现不是简单的使用synchronized,而是一中更为高效的方式CAS(compare and swap) + volatile + native方法,从而
  避免了synchronized的高开销,执行效率提升.
  AtomicInteger
  1) set()和get():可以原子地设定和获取atomic的数据,类似于volatile,保证数据会在主存中设置或读取.
  2) void set()和void lazySet()：set设置为给定值，直接修改原始值；lazySet延时设置变量值,所以如果不是想立即读取设置的新值，
     允许在“后台”修改值，那么此方法就很有用。
  3) getAndSet():设置为新值,同时返回旧值.
     本质是先get(),然后set().
  4)compareAndSet( ) 和weakCompareAndSet( )方法
   这2个方法接受2个参数，一个是期望数据(expected)，一个是新数据(new)；如果atomic里面的数据和期望数据一 致，则将新数据设定给
   atomic的数据，返回true，表明成功；否则就不设定，并返回false。指令重排
  5)对于 AtomicInteger、AtomicLong还提供了一些特别的方法。
getAndIncrement( )：以原子方式将当前值加 1，相当于线程安全的i++操作。
incrementAndGet( )：以原子方式将当前值加 1， 相当于线程安全的++i操作。
getAndDecrement( )：以原子方式将当前值减 1， 相当于线程安全的i--操作。
decrementAndGet ( )：以原子方式将当前值减 1，相当于线程安全的--i操作。
addAndGet( )： 以原子方式将给定值与当前值相加， 实际上就是等于线程安全的i =i+delta操作。
getAndAdd( )：以原子方式将给定值与当前值相加， 相当于线程安全的t=i;i+=delta;return t;操作。

附注：
虽然原子的标量类扩展了Number类，但并没有扩展一些基本类型的包装类，如Integer或Long，事实上他们也不能扩展：
基本类型的包装类是不可以修改的，而原子变量类是可以修改的。在原子变量类中没有重新定义hashCode或equals方法，每个实例都是不同的，
他们也不宜用做基于散列容器中的键值。

2. 第2组: 对这些类型的数组提供了支持, 内部并不是像AtomicInteger一样维持一个valatile变量，而是全部由native方法实现

3. 第三组AtomicLongFieldUpdater，AtomicIntegerFieldUpdater，AtomicReferenceFieldUpdater基于反射的实用工具，可以对指定类的
指定 volatile 字段进行原子更新。API非常简单，但是也是有一些约束：
（1）字段必须是volatile类型的
（2）字段的描述类型（修饰符public/protected/default/private）是与调用者与操作对象字段的关系一致。也就是说调用者能够直接操作
    对象字段，那么就可以反射进行原子操作。但是对于父类的字段，子类是不能直接操作的，尽管子类可以访问父类的字段。
（3）只能是实例变量，不能是类变量，也就是说不能加static关键字。
（4）只能是可修改变量，不能使final变量，因为final的语义就是不可修改。实际上final的语义和volatile是有冲突的，
    这两个关键字不能同时存在。
（5）对于AtomicIntegerFieldUpdater 和AtomicLongFieldUpdater 只能修改int/long类型的字段，不能修改其包装类型（Integer/Long）。
    如果要修改包装类型就需要使用AtomicReferenceFieldUpdater.

4. 为了避免CAS过程中的ABA问题，并发包提供了两个类，AtomicStampedReference和AtomicMarkableReference。
前者相当于一个[引用,integer]的二元组，后者相当于一个[引用,boolean]的二元组。
AtomicStampedReference可用来作为带版本号的原子引用，而AtomicMarkableReference可用于表示如：已删除的节点。

在Java中，AtomicStampedReference<E>也实现了这个作用，它通过包装[E,Integer]的元组来对对象标记版本戳stamp，从而避免ABA问题。


5. 累加器
   LongAdder,LongAccumulator && DoubleAdder,DoubleAccumulator:
   大量操作，乐观锁需要太多次尝试，性能下降; LongAdder,LongAccumulator，内部维护多个累加数,
   不同线程更新不同的累加数，只有需要总值的时候才累加所有的累加数
   DoubleAdder,DoubleAccumulator同上
