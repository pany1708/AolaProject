1. ThreadLocalRandom类继承自java.util.Random.

2. Random是一个线程安全类,这样的线程安全是通过AtomicLong实现的.

3. Java7在所有情形下都更推荐使用java.util.concurrent.ThreadLocalRandom.

4. Math.random()效率低于Random,实质还是调用Random的方法

5. 因为Random用了很多CAS的类，ThreadLocalRandom根本没有用到。

6. 在JDK7中java.util.concurrent包含了一个相当便利的类ThreadLocalRandom,当应用程序期望在多个线程或ForkJoinTasks中使用随机数时.
