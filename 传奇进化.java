
MakeTheCakeSent.java
private static final Data DB_EVO = DbData.newDataInstance(ACTIVITY_NAME, "是否已传奇进化");

res.putInt("ie", DB_EVO.get(userId) > 0 ? 1 : 0); 已经进化

/**
	 * @in "petId" - int : 亚比petId
	 * @out "r" - int : 返回
	 * @out "bn" - Array : 奖励列表
	 */
	@Command(value = CMDHEAD + "_evoEgg", name = "传奇进化亚比蛋")
	public void evoEgg(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		res.putInt("r", handleGetLegendaryEgg(u, ao.getInt("petId"), res));
		sendResponse(res, u);
	}

	private int handleGetLegendaryEgg(User u, int petId, ActionscriptObject res) {
		int userId = u.getUserId();
		PM pm = PMUtil.getLivePM(userId, petId);
		if (pm == null) {
			return -1;
		}
		int[] data = DbData.getMultiDataArray(userId, DB_PROG, DB_EVO);
		if (data[0] < MAKE_COSTS.length) { // 尚未做完蛋糕
			return -1;
		}
		if (data[1] == 1) {// 已经超进化
			return -2;
		}
		ArrayList<ActionscriptObject> bn = PMUtil.gainLegentPetEgg(u, petId, ID_SUPER_EVO);
		if (bn == null) {
			return -3;
		}
		res.putASObjectList("bn", bn);
		DB_EVO.set(userId, 1);
		return 1;
	}


	/**
	 * @in "petId" - int : 亚比petId
	 * @out "r" - int : 结果，1-成功
	 * @out "ce" - int : 进化后的战斗力
	 */
	@Command(value = CMDHEAD + "_extendEvo", name = "传奇进化继承进化")
	public void extendEvo(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		res.putInt("r", handleLegendEvo(u, ao.getInt("petId"), res));
		sendResponse(res, u);
	}

	private int handleLegendEvo(User u, int petId, ActionscriptObject res) {
		int userId = u.getUserId();
		PM pm = PMUtil.getLivePM(userId, petId);
		if (pm == null) {
			return -1;
		}
		int[] data = DbData.getMultiDataArray(userId, DB_PROG, DB_EVO);
		if (data[0] < MAKE_COSTS.length) { // 尚未做完蛋糕
			return -1;
		}
		if (data[1] == 1) {
			return -2;
		}
		int result = PMUtil.legendEvolve(u, petId, ID_SUPER_EVO, EVO_COST, res);
		if (result == 1) {
			DB_EVO.set(userId, 2);
		}
		return result;
	}
