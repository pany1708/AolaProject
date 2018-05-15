1. Selector： 选择器.

2. NIO引入了选择器的概念, 选择器用于监听多个通道的事件[比如: 连接打开, 数据到达]. ====>>> 单个线程可以监听多个数据通道.

3. 创建:
Selector selector = Selector.open();

4. Channel和Selector配合使用，必须将channel注册到selector上:
ServerSocketChannelconfigureBlocking(false);
ServerSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

5. 与Selector一起使用时,Channel必须处于非阻塞状态下.
