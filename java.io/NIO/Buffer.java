1. 缓冲区: 包含要写入或者要写出的数据. NIO 访问数据都是通过缓冲区进行的.

2. java中基本类型都有对应的Buffer: ByteBuffer.

3. 使用 NIO Buffer 的步骤如下:

将数据写入到 Buffer 中.

调用 Buffer.flip()方法, 将 NIO Buffer 转换为读模式.

从 Buffer 中读取数据

调用 Buffer.clear() 或 Buffer.compact()方法, 将 Buffer 转换为写模式.
