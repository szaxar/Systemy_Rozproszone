import java.io.PrintWriter;
import java.util.Scanner;

public class ClientSender implements Runnable {


   private String text;
   private String id;
   private PrintWriter out;


    public ClientSender(PrintWriter out, String id) {
        this.out=out;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Uruchamiam watek sender");
        Scanner odczyt = new Scanner(System.in);

        while(true){
            text=odczyt.nextLine();
            out.println(id+":"+text);
        }
    }
}
