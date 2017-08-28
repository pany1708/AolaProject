select UserId,DataId,Count from ActData8Mod%s where DataId in (#DataId#)

DataId=1142 and Count>=1
DataId=1115 and isFlagset(Count,1)

无需指定 select UserId,DataId,Count from ActData8Mod%s where DataId in (#DataId#)
eventId=24193
