private static final int TIME_CD_ENTER = 10 * 60;
private static final CD CD_HATCH = new CD(MC_CD_TIME, TIME_CD_ENTER);

private static final CD CD_COOLING_1 = new CD(MC_COOLING_1, new CDTotalTimeGetter() {
		@Override
		public int getCDTotalTimeSec(int userId) {
			int usedTimes = MC_TIMES_1.get(userId);
			return usedTimes >= START_CD_TIMES_1 ? COOL_TIME : 0;
		}
	});



2. 在线时长
private int getCurOnLineTime(User u, int lastLoginRemainTime) {
 int secNow = DateUtil.getSecNow();
 int onLineTime = secNow - (int) (u.getLoginTime() / 1000);
 Integer lastGetBonusTime = VAR_LAST_BONUS_TIME.getRaw(u);
 return lastGetBonusTime == null ? lastLoginRemainTime + onLineTime : secNow - lastGetBonusTime;
}

@Override
public void onInternalEvent(SystemEvent ieo) {
 if (ieo.eventType == SystemEvent.Lost) {
	 User u = ieo.user;
	 int userId = u.getUserId();
	 MC_LAST_LOGIN_REMAIN_TIME.set(userId, getCurOnLineTime(u, MC_LAST_LOGIN_REMAIN_TIME.get(userId)));
 }
}
