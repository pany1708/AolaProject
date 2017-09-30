1. 浅复制:

2. 深复制: 把要复制的对象所引用的对象都复制了一遍.

3. Object 类的 clone 方法执行特定的复制操作。首先，如果此对象的类不能实现接口 Cloneable，则会抛出 CloneNotSupportedException
注意，所有的数组都被视为实现接口 Cloneable。否则，此方法会创建此对象的类的一个新实例，并像通过分配那样，严格使用此对象相应字段的
内容初始化该对象的所有字段；这些字段的内容没有被自我复制。所以，此方法执行的是该对象的“浅表复制”，而不“深层复制”操作。

4. Object 类本身不实现接口 Cloneable，所以在类为 Object 的对象上调用 clone 方法将会导致在运行时抛出异.

5. Object的clone方法的说明：
在运行时刻，Object中的clone()识别出你要复制的是哪一个对象，然后为此对象分配空间，并进行对象的复制，将原始对象的内容一一复制到新对
象的存储空间中。

继承自java.lang.Object类的clone()方法是浅复制。

6. Java中实现对象的克隆：
  1)为了获取对象的一份拷贝，我们可以利用Object类的clone()方法。
  2)在派生类中覆盖基类的clone()方法，并声明为public。
  3)在派生类的clone()方法中，调用super.clone()。
  4)在派生类中实现Cloneable接口（一个标识性的接口）。

7. eg:

  // 实现clone方法,浅拷贝
  @Override
  protected Stack clone() throws CloneNotSupportedException {

      return (Stack) super.clone();
  }

  //深拷贝
  @Override
  protected Stack clone() throws CloneNotSupportedException {
      Stack result = (Stack) super.clone();
      result.elements = elements.clone(); //对elements元素进行拷贝（引用或基本数据类型）
      return result;
  }
