package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class ClientSender implements Runnable {

   private int portNumber;
   private String text;
   private String id;
   private String mode;
   private PrintWriter out;
   private DatagramSocket socket;

    public ClientSender(PrintWriter out, String id,int portNumber) {
        this.out=out;
        this.id = id;
        this.portNumber=portNumber;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
        while(true){
            System.out.println("Write mode of communication(U/T): ");
            mode=scanner.nextLine();
            System.out.println("Write text to send: ");
            text = scanner.nextLine();
            if (!text.contains(":")) {
                if (mode.equals("T")) {  //Tcp
                    out.println(id + ":" + text);  //id:text
                } else { ///UDP
                    socket = new DatagramSocket();
                    InetAddress address = InetAddress.getByName("localhost");
                    byte[] sendBuffer = new String(id+":"+text).getBytes();  //id:text
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                    socket.send(sendPacket);
                } //else
            } //if :
        } //while
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
