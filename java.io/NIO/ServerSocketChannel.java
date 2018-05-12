1. 创建Socket服务器:
ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();//新建NIO通道
serverSocketChannel.configureBlocking(false);//使通道为非阻塞

2. 创建socket连接:
ServerSocket serverSocket = ssc.socket();//创建基于NIO通道的socket连接
serverSocket.bind(new InetSocketAddress("127.0.0.1",SERVERPORT));//新建socket通道的端口

3. 
