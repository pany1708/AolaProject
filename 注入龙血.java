/**
 * @in "petId" - int : 亚比petId
 * @out "r" - int : 结果返回
 * @out "bn" - Array : 奖励
 */
@Command(value = CMDHEAD + "_inject", name = "注入龙血")
public void inject(String cmd, ActionscriptObject ao, User u, int fromRoom) {
    ActionscriptObject res = ServerHelper.createResponseObj(cmd);
    res.putInt("r", handleInject(u, ao.getInt("petId"), res));
    sendResponse(res, u);
}

private int handleInject(User u, int petId, ActionscriptObject res) {
    int userId = u.getUserId();
    int[] dbData = DbData.getMultiDataArray(userId, DB_PROG, DB_INJECT);
    if (dbData[0] < PROG_COUNT) {
        return -1;
    }
    if (dbData[1] != 0) {
        return -2;
    }
    // 背包中的100级指定亚比
    PM pm = PMUtil.getLivePM(userId, petId);
    if (pm == null || pm.getRaceId() != ID_RACE_INJECT) {
        return -3;
    }
    if (!PMUtil.getAllPMInTheBag(userId).contains(pm)) {
        return -4;
    }
    if (pm.getLevel() < LEVEL_PM_INJECT) {
        return -5;
    }
    DB_INJECT.set(userId, 1);
    res.putASObjectList("bn", BonusManager.addBonusWithoutValidation(u, BONUS_INJECT, 1));
    return MaterialResult.SUCCESS;
}
