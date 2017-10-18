1. 通过反射机制创建数组 + 获取数组的Class对象. [并发编程网](http://ifeve.com/java-reflection-10-arrays/)

2. int[] intArray = (int[]) Array.newInstance(int.class, 3); // 反射创建一个数组.

3. Array.set(intArray, 0, 123); // set

4. Array.get(intArray, 0); // get

5. Class stringArrayClass = String[].class;

6. Class componentType = arr.getClass().getComponentType();
