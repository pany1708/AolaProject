1. Arrays.asList(T...a)：
   返回的是一个数组的视图,而并非是 java.util.ArrayList, 是Arrays的一个内部类,速度较快.

   asList体现的是适配器模式,只是转换接口,后台依然是数组
   
