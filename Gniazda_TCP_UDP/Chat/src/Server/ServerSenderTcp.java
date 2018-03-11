package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.util.List;

public class ServerSenderTcp implements Runnable {
    private PrintWriter out;
    private BufferedReader in;
    private List<User> userList;
    private DatagramSocket socket;

    public ServerSenderTcp(List<User> userList, PrintWriter out, BufferedReader in, DatagramSocket socket) {
        this.userList=userList;
        this.out = out;
        this.in = in;
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // read msg, send response
                String msg = in.readLine();
                System.out.println("received msg: " + msg);
                String[] tmp = msg.split(":");

                if (tmp[0].equals("0")) {
                    User user = new User(userList.size() + 1, tmp[1], out, Integer.parseInt(tmp[2]), socket);
                    userList.add(user);
                    out.println(user.getId());
                } else {
                    int senderId = Integer.parseInt(tmp[0]);
                    User sender = userList.get(senderId - 1);
                    for (User user : userList) {
                        if (user.getId() != senderId) {
                            user.sendMessage(sender.getName(), tmp[0], tmp[1]);
                        } //if
                    } //for
                } //else
            } //while
            } catch(IOException e){
                e.printStackTrace();
            }
        } //run
    }


