1. 我做的聊天功能的那个[活动排行榜] + [消息转发]   SummerHolidayLegendNewForce.java

//活动排行榜
private static final ActTopCache ACT_TOP = new ActTopCache(CMDHEAD, 3 * 1000, 0, MAX_NEWS_COUNT);

sql: ActivityTops

private static final ActTopCache ACT_TOP = new ActTopCache(CMDHEAD, 3 * 1000, 0, MAX_NEWS_COUNT);
static {
	for (int i = 0; i < REDIS_KEY.length; ++i) {
		REDIS_KEY[i] = CMDHEAD + "_num" + i;
	}
	ACT_TOP.setTransformer(new IActTopTransformer() {
		@Override
		public ActionscriptObject transform(ActTopInfo info) {
			ActionscriptObject ao = new ActionscriptObject();
			String infoStr = info.getAttachment();
			int index = infoStr.indexOf(STR_KEY);
			ao.putString("dd", infoStr.substring(0, index));
			int index2 = infoStr.indexOf(STR_KEY, index+1);
			ao.putString("nn", infoStr.substring(index+1, index2));
			ao.putString("news", infoStr.substring(index2+1));
			return ao;
		}
	});
}

ACT_TOP.getTopAoCache()   ArrayList<ActionscriptObject>
ScheduleManagement.getInstance().execute(() -> {
			ACT_TOP.submit(u.getUserId(),
					String.format("%s#%s#%s", u.getName(), ServerHelper.getUserNickName(u), news));
});

2. 排行榜
sql: NewTops
MostGloriousCQWMZW170728.java

这类需要自己手动的在NewTopsDefine里插入数据
INSERT INTO `NewTopsDefine` (`Name`, `TopNum`, `Desc`) VALUES ('MostGloriousHHWJ170825', 10000, 0);

无需初始化. get(key);

炫一下
int buyResult = BuyService.instance.buy(u, SERVICE_SHARE, 1).getResult();
if (buyResult == MaterialResult.SUCCESS) {
		MessageUtil.sendWorldMessage(u, new CQWMZWShareMessage(u, ce, msg));  // 消息转发
}


get: TopService.getInstance().viewTop()
set: TopService.getInstance().submit()

private static final RedisData RD_GOT_APOLO_COUNT = RedisData.newDataInstance(ACTIVITY_NAME, "全服传奇无冕之王满战力的小奥拉数量，此Data的key=0");
private static List<ActionscriptObject> getTop(int startIndex, int length) {
	return TopService.getInstance().viewTop(KEY, startIndex, length).stream()
			.map(info -> ViewTopCache.defaultTransformer.transform(info, -1)).collect(Collectors.toList());
}

private int getPageCount() {
	return (TopService.getInstance().getSize(KEY) - 1) / COUNT_ITEM + 1;
}
TopService.getInstance().getRanking(KEY, u.getUserId());

TopService.getInstance().getRanking(KEY, u.getUserId());
TopService.getInstance().submit(KEY, u.getUserId(),
						String.format("%s#%s", u.getName(), ServerHelper.getUserNickName(u)),
						Long.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))));


3.我写的实例

1). Top
   [20170707] MostGloriousCQTSW170707

2). ActTop
   [20170707] SummerHolidayLegendNewForce


4. 新的部分: ViewTopCache  [BKManager]破阵王

sql: NewTops
对应的sql语句: Job170505BreakKingTop

private static final ViewTopCache TOP = new ViewTopCache(CMDHEAD, 3 * 1000L, 0, 100);

5. 总结：
ActTopService  ActivityTops      ActTopCache
TopService     NewTops           ViewTopCache

1)有Service的是直接从AppServer里读取的,
2)有cache的是先从cache读取,有强制的冷却时间,写入数据库的时候是排队写入的.
