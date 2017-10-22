1. 对象的clone,在堆里存在2个一样的对象.
   protected native Object clone() throws CloneNotSupportedException;

2. 实现Cloneable接口,重写clone();

  @Override
  public Object clone() throws CloneNotSupportedException{
   return super.clone();
  }

3. 深复制:
public class testDeepClone implements Cloneable {
    public int num = 0;
    public String str = "default";
    public A a;

    public Object clone() throws CloneNotSupportedException {
        testDeepClone o = (testDeepClone) super.clone(); // 仅仅实现了浅复制
        o.str = new String(this.str);
        o.a = (A) a.clone();
        return o;
    }
}

// 成员属性A必须为Cloneable的，否则无法Clone其组合的类
class A implements Cloneable {
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

4. 如果一个类重写了 Object 内定义的 clone() ，需要同时实现 Cloneable 接口（虽然这个接口内并没有定义 clone() 方法），
否则在调用 clone() 时会报 CloneNotSupportedException 异常，也就是说， Cloneable 接口只是个合法调用 clone()
的标识（marker-interface）.

5. 利用Serializable进行深拷贝的时候成员属性也必须是Serializable的，否则只返回一个引用.

6. 特别注意数组的clone(): java中数组类都是默认实现了Cloneable接口【java语法规定】
  数组的clone，仅仅复制的是数组中的元素，即若数组中元素为引用类型，仅仅复制引用
  1) 一维数组
  2) 二维数组: 浅复制
  3) 多维的非基本类型: 浅复制


  Arrays类的copyOf()和copyOfRange()可以实现数组的复制,底层调用Systems的arraycopy()
  Systems的arraycopy() 浅复制
