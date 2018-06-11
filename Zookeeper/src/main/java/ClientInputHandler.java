
import org.apache.zookeeper.ZooKeeper;

import java.util.Scanner;

public class ClientInputHandler implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private final ZNodeTreePrinter zNodeTreePrinter;

    public ClientInputHandler(ZooKeeper zooKeeper) {
        this.zNodeTreePrinter = new ZNodeTreePrinter(zooKeeper);
    }

    public void run() {
        while (true) {
            System.out.println("Write quit or tree");
            String line = scanner.nextLine();
            if (line.equals("quit")) break;
            else if (line.equals("tree")) {
                zNodeTreePrinter.startPrintingTree();
            }
        }
    }
}





