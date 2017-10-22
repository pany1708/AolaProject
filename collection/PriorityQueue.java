1. PriorityQueue类在Java5中引入, 基于优先堆的一个无界队列，这个优先队列中的元素可以默认自然排序或者通过提供的Comparator（比较器）在队列实例化的时排序。

2. 优先队列不允许空值，而且不支持non-comparable（不可比较）的对象.

3. PriorityQueue是非线程安全的，所以Java提供了PriorityBlockingQueue（实现BlockingQueue接口）用于Java多线程环境.

4. 按照Comparator比较器输出.

5. 每次从队列中取出的是具有最高优先权的元素。

6. 不允许使用 null 元素

7. 支持排序的类可以直接使用。
