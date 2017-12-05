1. 通过反射机制创建数组 + 获取数组的Class对象. [并发编程网](http://ifeve.com/java-reflection-10-arrays/)

2. int[] intArray = (int[]) Array.newInstance(int.class, 3); // 反射创建一个数组.

3. Array.set(intArray, 0, 123); // set

4. Array.get(intArray, 0); // get

5. Class stringArrayClass = String[].class;

6. Class componentType = arr.getClass().getComponentType();

7. Array类提供了动态创建和访问Java数组的方法,允许在执行set或get期间进行扩展转换.

8. Class类的isArray()方法判定此Class对象
   clazz;
   boolean isArray = clazz.isArray();
   Class eleClazz = clazz.getComponentType(); // 元素的类型<完整的包名>

   String[] arr = (String[])Array.newInstance(String.class, 2);
   Array.set(arr, 0, "hello");
   Array.set(arr, 1, "world");
   String str1 = Array.get(arr, 1);

9. Array允许在get/set操作时扩展转换.

10. 针对基础类型和obejct头不同的方法：
   set-Boolean
      -Byte
      -Char
      -Double
      -Float
      -Int
      -Long
      -Short
      -
     get_...对应

11. eg:
int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
//获取数组类型的Class 即int.class
Class<?> clazz = array.getClass().getComponentType();
//创建一个具有指定的组件类型和长度的新数组。
//第一个参数:数组的类型,第二个参数:数组的长度
Object newArr = Array.newInstance(clazz, 15);

12. 
