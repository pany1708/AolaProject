Arrays工具类提供了针对数组的操作.排序,搜索,将Array转换成list,提供的都是静态的方法.

1. asList():返回一个固定长度的list的便捷方法.
2. binarySearch():返回index.
3. copyOf(): 返回复制数组
   copyOfRange(): 返回指定复制的数组
4. sort(): 对数组排序.
5. toString(): 方便打印数组
   deepToString(): 打印出二维数组
6. equals(): 比较1维数组是否相等
   deepEquals(): 比较复杂的数组是否相等
7. fill(): 对数组元素赋值.


dataType[] var = new dataType[length];

//一维数组
private static final String[] COMMON_BONUS_ARR = new String[]{
    "98:0:888",//晶豆*888
    "2:1241:1",//史诗强化星砂*1
    "2:1071-1&1072-1&1073-1&1074-1&1075-1&1076-1:6:1",//随机超级学习力果*1
    "2:1387:1",//大经验福袋*1
    "2:1386:1",//小经验福袋*1
    "2:1031-1&1032-1&1033-1&1034-1&1035-1&1036-1:6:1",//随机高级学习力果*1
    "2:1240:1",//传说强化星砂*1
    "98:0:666"//晶豆*666
};
int[] s = new int[5];
int[] s1 = new int[]{1,2,3}; //[推荐的写法]

//二维数组
int[][] a = new int[][]{
  {500, 7, 1},
  {500, 10, 1},
  {500, 12, 1},
  {500, 13, 1}
};
String s[ ][ ] = new String[2][];
对于二维数组的空间,必须先为最高维分配引用空间,然后是给元素单独分配空间.
s[0] = new String[2];
s[1] = new String[2];
s[0][0] = new String("God");
s[0][1] = new String("Luck");
