import org.jgroups.*;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    private DistributedMap distributedMap;
    private Scanner scanner = new Scanner(System.in);
    JChannel ch;
    public Client() {
    }

    public void start() throws Exception {

        ch=new JChannel(false);
        setProtocols();
        ch.connect("hashMap");
        distributedMap=new DistributedMap(ch);
        while(true){
            System.out.println("Write operation type with arguments 1-containsKey , 2- get , 3- put , 4 -remove");
            String text=scanner.nextLine();
            String[] arguments = text.split(" ");
            try {
                switch (arguments[0]) {
                    case "1":
                        Validation(arguments);
                        System.out.println(distributedMap.containsKey(arguments[1]));
                        break;
                    case "2":
                        Validation(arguments);
                        System.out.println(distributedMap.get(arguments[1]));
                        break;
                    case "3":
                        putValidation(arguments);
                        System.out.println(distributedMap.put(arguments[1], arguments[2]));
                        break;
                    case "4":
                        Validation(arguments);
                        System.out.println(distributedMap.remove(arguments[1]));
                        break;
                }
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void setProtocols() throws Exception {
        ProtocolStack stack=new ProtocolStack();
        ch.setProtocolStack(stack);
        stack.addProtocol(new UDP().setValue("mcast_group_addr", InetAddress.getByName("230.0.0.123")))
        .addProtocol(new PING())
                .addProtocol(new MERGE3())
                .addProtocol(new FD_SOCK())
                .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                .addProtocol(new VERIFY_SUSPECT())
                .addProtocol(new BARRIER())
                .addProtocol(new NAKACK2())
                .addProtocol(new UNICAST3())
                .addProtocol(new STABLE())
                .addProtocol(new GMS())
                .addProtocol(new UFC())
                .addProtocol(new MFC())
                .addProtocol(new FRAG2())
                .addProtocol(new STATE_TRANSFER());
        stack.init();
    }


    public void putValidation(String[] arguments){
        if(arguments.length!=3) {
            throw new IllegalArgumentException("Put method needs 3 arguments");
        }
    }

    public void Validation(String[] arguments){
        if(arguments.length!=2) {
            throw new IllegalArgumentException("This method needs 2 arguments");
        }
    }
}


