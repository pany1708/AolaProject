1. 客户端:
Socket client = new Socket("127.0.0.1", 8919); // ip,port

public class ClientSocket {
  public static void main(String args[]) {
        String host = "127.0.0.1";
        int port = 8919;
        try {
          Socket client = new Socket(host, port);
          Writer writer = new OutputStreamWriter(client.getOutputStream());
          writer.write("Hello From Client");
          writer.flush();
          writer.close();
          client.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

}

2. 服务器端：
int port = 8919;
ServerSocket server = new ServerSocket(port);
Socket socket = server.accept();


public class ServerClient {
  public static void main(String[] args) {
        int port = 8919;
        try {
            ServerSocket server = new ServerSocket(port);
                Socket socket = server.accept();
            Reader reader = new InputStreamReader(socket.getInputStream());
            char chars[] = new char[1024];
            int len;
            StringBuilder builder = new StringBuilder();
            while ((len=reader.read(chars)) != -1) {
               builder.append(new String(chars, 0, len));
            }
            System.out.println("Receive from client message=: " + builder);
            reader.close();
            socket.close();
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
  }
}

3. 客户端-服务器编程:
   java.net包提供了网络编程的类
