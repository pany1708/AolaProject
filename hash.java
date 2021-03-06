1. 只要重写equals()，就必须重写hashCode()

2. 因为Set存储的是不重复的对象，依据hashCode和equals进行判断，所以Set存储的对象必须重写这两个方法。

3. hashCode()的作用是获取哈希码, 散列码.确定该对象在哈希表中的索引位置.

4. hashCode() 定义在JDK的Object.java中, 确定该类的每一个对象在散列表中的位置.
   Java集合中本质是散列表的类，如HashMap，Hashtable，HashSet。

5. 在散列表中，
  1) 如果两个对象相等，那么它们的hashCode()值一定要相同；
  2) 如果两个对象hashCode()相等，它们并不一定相等

6. 重写hashCode一个是推荐IDE自动生成,另外一个是调用 java.util.Objects#hashCode 【HashMap.Node】推荐的实现

7. hash()的设计思路:
   1) 使冲突进可能的少.
   2) 解决冲突发生后如何处理

========================================================================================================================
equals()
1. Object里的equals()是通过判断2个对象的地址是否相同来判断的.使用默认的“equals()”方法，等价于“==”方法

2. 因此，我们通常会重写equals()方法：若两个对象的内容相等，则equals()方法返回true；否则，返回fasle.

3. 推荐写法:
  "test".equals(str);
  Objects.equals("test", str);

========================================================================================================================
^
1. 异或操作符
