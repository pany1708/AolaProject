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
