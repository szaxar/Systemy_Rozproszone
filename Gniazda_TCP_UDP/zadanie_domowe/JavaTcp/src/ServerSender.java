import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ServerSender implements Runnable {
    private PrintWriter out;
    private BufferedReader in;
    private List<User> userList;

    public ServerSender(List<User> userList,PrintWriter out, BufferedReader in) {
        this.userList=userList;
        this.out = out;
        this.in = in;

    }

    @Override
    public void run() {

        while (true) {
        try {
                // read msg, send response
                String msg = in.readLine();
                System.out.println("received msg: " + msg);
                if (msg.charAt(0) == '0') {
                    User user = new User(userList.size() + 1, msg.substring(1), out, in);
                    userList.add(user);
                    out.println(user.getId());
                } else {
                    String[] tmp=msg.split(":");
                  int senderId=Integer.parseInt(tmp[0]);
                  User sender=userList.get(senderId-1);
                    for (User user : userList) {
                        if(user.getId()!=senderId) {
                            System.out.println("Wysylam do: " + user.getName());
                            user.sendMessage(sender.getName(), tmp[1]);
                        }
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
