1. Object里的方法都是非static的
   equals()方法容易抛出空指针异常,应该使用常量或者确定有值的对象来调用equals();

   Object object  = null;
   "test".equals(object);

   推荐使用jdk7新引入的Objects工具类.<java.util.Objects>

2. 要注意基础类和包装类的本质区别:
   对应在【-128，127】范围内的赋值,Integer对象是在IntegerCache.cache产生的,会复用已有的对象,在这个区间的Integer值是可以直接使用==进行判断的.
   在这个区间之外的的所有数据都会在堆上产生,并不会复用已有的对象,推荐equals方法判断.

3. Integer的缓存策略
   jdk5引入的一个有助于节省内存,提高性能的特性。用来节省内存和提高性能,整型对象在内部实现中通过使用相同的对象引用实现了缓存和重用。

   这种策略仅在自动装箱的时候有用，使用构造器创建的Integer对象不能被缓存。

4. Integer的自动装箱实质是: Integer.valueOf();

5. 在创建新的 Integer 对象之前会先在 IntegerCache.cache 中查找。有一个专门的 Java 类来负责 Integer 的缓存。

   IntegerCache [IntegerCache 是 Integer 类中一个私有的静态类].

6.
