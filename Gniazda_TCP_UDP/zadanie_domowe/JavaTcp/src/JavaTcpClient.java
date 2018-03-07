import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class JavaTcpClient {

    public static void main(String[] args)  throws IOException {

        System.out.println("JAVA TCP CLIENT");
        String hostName = "localhost";
        int portNumber = 12345;
        Socket socket = null;
        String name;
        String id;
        try {
            // create socket
            socket = new Socket(hostName, portNumber);

            // in & out streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send msg, read response
            Scanner odczyt = new Scanner(System.in);
            name = odczyt.nextLine();

            out.println("0"+name);
            String response = in.readLine();
            System.out.println("Received response: " + response);
            id=response;

            Thread sender=new Thread(new ClientSender(out,id));
            Thread reciever=new Thread(new ClientReciever(in));

            sender.start();
            reciever.start();

            sender.join();
            reciever.join();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                socket.close();
            }
        }
    }

}
