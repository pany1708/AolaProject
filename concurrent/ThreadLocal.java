1. ThreadLocal类的使用：
  Java中的ThreadLocal类允许我们创建只能被同一个线程读写的变量.
2. private ThreadLocal myThreadLocal = new ThreadLocal();
  说明:实例了一个ThreadLocal对象,我们只需要实例化对象一次,并且也无需知道它被哪个线程实例化.虽然所有的线程都能够访问到这个
ThreadLocal实例,但是每一个线程却只能访问到自己通过ThreadLocal的set()设置的值.即使是2个不同的线程在同一个ThreadLocal对象
上设置了不同的值,依然无法访问到对方的值.


3. myThreadLocal.set("A thread local value"); //写入值
   String threadLocalValue = (String)myThreadLocal.get(); //读取值
   值的都是Object对象.

4. 为ThreadLocal指定泛型类型,这样每次使用get的时候返回值就不用强制转型了.

5. 线程本地变量,将变量和线程绑定在一起。

6. 而ThreadLocal从本质上讲，无非是提供了一个“线程级”的变量作用域，它是一种线程封闭（每个线程独享变量）技术，更直白点讲，
ThreadLocal可以理解为将对象的作用范围限制在一个线程上下文中，使得变量的作用域为“线程级”。

7. ThreadLocal创建的变量只能被当前的线程访问,区别与一般变量可以被所有线程访问.

8. ThreadLocal设置默认的get初始值,需要重写initialValue(),
ThreadLocal<String> mThreadLocal = new ThreadLocal<String>() {
    @Override
    protected String initialValue() {
      return Thread.currentThread().getName();
    }
};

9. 栈内存归属于单个线程，每个线程都会有一个栈内存，其存储的变量只能在其所属线程中可见，即栈内存可以理解成线程的私有内存。
   而堆内存中的对象对所有线程可见。堆内存中的对象可以被所有线程访问。

10. 使用场景：
    实现单个线程单例以及单个线程上下文信息存储，比如交易id等
    实现线程安全，非线程安全的对象使用ThreadLocal之后就会变得线程安全，因为每个线程都会有一个对应的实例
    承载一些线程相关的数据，避免在方法中来回传递参数


11. 理解栈和堆的本质区别:
  最主要的区别就是栈内存用来存储局部变量和方法调用。
  而堆内存用来存储Java中的对象。无论是成员变量，局部变量，还是类变量，它们指向的对象都存储在堆内存中


12. ThreadLocal支持泛型,一个ThreadLocal只能放一个对象.
  每个线程都对应一个本地变量的Map，所以一个线程可以存在多个线程本地变量ThreadLocal.

13. ThreadLocal使用灵一种思路来解决线程安全的问题. 一个是锁机制进行时间换空间，一个是存储拷贝进行空间换时间。

14. ThreadLocal 最佳实践:
综合上面的分析，我们可以理解ThreadLocal内存泄漏的前因后果，那么怎么避免内存泄漏呢？
  每次使用完ThreadLocal，都调用它的remove()方法，清除数据。
  在使用线程池的情况下，没有及时清理ThreadLocal，不仅是内存泄漏的问题，更严重的是可能导致业务逻辑出现问题。所以，
  使用ThreadLocal就跟加锁完要解锁一样，用完就清理。


15. ThreadLocal会引起内存泄漏的本质: ThreadLocal的声明周期和Thread一样长.

16. 分配使用了ThreadLocal又不再调用get(),set(),remove()方法，那么就会导致内存泄漏。

17. 引发的内存泄漏
  https://my.oschina.net/hosee/blog/729726
