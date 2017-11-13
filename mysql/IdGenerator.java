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

5. Connection.setAutoCommit(false);
   1)setAutoCommit默认值是ture,即为每一条sql在各自的事物中执行.
   2)一个操作需要多个事物配合时,开始：conn.setAutoCommit(false); 结束是:conn.commit();并在catch中执行conn.rollback(),回滚来保证事物的原子性<数据的完整性>.
   3)需要注意的是：设定setAutoCommit(false)若没有在catch中进行Connection的rollBack操作，操作的表就会被锁住，造成数据库死锁。虽然在执行con.close()的时候会释放锁，但若应
     用服务器使用了数据库连接池，连接不会被断开，从而不会放锁.
   4)主要用来保证多张表的更新.
