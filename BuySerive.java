1. MaterialParam buyParam = MaterialParam.createMaterialParam(serviceId, Material.TYPE_SERVICE, 1);
		int result = BuyService.instance.buy(u, buyParam).getResult();

2. int result = BuyService.instance.buy(u, ONE_KEY_GAIN_SERVICE, 1).getResult();

这2个的写法注意代码的实现

// 代币的例子
// 文俊的【做16.7.29周年蛋糕六的飞起】Act160729SixToFly
int buyResult = ProduceService.instance.produce(u, new MaterialFormula(100, getMakeCost(curProg),
                null), false).getResult();
