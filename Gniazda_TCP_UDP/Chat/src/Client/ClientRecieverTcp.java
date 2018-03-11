package Client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientRecieverTcp implements Runnable {


    private String response;
    private BufferedReader in;

    public ClientRecieverTcp(BufferedReader in) {
        this.in=in;
    }

    @Override
    public void run() {
        try {
            while (true) {
                response = in.readLine();
                if(response!=null) {
                    System.out.println(response);
                }
            }
        }catch (IOException e) {
                e.printStackTrace();
            }



    }
}
