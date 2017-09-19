1. pm是否存在   进化
   PM pm = PMUtil.getLivePM(userId, petId);

2. 是否有 MostGloriousCQWMZW170728.java
  PMUtil.getLocation(u.getUserId(), ID_RACE) != 0

3. 升级技能 BigMouthTalentMachine.java
PMUtil.addBA(u, pm, talent[1] - 1, talent[2])


4.
   int raceId = pm.getRaceId(); // 传递的petId是否正确
   int ce = CombatEffectiveness.getCombatEffectiveness(pm); //战力

5. 传奇进化
 ArrayList<ActionscriptObject> bn = PMUtil.gainLegentPetEgg(u, petId, ID_SUPER_EVO);
 PMUtil.legendEvolve(u, petId, ID_SUPER_EVO, EVO_COST, res)


6. 吸收天赋
BaseAbility ba = pm.getIndividualBA_Storage();
int geniusLevel = PetStarManager.getNewLevel(ba); // 天赋等级  @return 0=一，1=十，2=百，3=千，4=万，5=王，6=天下无双
int[] arr = ba.toIntArray();
		if (arr[talent[1] - 1] >= COUNT_MAX_BA) { // [ HP, Attack, Defence, SpecialAttack,SpecialDefence, Speed ]
			return -3;
}
只有某一些的等级之下的pm才可以吸收技能,技能有上限

7. 核心是PMUtil


8.在战斗时的获取最高战力
PMUtil.getPMMaster(userId).getMaxAllCE();


9.
private boolean validNeedAllTXWS(PM pm, int studyId1, int studyId2, int characterId) {
  if (PMUtil.validatePerfectLevel(pm)) {
    return true;
  }
  if (PMUtil.validatePerfectGeniusTXWS(pm)) {
    return true;
  }
  if (PMUtil.validatePerfectStudy(pm, studyId1, studyId2)) {
    return true;
  }
  if (PMUtil.validatePerfectCharacter(pm, characterId)) {
    return true;
  }
  return false;
}

10. 
