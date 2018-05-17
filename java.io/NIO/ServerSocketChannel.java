1. 创建Socket服务器:
ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();//新建NIO通道
serverSocketChannel.configureBlocking(false);//使通道为非阻塞

2. 创建socket连接:
ServerSocket serverSocket = ssc.socket();//创建基于NIO通道的socket连接
serverSocket.bind(new InetSocketAddress("127.0.0.1",SERVERPORT));//新建socket通道的端口

3. 一个基于通道的socket监听器.

    serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		// setReuseAddress应在bind之前调用才有效,其作用为:允许绑定处于TIME_WAIT 状态的端口
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress(ConfigData.SERVER_PORT));

		acceptSelector = Selector.open();
		serverSocketChannel.register(acceptSelector, SelectionKey.OP_ACCEPT);

4. 取出对等的socket设置对应socket选项.

5. 2种方式创建SocketChannel：
打开一个SocketChannel并连接到互联网上的某台服务器。
一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。

6.
