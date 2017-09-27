1. 基于volatile上网双重检查锁定的解决方案:
public class SingleInstance {
  private static volatile SingleInstance sInstance;
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
