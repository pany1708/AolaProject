//0428
private static final int ID_TARGET_RACE = 3256;

private static final Map<Integer, Integer> RACE_TO_POS = new HashMap<Integer, Integer>();
static {
  RACE_TO_POS.put(3251, 2);
  RACE_TO_POS.put(3230, 5);
  RACE_TO_POS.put(3239, 8);
}
static {
  BattleHandlerCollector.register(CMDHEAD + "_f", new Fight170428LFGC());
}


private static final Service SERVICE_ONE_KEY = LocalDate.now().isAfter(LocalDate.of(2017, 5, 4)) ?
			Service.of("【智慧王】一键获得首周后") : Service.of("【智慧王】一键获得首周");

//一次性取出多个数据,省了连接
Integer mcData[] = McData.getMultiDataRawIntegerArray(u.getUserId(), MC_POS, MC_FLAG, MC_MAZE_ENTER_COUNT, MC_WISDOM_ENTER_COUNT, MC_XB_PASS_COUNT);
res.putInt("pos", get(mcData[0], POS_INIT));
res.putInt("flag", getFlag(u, mcData[1]));
res.putInt("map", get(mcData[2], 0));
res.putInt("boss", get(mcData[3], 0));
res.putInt("evo", DB_EVO.get(u.getUserId()));
res.putInt("xb", get(mcData[4], 0));



AolaExpert.finishAct(u, "智慧王挑战获得智慧王");

LocalDate date = LocalDate.now();


StringBuilder sb = new StringBuilder();
Stream.of(ids).forEach(id -> {
sb.append(String.format(CLOTH_FORMAT, id)).append(";");
  LogParticipationHandler.log(u, ID_EVENT_ID_MAP.get(id));
});


Integer[] ids = new Integer(10);
List<Integer> idList = Arrays.asList(ids);
private boolean isLegal(User u, Integer[] ids){
  if (ids.length > DISCOUNTS.length){
    return false;
  }
  List<Integer> idList = Arrays.asList(ids);
  ArrayList<Integer> existIdList = ClothesManager.instance.getExistedClothId(u.getUserId(), idList);
  if (existIdList.size() > 0){
    return false;
  }
  HashSet<Integer> set = new HashSet<>();
  set.addAll(idList);
  return set.size() == ids.length && !Stream.of(ids).anyMatch(id -> !ID_PRICE_MAP.containsKey(id));
}


private static final ThreadLocal<DateFormats> dateFormats = new ThreadLocal<DateFormats>();
