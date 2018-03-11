package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args)  throws IOException {

        System.out.println("JAVA CLIENT");
        String hostName = "localhost";
        int portNumberTcp = 12345;
        int portNumberUdp;
        Socket socketTcp = null;
        DatagramSocket socketUdp =null;
        String name;
        String id;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your Udp port: ");
        portNumberUdp=scanner.nextInt();

        try {
            // create socket
            socketTcp = new Socket(hostName, portNumberTcp);
            socketUdp = new DatagramSocket(portNumberUdp);

            // in & out streams
            PrintWriter out = new PrintWriter(socketTcp.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socketTcp.getInputStream()));

            // send msg, read response
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Write nick:");
            name = scanner1.nextLine();

            out.println("0"+":"+name+":"+portNumberUdp);  ///0:name:portUdp
            String response = in.readLine();
            System.out.println("Received id: " + response);
            id=response;

            Thread sender=new Thread(new ClientSender(out,id,portNumberTcp));
            Thread reciever=new Thread(new ClientRecieverTcp(in));
            Thread recieverUdp=new Thread(new ClientReciverUdp(socketUdp));
            sender.start();
            reciever.start();
            recieverUdp.start();


            sender.join();
            reciever.join();
            recieverUdp.join();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socketTcp != null && socketUdp !=null ){
                socketTcp.close();
                socketUdp.close();
            }
        }
    }

}
