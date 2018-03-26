import org.jgroups.*;
import org.jgroups.util.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static java.lang.System.*;


public class DistributedMap extends ReceiverAdapter implements SimpleStringMap {

    private Channel ch;
    private Map<String , String> distrubutedHashMap = new ConcurrentHashMap<>();
    private MapHandler mapHandler;

    public DistributedMap(Channel ch) throws Exception {
        this.ch=ch;
        ch.setReceiver(this);
        ch.getState(null,10000);
    }

    @Override
    public boolean containsKey(String key) {
        return distrubutedHashMap.containsKey(key);
    }

    @Override
    public String get(String key) {
        return distrubutedHashMap.get(key);
    }

    @Override
    public String put(String key, String value) throws Exception {
        mapHandler=new MapHandler(key,value);
        sendMessage(mapHandler);
        return distrubutedHashMap.put(key,value);
    }

    @Override
    public String remove(String key) throws Exception {
        mapHandler=new MapHandler(key);
        sendMessage(mapHandler);
        return distrubutedHashMap.remove(key);
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        synchronized(distrubutedHashMap) {
            Util.objectToStream(distrubutedHashMap, new DataOutputStream(output));
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        Map<String, String> map;
        map = (ConcurrentHashMap<String, String>) Util.objectFromStream(new DataInputStream(input));
        synchronized (distrubutedHashMap) {
            distrubutedHashMap.clear();
            distrubutedHashMap.putAll(map);
        }
        out.println(map.size() + " messages in chat history):");
    }


    public void receive(Message msg) {
        System.out.println(msg.getObject());
        update(msg.getObject());
    }


    private void update(Object message){
        String[] arguments=message.toString().split(" ");
        if(arguments[0].equals(new String("REMOVE"))){
            distrubutedHashMap.remove(arguments[1]);
        }
        else {
            distrubutedHashMap.put(arguments[1],arguments[2]);
        }

    }

   public void sendMessage( MapHandler mapHandler) throws Exception {
       Message msg = new Message(null, null,mapHandler);
       ch.send(msg);
   }

    @Override
    public void viewAccepted(View view) {
        if(view instanceof MergeView) {
            ViewHandler viewHandler = new ViewHandler((JChannel) ch, (MergeView) view);
            viewHandler.start();
        }
    }
}
