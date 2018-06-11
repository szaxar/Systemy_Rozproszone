

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperClient implements Runnable, Watcher {

    private final static int TIMEOUT = 10000;
    private final String hostAndPort;
    private final String[] exec;

    public ZookeeperClient(String hostAndPort, String[] exec) {
        this.hostAndPort = hostAndPort;
        this.exec = exec;
    }

    public void run() {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(hostAndPort, TIMEOUT, this);
            ZNodeWatcher zNodeWatcher = new ZNodeWatcher(zooKeeper, exec);
            Stat stat = zooKeeper.exists("/znode_testowy", zNodeWatcher);
            if (stat != null) zNodeWatcher.handleExistingTestNode();
            new Thread(new ClientInputHandler(zooKeeper)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.toString());
    }

    public static void main(String[] args) {
            String exec[] = new String[args.length - 1];
            System.arraycopy(args, 1, exec, 0, exec.length);
            new Thread(new ZookeeperClient(args[0], exec)).start();
    }
}