1. list转数组,必须强制使用toArray(T[] array),传入的是类型完全一样的数组.
   toArray()无参函数,返回的是Object[]类,强转会出现类型错误.

2. subList(): 这个是原list的视图.
  subList方法获得的List并不是截取出的一个List，而是通过对原来的List做了封装，提供了一些方法来管理，通过偏移量来进行控制，
  因此一旦改变subList，原来的List同时发生变动。

3. 推荐使用subList()处理局部列表

4. 
