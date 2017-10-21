1. alibaba:
【强制】不要在foreach循环里进行元素的remove/add操作。remove元素请使用Iterator方式，如果并发操作，需要对Iterator对象加锁。
正例：
  Iterator<String> iterator = list.iterator();
  while (iterator.hasNext()) {
    String item = iterator.next();
    if (删除元素的条件) {
      iterator.remove();
    }
  }
反例：
  List<String> a = new ArrayList<String>();
  list.add("1");
  list.add("2");
  for (String item : list) {
    if ("1".equals(item)) {
      list.remove(item);
    }
  }

2. HashMap底层是数组加单向链表或红黑树实现的, 引入红黑树是为了提升查询的效率。

  [HashMap源码分析](http://liujiacai.net/blog/2015/09/03/java-hashmap/)

3. 集合类用Iterator类来遍历其包含的元素<java.util.Iterator>;
   Iterator允许调用者在遍历集合类时删除集合类中包含的元素

4. HashMap中提供的三种集合视角，底层都是用HashIterator实现的。

5. 序列化:
  transient Entry<K,V>[] table = (Entry<K,V>[]) EMPTY_TABLE;
  当序列化一个HashMap对象时，保存Entry的table是不需要序列化进来的,
  HashMap重写了writeObject与readObject 方法
  在序列化时，针对Entry的key与value分别单独序列化，当反序列化时，再单独处理即可

6. hashmap是可以
