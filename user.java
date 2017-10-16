1. 多多号是否存在:
int freUserId = UserQuery.name2Id(duoduoId); // 多多号对应的userId
if(freUserId==0){
  return -1;
}

2. 好友的多多号验证
if (!BuddyService.instance.isBuddy(u, duoduoId)) {
  return -1;
}

BuddyList{0} 分库分表

3. u.getName() 得到多多号  8位

4. ServerHelper.getUserNickName(u) 得到昵称


User u = ExtensionHelper.instance().getUserById(userId); // userId -> User
