// 动态获取战力
private static final int MAX_CE = CombatEffectiveness.getMaxCombatEffectiveness(PMDataManager.getInstance().getPMData(PM_RACE_ID));

// 当前战力
int curCE = CombatEffectiveness.getCombatEffectiveness(pm);

// 在战斗时的获取最高战力
PMUtil.getPMMaster(userId).getMaxAllCE();
