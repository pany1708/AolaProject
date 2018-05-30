1. Reactor单线程模型

1) 接受(或者发起tcp连接),读取(或者发送)消息 ====>>> 全部在一个线程上完成.

2. Reactor多线程模型

1) 加了一组线程吃来处理IO操作 [想对于先线程模型].

2) 1个线程来处理客户端的连接【Acceptor线程】, 可能包含了安全认证, 当链路建立之后就在另一个线程里处理网络读取操作.

3) 线程池来处理网络io操作

3. Reacotr主从多线程模型

1) 多线程模型中Acceptor线程在特殊场景下存在性能不足的情况.

2) 处理连接的不再是1个线程, 而是一个线程池.

4. Netty线程模型

1) netty可以支持以上多种线程模型, 取决于启动参数配置.

2) EventLoopGrup是一组nio线程,

EventLoopGroup bossGroup = new NioEventLoopGroup(); // 用于服务端接受客户端连接

EventLoopGroup workerGroup = new NioEventLoopGroup(); // 用于SockentChannel的网络读写
