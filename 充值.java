1. 首充蓝宝石: MemberDao.getSaPayActivity(): 注意activityId.

2. 是否为蓝宝石会员: MemberManager.isCarbuncle(u); // 从内存读取或者aolabank.


3. 免费VIP蓝宝石1天
AolaBank 416

4. // 是否在活动期间充值了蓝宝
public static boolean hasCharged(User u) {
  return MemberDao.getInstance().getSaPayActivity(u.getUserId(), ACTIVITY_ID)[0] > 0;
}
