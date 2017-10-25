1. stream采用内部迭代.
2. findAny() 和 findFirst() 返回一个Optional<T>类型.
3. 将普通流转换成数值流:
    IntStream, DoubleStream, LongStream
    方法:
        mapToInt(), mapToDouble(), mapToLong();
4. 抛弃for循环,一切交给stream去做.
5. Stream是lazy操作,只有终止操作被调用时,中间操作才会被真正的触发.
   filter不会对整个流进行过滤,发现一个就传递给下一个中间操作.

6. 静态工厂方法:
    of(T...values): 长度是values的长度.
    generator(Supplier): 返回一个无限长度的stream.
    iterate(Seed, UnaryOperator): 返回一个无限长度的stream.
7. concat: Stream.concat(Stream.of(4, 5), Stream.of(1, 2, 3));

8. flatMap/flatMapToInt/flatMapToLong/flatMapToDouble:          【重点研究】
    flatMap: 将原先stream的每个元素转换成Stream,然后连接起来.

9. peek(Consumer): 调试
10. max/min(Comparator): 返回一个Optional<T>

11. [深度系列](http://blog.csdn.net/IO_Field/article/details/54971761)
12. [万能的Collecotr](http://blog.csdn.net/IO_Field/article/details/54971608)
13. [万能的reduce](http://blog.csdn.net/IO_Field/article/details/54971679)
14. reduce: 可以实现从Stream中生成一个值,生成的值是根据指定的计算模型计算出来的. 【聚合操作】

    1) 一个int[][]合并成一维数组:
        int[] IDS_OLD_ARR = Arrays.stream(IDS_OLD).reduce(new int[0], ArrayUtils::addAll);
    2)int flag = list.strem().reduce(flag, BitUtil::set).intValue();
    3)private boolean validateGainClothes(int userId, Integer[] clothesIndex) {
	       return !Stream.of(clothesIndex).map(a->noMiss(userId, a)).reduce((a,b)->a&&b).get();
      }


   理解reduce是怎么聚合的?

   Stream.reduce，常用的方法有average, sum, min, max, and count，返回单个的结果值，并且reduce操作每处理一个元素总是创建一个新值

   T reduce(T identity, BinaryOperator<T> accumulator):
identity: 新值
accumulator: 累加器
在该reduce方法中第一个参数t为上次函数计算的返回值，第二个参数u为Stream中的元素，这个函数把这两个值计算apply，得到的和会被赋值给下次执行这个方法的第一个参数.

15. sorted(Comparator):

Comparator<Developer> byName = new Comparator<Developer>() {
  @Override
  public int compare(Developer o1, Developer o2) {
      return o1.getName().compareTo(o2.getName());
  }
};

Comparator<Developer> byName =  (o1, o2)->o1.getName().compareTo(o2.getName());

16.
1) partitioningBy:
Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).limit(100).collect(Collectors.partitioningBy(p -> p.getAge() < 18));
System.out.println("Children number: " + children.get(true).size());
System.out.println("Adult number: " + children.get(false).size());

2) groupingBy:
Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier()).limit(100).collect(Collectors.groupingBy(Person::getAge));
Iterator it = personGroups.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
    System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
}

17. Stream和Streams的区别:
    Stream: 接口
    Streams: final类

18. IntStream.range()/rangeClosed(): 这个方法属于IntStream锁独有的.

19. Stream并行时无需使用多线程,会自动将所有操作并行的.                                  【重点】
   [使用并行流的场景](http://gee.cs.oswego.edu/dl/html/StreamParallelGuidance.html)
   这篇文章还看不懂.
