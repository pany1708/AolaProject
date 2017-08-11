EventDispatcher.dispatch(new UserLoginEvent(user, userInfo));


@EventInject(UserLoginEvent.class)
public void listen(UserLoginEvent event) {
   User u = event.u;
   if(LocationService.getCurrentLocationEnum() == LocationEnum.DIAN_XIN && u.getName().equals("900030896")){
      ServerHelper.setUserVariable(u, UV_KEY, 0);
   }
}
