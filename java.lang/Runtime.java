1. Runtime类代表着Java程序的运行时环境

2. 获取cpu数量:

  Runtime.getRuntime().availableProcessors();

3. 拷贝文件:

Runtime.getRuntime().exec("XCOPY E:\\vstsworkspace\\project2009\\source\\server\\pm.xls E:\\vstsworkspace\\project2009\\source\\server\\extension\\ /Y").waitFor();

在调用runtime去执行脚本的时候，其实就是JVM开了一个子线程去调用JVM所在系统的命令.

Process.waitFor()等待操作完成

4. Runtime.getRuntime().addShutdownHook();
1) 在jvm中增加一个关闭的钩子;
2) 这个操作会在jvm关闭前才会执行.
