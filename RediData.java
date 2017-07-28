private static final RedisData RD_GOT_APOLO_COUNT = RedisData.newDataInstance(ACTIVITY_NAME, "全服传奇无冕之王满战力的小奥拉数量，此Data的key=0");

get: RD_GOT_APOLO_COUNT.get(0);  RedisKey
其他操作等同于Data

2. 在 SummerHolidayLegendNewForce.java 中有另一种写法,与上面是等价的.
这里的 REDIS_KEY 是个key的String数组
RedisData.getRedisNode().getMulti(REDIS_KEY);
String value = redisVoteMap.get(REDIS_KEY[i]);

RedisData.getRedisNode().incCountBy(REDIS_KEY[i], 1);

3.
RedisData和GlobalData的区别:
private static final GlobalData DATA_GLOBAL_COUNT = GlobalData.newDataInstance("集七星龙珠的总集齐人数");
前者有key, 后者无userId。
在mysql便于统计, RedisData里无法统计

MC_GlobalData 的userId是0
