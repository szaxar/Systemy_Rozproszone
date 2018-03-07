import java.io.BufferedReader;
import java.io.PrintWriter;

public class User {

    private int id;
    private String name;
    private PrintWriter out;
    private BufferedReader in;

    public User(int id, String name, PrintWriter out, BufferedReader in) {
        this.id = id;
        this.name = name;
        this.out = out;
        this.in = in;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(String name,String text){
        out.println(name+"send you message: "+text);
    }
}
