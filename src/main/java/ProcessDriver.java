import java.util.HashMap;

public class ProcessDriver {
    private static HashMap<Integer, Status> status_map = new HashMap<Integer, Status>();


    public static HashMap<Integer, Status> getStatus_map() {
        return status_map;
    }

    public static void setStatus_map(HashMap<Integer, Status> status_map) {
        ProcessDriver.status_map = status_map;
    }
}
