
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class ZNodeChildrenWatcher implements Watcher {

    private final ZooKeeper zooKeeper;
    private int childrenAmmount = -1;

    public ZNodeChildrenWatcher(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
        registerAsWatcher();
    }

    public void process(WatchedEvent watchedEvent) {
        registerAsWatcher();
        try {
            handleCountingDescendants();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleCountingDescendants() throws KeeperException, InterruptedException {
        Stat stat = this.zooKeeper.exists("/znode_testowy", false);
        if (stat != null) {
            int descendants = countDescendants("/znode_testowy");
            stat = this.zooKeeper.exists("/znode_testowy", false);
            if (stat != null) {
                if (childrenAmmount != descendants) {
                    System.out.println("Liczba potomków : ");
                    System.out.println(descendants);
                    childrenAmmount = descendants;
                }
            }
        }
    }

    private int countDescendants(String node) {
        int descendants=0;
        try {
            List<String> children = this.zooKeeper.getChildren(node, false);
            for (String child:children) {
                descendants=descendants+1+countDescendants(node+"/"+child);
            }
        } catch (Exception e) {
            return 0;
        }
        return descendants;
    }

    private void registerAsWatcher() {
        try {
            if (zooKeeper.exists("/znode_testowy", this) != null) {
                register("/znode_testowy");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register(String node) throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(node, this);
        for (String child:children) {
            register(node+"/"+child);
        }
    }
}