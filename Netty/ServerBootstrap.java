1. Socket服务端的启动辅助类

2. 使用了Builder模式[遇到多个构造器参数时要考虑使用构造器]

3. 创建2个EventLoopGroup:
   EventLoopGroup bossGroup = new NioEventLoopGroup();
   EventLoopGroup workerGroup = new NioEventLoopGroup();

1) boss线程组, 用于服务端接受客户端的连接.
2) worker线程组, 用于进行SocketChannel的网络读写.
3) 实际上可以创建一个并共享.

4. 服务端可以使用handler和childHandler:
   handler针对 bossGroup 起作用.
   childerHandler针对 workerGroup起作用.

5. 客户端只能使用handler.

6. Reactor线程就是NioEventLoop.

7. 
