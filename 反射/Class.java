1. getMethods(): 返回类及其父类(实现接口的方法),所有的public方法.

2. getDeclaredMethods():类内声明的所有的方法,包含了private,public..

3. 2个侧重点不同,2使用的较多．

4. public Method getMethod(String name, Class<?>... parameterTypes);
   这个函数的是返回知道的方法. 
