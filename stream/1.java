1. 问题: Arrays.stream 和  Stream.of()的区别：

   [网址](http://www.techiedelight.com/difference-stream-of-arrays-stream-java/)


  1) 对于包装类,Stream.of是调用Arrays.stream来实现的.

  2) 对于int基础, 推荐使用Arrays.stream(), Stream.of()返回的不是理想的结果.


2. Stream.iterate():
  Stream.iterate(1, n -> n + 1).skip(100).limit(10).forEach(System.out::println);

3. reduce
  110 = Stream.of(1, 2, 3, 4).reduce(100, Integer::sum);
