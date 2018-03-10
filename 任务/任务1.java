/**
* 装备2个3级铭文,铭文等级从0开始
*/
private boolean handleSecondTask(int userId) {
PM pm = getPM(userId);
if (pm == null) {
	return false;
}
int[] inscriptionIds = pm.getInscriptionIds();
if (Arrays.stream(inscriptionIds).filter(i -> i != -1).count() != 2) { // 铭文数量不为2
	return false;
}
if (Arrays.stream(inscriptionIds).map(id -> Inscription.getDefine(id).getLevel()).anyMatch(i -> i < 2)) {
	return false;
}
return true;
}

/**
* 解锁超元气破技能
*/
private boolean handleThirdTask(int userId) {
PM pm = getPM(userId);
if (pm == null) {
	return false;
}
if(Arrays.stream(pm.getUsedSkillArray()).anyMatch(item -> pm.getSkill().get(item[0]).getSkillData()
		.isDegeneratorNormalSuperSkill())){
	return true;
}
return false;
}


/**
 * 战斗力30000以上
 */
private boolean handleFourthTask(int userId) {
	PM pm = getPM(userId);
	if (pm == null) {
		return false;
	}
	if (CombatEffectiveness.getCombatEffectiveness(pm)>= 30000) {
		return true;
	}
	return false;
}

// 新亚比战斗力
private static final int MAX_CE = CombatEffectiveness
        .getMaxCombatEffectiveness(PMDataManager.getInstance().getPMData(PM_RACE_ID));
