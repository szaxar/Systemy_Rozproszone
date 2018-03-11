package Server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class User {

    private int id;
    private String name;
    private PrintWriter out;
    private int port;
    private DatagramSocket socket;

    public User(int id, String name, PrintWriter out, int port, DatagramSocket socket) {
        this.id = id;
        this.name = name;
        this.out = out;
        this.port=port;
        this.socket=socket;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(String name,String id,String text){
        out.println(name+" with id "+id+" send you message by TCP: "+text);
    }

    public void sendMessagebyUdp(String name, String id, String text) throws IOException {
        String msg=name+" with id "+id+" send you message by UTP: "+text;
        byte[] sendBuffer = msg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getLocalHost(), port);
        socket.send(sendPacket);
    }
}
