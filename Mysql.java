1. 	数据库层面实现BitUtil的功能
```
    private static final String SET_BIT = "INSERT INTO ActData%sMod%s(UserId,DataId,Count)VALUES(?,?,1<<?)
                                          ON DUPLICATE KEY UPDATE Count=Count|VALUES(Count)";
    private static final String INCREASE = "INSERT INTO ActData%sMod%s(UserId,DataId,Count) VALUES(?,?,?)
                                          ON DUPLICATE KEY UPDATE Count=Count+VALUES(Count)";
    private static final String DECREASE = "UPDATE ActData%sMod%s SET Count=Count-? WHERE UserId=? AND DataId=?
                                          AND Count>=?";
   private static final String SET_IF_GREATER_THAN = "INSERT INTO ActData%sMod%s(UserId,DataId,Count) VALUES(?,?,?)
                                             ON DUPLICATE KEY UPDATE Count=IF(VALUES(Count)>Count,VALUES(Count),Count)";
   private static final String SET_BIT_OR = "INSERT INTO ActData%sMod%s(UserId,DataId,Count)VALUES(?,?,?)
                                            ON DUPLICATE KEY UPDATE Count=Count|VALUES(Count)";
```

2.
