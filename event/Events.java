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
    try {
//				Integer userVarEnter = VAR_ENTER.getRaw(u); // 注意去看UserVarData的实现
//				int enterFlag = (userVarEnter == null) ? 0 : userVarEnter.intValue();
      if (VAR_ENTER.get(u.getUserId()) == 1) {
        startCD(u);
      }
    } catch (Exception e) {
      Logger.info("百万经验吃出来活动掉线之后,cd开启异常");
    } finally {
      VAR_ENTER.clear(u.getUserId());
      VAR_GET.clear(u.getUserId());
    }
  }
}
