1. 在memcache中存字符串:
MemcachedFacade.getInstance().setDailyObject(u.getUserId(), CMDHEAD, bonusIndexs);
String bonusIndexs = (String)MemcachedFacade.getInstance().getDailyObject(userId, CMDHEAD);

2. McData的有些暂时还看不懂,自己装个MC试试再说.
