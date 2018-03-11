package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.List;

public class ServerSenderUdp implements Runnable{

    private DatagramSocket socket;
    private byte[] receiveBuffer = new byte[1024];
    private List<User> userList;

    public ServerSenderUdp(List<User> userList, DatagramSocket socket) {
        this.userList=userList;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while(true) {
            Arrays.fill(receiveBuffer, (byte)0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String msg = new String(receivePacket.getData());
            System.out.println("Received msg: " + msg);

            String[] tmp = msg.split(":");
            int senderId = Integer.parseInt(tmp[0]);
            User sender = userList.get(senderId - 1);
            for (User user : userList) {
                if (user.getId() != senderId) {
                    user.sendMessagebyUdp(sender.getName(), tmp[0], tmp[1]);
                } //if
            } //for
            }//while
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //run
}
