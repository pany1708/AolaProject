1. 基于volatile上双重检查锁定的解决方案:  双重检查锁定来实现延迟初始化
public class SingleInstance {
  private static volatile SingleInstance sInstance; // volatile  禁止重排序
  private SingleInstance() {
  }

  public static SingleInstance getInstance() {
      if (null == sInstance) {
          synchronized (SingleInstance.class) {
              if (null == sInstance) {
                  sInstance = new SingleInstance();
              }
          }
      }
      return sInstance;
  }
}
这种可以应该多线程的场景.
懒汉式: 实例在第一次使用时创建.

2. 饥汉模式
public class SingleInstance {
  private static SingleInstance sInstance = new SingleInstance();

  private SingleInstance() {
  }

  public static SingleInstance getInstance() {
      return sInstance;
  }
}

这种模式的缺点：
1) 如果构造方法中存在过多的处理，会导致加载这个类时比较慢，可能引起性能问题。
2) 如果使用饿汉式的话，只进行了类的装载，并没有实质的调用，会造成资源的浪费。

3. 双重检查锁的分析:
public class UnsafeLazyInitialization {
  private static Instance instance;
  public static Instance getInstance() {
    if (instance == null) //1：A线程执行
      instance = new Instance(); //2：B线程执行
    return instance;
  }
}

分析可以知道:
  假设A线程执行代码1的同时，B线程执行代码2。此时，线程A可能会看到instance引用的对象还没有完成初始化

sInstance = new SingleInstance(): new操作的实质:
  给 instance 分配内存
  调用 Singleton 的构造函数来初始化成员变量
  将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）

4.
  静态内部类 static nested class
  我比较倾向于使用静态内部类的方法，这种方法也是《Effective Java》上所推荐的。

  public class Singleton {
      private static class SingletonHolder {
          private static final Singleton INSTANCE = new Singleton();
      }
      private Singleton (){}
      public static final Singleton getInstance() {
          return SingletonHolder.INSTANCE;
      }
  }
