EventDispatcher.dispatch(new UserLoginEvent(user, userInfo));


@EventInject(UserLoginEvent.class)
public void listen(UserLoginEvent event) {
   User u = event.u;
   if(LocationService.getCurrentLocationEnum() == LocationEnum.DIAN_XIN && u.getName().equals("900030896")){
      ServerHelper.setUserVariable(u, UV_KEY, 0);
   }
}


--------------------------------------------------------------------------------------------------------------------
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
