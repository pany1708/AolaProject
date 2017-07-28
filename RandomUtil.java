1.从idToGet中平均概率获得一个元素;
int[] idToGet = IntStream.of(Arrays.copyOf(dbBall, dbBall.length - 2)).filter(i -> i == 0).toArray();
return RandomUtil.evenlyRandomGet(idToGet);

2.根据概率随机出下标
private static final int[] RATE_WITH = {17, 17, 17, 17, 17, 10, 5};
int bt = RandomUtil.randomIndex(RATE_WITH);


3.从数组中按照一定概率来选取其中一样元素
public static <E> E randomGet(E[] array, int[] rates) { }


4.随机数0~CJ_CONFIG.length-1
int id = RandomUtil.nextInt(CJ_CONFIG.length-1);
