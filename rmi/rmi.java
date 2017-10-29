1. 远程方法调用(Remote Method Invocation),能够让jvm上的对象像调用本地对象一样调用另一个jvm上的对象的方法.

2. rmi客户端可以根据服务端暴露的接口,就可以去实现调用rmi服务端的代码实现。感觉就像是在调用自己本地的代码一样。但实际上是在调用远程机器上的代码。

3. RPC(remote procedure call protocal)

4. PRC与PMI:
   1) RPC跨语言,而PMI仅仅支持Java.
   2) PMI调用远程对象方法,RPC不支持对象的概念,可以说是:PMI是面向对象方式的JavaRPC.

5. JMS(Java Messaging Service),JMS是Java的消息服务.

6. RMI是Enterprise JavaBeans的支柱，是建立分布式Java应用程序的方便途径。

7. 远程方法调用特性使Java编程人员能够在网络环境中分布操作。RMI全部的宗旨就是尽可能简化远程接口对象的使用。
