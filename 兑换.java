1. AllPeoplePMTreasureMap里的兑换神宠：

ExchangeItem exchangeItem = new ExchangeItem(cost, BONUS_EXCHANGE.get()[index]); // (cost, produce)
ExchangeResult exchangeResult = exchangeItem.exchange(u); // 开始兑换
if (exchangeResult.getResult() != MaterialResult.SUCCESS) { // 兑换结果
	return exchangeResult.getResult();
}
res.putASObjectList("bn", exchangeResult.getContent());  // 兑换后的奖励

核心的2个类:
  ExchangeItem
  ExchangeResult


2. 通用的兑换流程

前端发送: SynthesizeExtension + 57_8;

相关的类: SynthesizeService

Formula<module>: 包含了对应的数据库的结构.rpg.Formula表的FormulaId发给前端


3. 文俊写的一个兑换的例子. 寻宝记活动里的: Act161021SeekTreasure [STManager#404]
