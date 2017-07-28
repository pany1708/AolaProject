1. 登录
u.getLoginTime()

2. 离线
@Override
public void onInternalEvent(SystemEvent ieo) {
  if (ieo.eventType == SystemEvent.Lost) {
    User u = ieo.user;
    long onlineTime = System.currentTimeMillis() - u.getLoginTime();
    DB_ONLINE.incr(u.getUserId(), (int) (onlineTime / 1000));
  }
}
