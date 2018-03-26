public class Main {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.preferIPv4Stack","true");
        Client client=new Client();
        client.start();
    }
}
