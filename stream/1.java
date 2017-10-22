1. 问题: Arrays.stream 和  Stream.of()的区别：

   [网址](http://www.techiedelight.com/difference-stream-of-arrays-stream-java/)


  1) 对于包装类,Stream.of是调用Arrays.stream来实现的.

  2) 对于int基础, 推荐使用Arrays.stream(), Stream.of()返回的不是理想的结果.


2. Stream.iterate():
  Stream.iterate(1, n -> n + 1).skip(100).limit(10).forEach(System.out::println);

3. reduce
  110 = Stream.of(1, 2, 3, 4).reduce(100, Integer::sum);

4. Runnable r = () -> System.out.println("do something.");

  Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("do something.");
    }
  }

5. 函数式接口都可以使用lambda表达式.

public static void donation(Integer money, Consumer<Integer> consumer){
    consumer.accept(money); // 回调方法
}
public static void main(String[] args) {
    donation(1000, money -> System.out.println("好心的麦乐迪为Blade捐赠了"+money+"元")) ;
}

6. flatMap:  层级结构扁平化，就是将最底层元素抽出来放到一起

  降低维度

7: reduce：
  基础数组的累加

8. Stream并行流: 并行化是指为缩短任务执行时间，将一个任务分解成几部分，然后并行执行。
  调用Stream对象的parallel方法
  创建流的时候调用parallelStream而不是stream方法

9.  generator方法：生成一个无限长度的Stream，其元素的生成是通过给定的Supplier
    Stream.generate(Math::random);

10. iterate方法：也是生成无限长度的Stream，和generator不同的是，其元素的生成是重复对给定的种子值(seed)调用用户指定函数来生成的。
其中包含的元素可以认为是：seed，f(seed),f(f(seed))无限循环

Stream.iterate(1, item -> item + 1).limit(10).forEach(System.out::println);

11. Java 8 Stream.peek（）调试流示例
   http://www.java67.com/2016/09/java-8-streampeek-example.html#ixzz4wFTz07JH
