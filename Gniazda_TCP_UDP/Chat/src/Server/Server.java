package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) throws IOException {

        System.out.println("JAVA TCP SERVER");
        int portNumber = 12345;
        ServerSocket socketTcp = null;
        DatagramSocket socketUdp = null;
        List<User> userList=new ArrayList<>();

        try {
            // create socket
            socketTcp = new ServerSocket(portNumber);
            socketUdp = new DatagramSocket(portNumber);

            while(true){
                // accept client

                Socket clientSocket = socketTcp.accept();
                System.out.println("client connected");

                // in & out streams
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                Thread serverSenderTcp=new Thread(new ServerSenderTcp(userList,out,in,socketUdp));
                Thread serverSenderUdp=new Thread(new ServerSenderUdp(userList,socketUdp));
                serverSenderTcp.start();
                serverSenderUdp.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (socketTcp != null && socketUdp !=null){
                socketTcp.close();
                socketUdp.close();
            }
        }
    }

}
