1. SELECT Current, Step FROM IDGenInfo WHERE Name = ? FOR UPDATE;

2. mysql中的select * for update;

    mysql的RowLock和TableLock的触发.

    FOR UPDATE仅适用于InnoDB, 主键必须明确的前提下.

3. MyAsim 只支持表级锁，InnerDB支持行级锁
