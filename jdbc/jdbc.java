1. 数据库的编程语言是SQL (Structured Query Language): 结构化的查询语言.
   1) 抽象的看，访问数据库就是执行SQL语句，返回查询记录集或操作结果。具体一点，访问数据库的应用程序首先要和数据库建立tcp链接，
   2) 把SQL和参数通过数据库的访问协议编码，通过链接发给数据库系统，数据库收到之后，执行，把结果发回给应用程序，应用程序解码结果，转换成相应语言的数据结构.

2. 访问协议的实现称为数据库驱动[driver].

3. jdbc: java.sql.*; java访问数据库常见的类有3个:
    1) Connection: 该接口负责维护数据库和程序之间的会话[链接], 从网络通讯的角度看, 就是一个Socket链接.
    2) Statement: 指定SQL语句的类.
    3) ResultSet: 查询返回的结果集.

4. 数据库服务器同时保持的tcp链接数是一定的, 用完了要立即释放资源.

5.  gc机制只能回收内存资源, 非内存资源还是需要手动释放的.
