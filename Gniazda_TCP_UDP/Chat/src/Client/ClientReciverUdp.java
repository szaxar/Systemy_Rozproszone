package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class ClientReciverUdp implements Runnable {

    private DatagramSocket socket;
    byte[] receiveBuffer = new byte[1024];
    public ClientReciverUdp(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
             while(true) {
            Arrays.fill(receiveBuffer, (byte) 0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            String msg = new String(receivePacket.getData());
            System.out.println("received msg: " + msg);
            }//while
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //run
}
