select UserId,DataId,Count from ActData8Mod%s where DataId in (#DataId#)

DataId=1142 and Count>=1
DataId=1115 and isFlagset(Count,1)

无需指定 select UserId,DataId,Count from ActData8Mod%s where DataId in (#DataId#)
eventId=24193


buttonId 需要指定


1. LogParticipationHandler.log(u, eventId);

2. 将统计里的字段拷贝进EventIdDefin.txt

  使用EventIdGen跑一下，得到EventId.

3. 将db签入到rpg.BI_ActEventDefine

insert into ButtonIdDefine(ButtonId,Define)values('11474','开学狂欢特惠专场_输出A星亚比军团蛋_总数');

脚本： rpglog

eventId=24784

4. ButtonId:

LogHandler.putLogCommand(new ButtonLog(BUTTON_IDS_SHOP_ITEM[index]));

脚本:   rpglog

select buttonId,value@count from rpglog.ButtonLog where buttonId in (#buttonId#)
buttonId=11833
