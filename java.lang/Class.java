1. Class.asSubClass():
  应用场景: Class.forName("xxx.xxx.xxx").asSubclass(List.class).newInstance();
     限制xxx是List的子类,正常执行,否则抛出异常ClassCastException。

  java.lang.ClassCastException是进行强制类型转换的时候产生的异常，强制类型转换的前提是父类引用指向的对象的类型是子类的时候才可以
  进行强制类型转换，如果父类引用指向的对象的类型不是子类的时候将产生java.lang.ClassCastException异常。

  1)通过o.getClass().getName()得到具体的类型，可以通过输出语句输出这个类型，然后根据类型进行进行具体的处理。

  2)通过if(o instanceof 类型)的语句来判断o的类型是什么.


2. Class.cast():


3.  forName和newInstance结合起来使用，可以根据存储在字符串中的类名创建对象。例如
    Object obj = Class.forName(s).newInstance();
