1. 文俊写的 Act161230QianKunFourKing  乾坤四君蓝钻兑换

1) 蓝钻充了几个月/剩余多少个蓝钻可以领取
   核心的类是 AnnualVipManager ——>  UserAnnualVipData

2) 活动充值已充值了多少个月
MemberDao.getInstance().getSaPayActivity(u.getUserId(), ACTIVITY_ID)[0];
