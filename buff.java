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

-----------------------------------------------------------------------------------------------------------------

1.每只传奇灵兽BOSS获得数值加成：全属性数值提升=已通过试炼数*1500

public static final String ENEMY_BUFF_FORMAT = "<buff id='122' x='%d,%d,%d,%d,%d,%d' buffReset='false'/>";

public static String getEnemyPointBuff(int prog){
  int curGate = prog + 1;
  if (curGate < 5){
    return "";
  }
  int point = (curGate - 4) * 1000;
  return String.format(BKConfig.ENEMY_BUFF_FORMAT, point, point, point, point, point, point);
}

rw.setRobotWanted(BuffRobotAIEx.class);
String enemyPointBuff = BKGateHelper.getEnemyPointBuff(prog);
if (enemyPointBuff.length() > 0){
	 BuffRobotAIEx.addBuff(rw, BuffCreator.createFromXml(enemyPointBuff).toBuff());
}

eg: BreakKing.java

------------------------------------------------------------------------------------------------------------------
暑假Buff: Act170630SummerBuff

Act170630SummerBuff.addBuffToPlayer(u, jba); //我方

Act170630SummerBuff.addBuffToBoss(rw);  // 敌方

暑假Buff: 上场亚比获得2级全属性
          暑假挑战Boss血量减少20%.

-----------------------------------------------------------------------------------------------------------------
buff的几个核心的参考：
1. EachRoundAppendBuff.java
2. SkillRange.java
3. StatusUtil.java
4. Status.java
5. StatusType.java 【最为重要】



-------------------------------------------------------------------------------------------------------------------

// 全数值提升1000点
"<buff id='122' buffReset='false' x='1000,1000,1000,1000,1000,1000'/>",
// 上场提升2级全属性
"<buff id='96' x='2,2,2,2,2,2,2,2' buffReset='false'/>",
