eg: from FistsOfLegendaryHero【传奇大乱斗】
1. 战斗中给敌方亚比（boss）增加一个BUFF：全属性+500
   private static final String BUFFS = "<buff id='122' x='800,800,800,800,800,800' buffReset='false'/>";

2. 战斗中:
@Override
protected void setRobotWantedPros(RobotWanted rw, ActionscriptObject ao, User u, BattleAttr attr) {
		List<String> buffs = new ArrayList<>();
		buffs.add(BUFFS);
    rw.setRobotWanted(BuffRobotAIEx.class);
		for (String buff : buffs) {
			BuffRobotAIEx.addBuff(rw, BuffCreator.createFromXml(buff).toBuff());
		}
}
----------------------------------------------------------------------------------------------------------------

1. 设置敌方的buff是在setRobotWantedPros.

2. 设置我方buff,在setBattleAttr的JoinBattleAttribute

3. 传参数是BattleAttr.设置敌方亚比
