1. 在memcache中存字符串:
MemcachedFacade.getInstance().setDailyObject(u.getUserId(), CMDHEAD, bonusIndexs);
String bonusIndexs = (String)MemcachedFacade.getInstance().getDailyObject(userId, CMDHEAD);
