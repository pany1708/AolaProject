1. [菜鸟官网](http://www.runoob.com/java/java8-new-features.html)

1) Lambda
2) 方法引用
3) 默认方法
4) 新工具：新的编译工具，如：Nashorn引擎 jjs、 类依赖分析器jdeps
5) Stream API
6) Data Time API
7) Options类：用来解决空指针异常
8) Nashorn, JavaScript 引擎 − Java 8提供了一个新的Nashorn javascript引擎，它允许我们在JVM上运行特定的javascript应用

大多数都出现在 java.util包下

Stream是并发

2. 并行增强：并行数组 Arrays.parallelXXX(): 并行处理,并且可以指定范围
eg:
parallelPrefix将数组中每个元素替换为指定关联操作前缀的积累

假设array [1, 2, 3, 4, ...]，执行完Arrays.parallelPrefix(values, (x, y) -> x * y)之后，array的结果为

[1, 1 × 2, 1 × 2 × 3, 1 × 2 × 3 × 4, ...]


3. http://ifeve.com/java-8-features-tutorial/  [推荐学习]
