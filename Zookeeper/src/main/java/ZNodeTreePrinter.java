import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZNodeTreePrinter {

    private final ZooKeeper zooKeeper;

    public ZNodeTreePrinter(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void startPrintingTree() {
        printLevelOfTheThree("/znode_testowy", 0);
    }

    private void printLevelOfTheThree(String zNodeName, int level) {
        Stat stat = null;
        try {
            stat = this.zooKeeper.exists("/znode_testowy", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (stat != null) {
            for (int i = 0; i < level; i++) System.out.print("\t");
            System.out.println(zNodeName);
            try {
                for (String children : zooKeeper.getChildren(zNodeName, false)) {
                    printLevelOfTheThree(zNodeName + "/" + children, level + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
