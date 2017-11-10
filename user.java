1. 多多号是否存在:
int userId = UserQuery.name2Id(duoduoId); // 多多号对应的userId
if(userId == 0){
  return "duoduoId错误!";
}

2. 好友的多多号验证
if (!BuddyService.instance.isBuddy(u, duoduoId)) {
  return -1;
}

BuddyList{0} 分库分表

3. u.getName() 得到多多号  8位

4. ServerHelper.getUserNickName(u) 得到昵称

5.

User u = ExtensionHelper.instance().getUserById(userId); // userId -> User

/**
  *  userId -> User [写在UserValData的93];
  */
private static User getUser(int userId) {
		User u = RequestContext.getCurrent().getUser().getRawUser();
		if(u != null && u.getUserId() == userId){
			return u;
		} else {
			return ExtensionHelper.instance().getUserById(userId);
		}
}

6. 用户表Users{0} ——> 开发库: rpg1.User1

userId
userName
lastLoginTime != loginTime(本次登录, User初始化的时候):
   loginTime: user的自身属性
   lastLoginTime: 持久化在数据库
nickName

7. 用于在internalEvent的userlost事件，因为此时通过extensionHelper已经取不到user了

8. 获得用户注册的天数:
  UserDao.instance.getLifeDay(u.getUserId())

9. 
