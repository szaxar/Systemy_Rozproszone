import java.io.Serializable;

public class MapHandler implements Serializable {

    private static long serialVersionUID = 1L;
    private Type type;
    private String key;
    private String value;

    public MapHandler(String key, String value) {
        this.type = Type.PUT;
        this.key = key;
        this.value = value;
    }

    public MapHandler(String key) {
        this.type = Type.REMOVE;
        this.key = key;
        this.value = "";
    }

    @Override
    public String toString() {
        return type + " " + key + " " + value;
    }

}


