import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;


public class ZNodeWatcher implements Watcher{

    private final ZooKeeper zooKeeper;
    private final ZNodeChildrenWatcher ZNodeChildrenWatcher;
    private final String[] exec;
    private Process process = null;

    public ZNodeWatcher(final ZooKeeper zooKeeper, String[] exec) {
        addShutdownHookHandlingClosingProcess(zooKeeper);
        this.zooKeeper = zooKeeper;
        ZNodeChildrenWatcher = new ZNodeChildrenWatcher(zooKeeper);
        this.exec = exec;
    }

    private void addShutdownHookHandlingClosingProcess(final ZooKeeper zooKeeper) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(process!=null) process.destroy();
            try {
                zooKeeper.close();
            } catch (Exception e) {
                System.err.println("Cannot close ZooKeeper");
            }
        }));
    }

    public void process(WatchedEvent watchedEvent) {
        try {
            switch (watchedEvent.getType()) {
                case NodeCreated:
                    handleExistingTestNode();
                    break;
                case NodeDeleted:
                    if (process != null) process.destroy();
                    break;
                default:
                    break;
            }
            zooKeeper.exists("/znode_testowy", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleExistingTestNode() throws KeeperException, InterruptedException, IOException {
        zooKeeper.getChildren("/znode_testowy", ZNodeChildrenWatcher);
        process = Runtime.getRuntime().exec(exec);
    }
}