1. 清除分库的数据
DELETE FROM ActData4Mod{0} WHERE DataId in(771,772);

注意思考下: [可以参见分库的结构]

连续的记录 between and

2.  truncate table Act160429MayDayFreeGo;  清除一个表的数据

　　delete from Act160429MayDayFreeGo;
