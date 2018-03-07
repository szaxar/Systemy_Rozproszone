import java.io.BufferedReader;
import java.io.IOException;

public class ClientReciever implements Runnable {


    private String response;
    private BufferedReader in;

    public ClientReciever(BufferedReader in) {
        this.in=in;
    }

    @Override
    public void run() {

        System.out.println("Uruchiam watek recievcer");
        try {

            while (true) {
                response = in.readLine();
                if(response!=null)
                System.out.println("received response: " + response);
            }
        }catch (IOException e) {
                e.printStackTrace();
            }



    }
}
