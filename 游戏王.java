// 从游戏王活动获得的晶豆数据
	private int getSBNum(User u) {
		return SmallGameManager.getInstance().getWeeklyGain(u);
	}
