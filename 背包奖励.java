// TODO: 放入背包的奖励配置
private static final String EXP_FORMAT = "101:0:%d";
ActionscriptObject res = ServerHelper.createResponseObj(cmd);
res.putASObjectList("bn",

BonusManager.addBonusWithoutValidation(u, String.format(EXP_FORMAT, exp), 1));

BuyService.instance.buy(u, DELAY_TIME_SERVICE, 1).getResult();

int[] data = DbData.getMultiDataArray(userId, DB_PACKAGE, DB_BUYGLOD);


//  1.单个奖励的写法
private final static Prop<String> PET_BONUS = Props.newProp(ACTIVITY_NAME, "亚比奖励", "102:3255:1");

BonusManager.instance.gainBonusWithAoResult(u, PET_BONUS.get(), false), 1);

// 2.多个奖励的写法
private static final Prop<String[]> PURPLE_EQUIP_ARR = Props.newProp(ACTIVITY_NAME, "紫装奖励",new String[] {
			"8:2792:1", "8:2786:1", "8:2789:1", "8:2795:1"
	});
BonusManager.addBonusWithoutValidation(u, PURPLE_EQUIP_ARR.get()[index], 1);

// 注意:
Prop.get(): 无参数时返回的第一个T




// 3. 两种写法的本质区别:
private static final String PET_BOUNDS = "102:3576:1";
private final static Prop<String> PET_BONUS2 = Props.newProp(ACTIVITY_NAME, "亚比奖励", "102:3255:1");
PET_BOUNDS = PET_BONUS2.get();


// 4. 发送多个奖励到背包
StringBuilder bonus = new StringBuilder();
for (int i : arr) {
		bonus.append(String.format("0:%d:1", i)).append(";");
}
res.putASObjectList("bn", BonusManager.addBonusWithoutValidation(u, bonus.toString(), 1));


// 4. 随机金装

"8:433-1&521-1&437-1&525-1:4:1",

BonusUtil.genRandomCard(Arrays.asList(4), null, 1)

// 另一种购买的写法；
BuyService.instance.buy(u, MaterialParam.createMaterialParam(serviceId, Material.TYPE_SERVICE, price)).getResult();


根据 Service 来得到对应的价格:
MaterialParam materialParam = MaterialParam.createMaterialParam(STConfig.BUY_MAP_SERVICE_ID, Material.TYPE_SERVICE, 1);
int buyResult = BuyService.instance.buy(u, materialParam).getResult();
if (buyResult == MaterialResult.SUCCESS){
	int price = materialParam.getMaterial().getPrice();
}


放大奖励 + 合并奖励 ：  BonusUtil
