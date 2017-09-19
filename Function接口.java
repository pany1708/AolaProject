1. java8中java.util.function包下的函数式编程接口.

这个接口的主要的方法:
apply(): 将Function对象应用到输入的参数上.


2. Java中重要的函数接口:

  Predicate<T>
  Consume<T>
  Function<T,R>
  Supplies<T>
  UnaryOperator<T>
  BinaryOperator<T>


3. 关于function和enum配合使用的例子可以看enum.java

4. ArmyEgg11里的例子
配置:
List<Function<Point, Point>> list = new ArrayList<Function<Point,Point>>();
list.add(point -> new Point(point.rowIndex + 1, point.columnIndex));
list.add(point -> new Point(point.rowIndex, point.columnIndex + 1));
list.add(point -> new Point(point.rowIndex - 1, point.columnIndex));
list.add(point -> new Point(point.rowIndex, point.columnIndex - 1));

调用:
Point newPoint = list.get(directionIndex).apply(point);


5. 消息盒子处的调用
   经典的例子

6.

List<Integer> _numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
Function<Integer, Integer> lambda = value -> value * 2;
List<Integer> doubled = _numbers.stream().map(lambda).collect(java.util.stream.Collectors.toList());
