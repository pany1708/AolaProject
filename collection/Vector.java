1. 和ArrayList类似,同步访问;

2. 包含了很多不属于集合框架的传统的方法.

3. 因为 Vector 实现了 RandmoAccess 接口，可以通过下标来进行随机访问。

4. java.util.concurrent.CopyOnWriteArrayList等而不是Vector.

5. Stack：

如果你要使用Stack做类似的业务.那么非线程的你可以选择linkedList,多线程情况你可以选择java.util.concurrent.ConcurrentLinkedDeque
或者java.util.concurrent.ConcurrentLinkedQueue

6. 这个是被遗弃的类,不建议使用.
