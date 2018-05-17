1. Selector： 选择器.

2. NIO引入了选择器的概念, 选择器用于监听多个通道的事件[比如: 连接打开, 数据到达]. ====>>> 单个线程可以监听多个数据通道.

3. 创建:
Selector selector = Selector.open();
一创建就会处于打开状态.

4. Channel和Selector配合使用，必须将channel注册到selector上:
ServerSocketChannelconfigureBlocking(false);
ServerSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

5. 与Selector一起使用时,Channel必须处于非阻塞状态下.

6. NIO能通过单个线程管理多个 I/O 通道, 主要是通过选择器Selector来实现的.

7. 将通道注册到选择器Selector上时,会返回一个SelectionKey选择键对象,这个键对象标识了通道和选择器之间的注册关系.
   1) 选择键对象会记住你关心的通道.
   2) 会追踪到对应的通道是否已经就绪.
   3) 调用选择器的select()时,相关的键会被更新.用来检查所有被注册到选择器的通道.

   实际上就是将(channel,selector)封装成了一个SelectionKey对象，并将此对象保存在了Selector对象中。

8. close(): 当调用了Selector对象的close()方法，就进入关闭状态.
  用完Selector后调用其close()方法会关闭该Selector，且使注册到该Selector上的所有SelectionKey实例无效.
  通道本身并不会关闭.

9. 只要ServerSocketChannel及SocketChannel向Selector注册了特定的事件，Selector就会监控这些事件是否发生。