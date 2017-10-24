@EventInject(ActBreakKingPassEvent.class)
public void onActBreakKingPassEvent(ActBreakKingPassEvent event){
  handleListenEvent(event.u, HolidayFightMission.破阵王通过1次挑战);
}

EventDispatcher.dispatch(new ActBreakKingPassEvent(u, cache.getProg() - 1));

public class ActBreakKingPassEvent extends Event {
	public final int prog;//通关了哪个关卡，从0-19

	public ActBreakKingPassEvent(User user, int prog){
		super(user);
		this.prog = prog;
	}
}
------------------------------------------------------------------------------------------------------------------

大量被应用的场景: 蓝宝周年抢先看170922  [BlueGemAnniversaryFirstSee]

------------------------------------------------------------------------------------------------------------------
eg: Act170818LegendMingWang

在HolidayResponder中监听
@EventInject(UserLoginEvent.class)
public void login(UserLoginEvent e) {
  if (DB_INIT_HP.get(e.u.getUserId()) == 0) {
    DB_CUR_HP.set(e.u.getUserId(), 99);
    DB_INIT_HP.set(e.u.getUserId(), 1);
  }
}

@Override
public void onInternalEvent(SystemEvent ieo) {
  if (ieo.eventType == SystemEvent.Lost) {
    User u = ieo.user;
    // 注意这里从userId获取user是很艰难的
  }
}
