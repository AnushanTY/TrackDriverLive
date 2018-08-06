import com.pickme.config.Config;
import com.pickme.display_dashboard.Dashboard;
import com.pickme.drivertrack.consumers.DriverAndTripConsumer;
import org.apache.kafka.clients.consumer.*;
import org.apache.log4j.BasicConfigurator;
import java.util.Properties;

public class MainTrackDriverLive {
    private static Properties props;
    private static String topicLogin;
    private static String topicShift;
    private static String topicDriver;
    private static String topicDriverLocationChanged;
    private static  String topicTrip;



    public static void main(String[] args) {


        Dashboard dashboard = new Dashboard();
        dashboard.run();


        BasicConfigurator.configure();
        Config config=new Config();

        props = new Properties();

        props.put("enable.auto.commit", config.getProp().getProperty("AUTO_COMMIT"));
        props.put("auto.commit.interval.ms", config.getProp().getProperty("AUTO_COMMIT_INTERVAL"));
        props.put("session.timeout.ms", config.getProp().getProperty("SESSION_TIME_OUT"));

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getProp().getProperty("BOOTSTRAP_SERVERS_CONFIG"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getProp().getProperty("GROUP_ID_CONFIG"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getProp().getProperty("KEY_DESERIALIZER_CLASS_CONFIG"));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getProp().getProperty("VALUE_DESERIALIZER_CLASS_CONFIG"));
        props.put("schema.registry.url",config.getProp().getProperty("SCHEMA_URL"));
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,config.getProp().getProperty("AUTO_OFFSET_RESET_CONFIG"));


        topicLogin=config.getProp().getProperty("TOPIC_LOGIN");
        topicShift=config.getProp().getProperty("TOPIC_SHIFT");
        topicDriver=config.getProp().getProperty("TOPIC_DRIVER");
        topicDriverLocationChanged=config.getProp().getProperty("TOPIC_DRIVER_LOCATION_CHANGED");
        topicTrip=config.getProp().getProperty("TOPIC_TRIP");
        DriverAndTripConsumer driverConsumer= new DriverAndTripConsumer(props, topicLogin,topicShift,topicDriver,topicDriverLocationChanged,topicTrip);

        driverConsumer.getdata();








    }


}
