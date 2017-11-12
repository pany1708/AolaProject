1. SELECT Current, Step FROM IDGenInfo WHERE Name = ? FOR UPDATE;

2. mysql中的select * for update;

    mysql的RowLock和TableLock的触发.

    FOR UPDATE仅适用于InnoDB, 主键必须明确的前提下.

3. MyAsim 只支持表级锁，InnerDB支持行级锁
   mysql的innodb存储引擎实务锁虽然是锁行，但它内部是锁索引的。【如果SELECT 后面若要UPDATE 同一个表单，最好使用SELECT ... UPDATE】

   更新或查询for update的时候，会在where条件中开始为每个字段判断是否有锁，如果有锁就会等待，因为如果有锁，那这个字段的值不确定，只能等待锁commit或rollback后数据确定后再查询。

4. 因此必须透过的事务机制来确保读取及提交的数据都是正确的。
SET AUTOCOMMIT=0; BEGIN WORK;
select * for update;
COMMIT WORK;
