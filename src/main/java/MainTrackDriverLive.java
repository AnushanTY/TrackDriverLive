import com.pickme.config.Config;
import com.pickme.display.Display;
import com.pickme.display_dashboard.Dashboard;
import com.pickme.drivertrack.consumers.DriverAndTripConsumer;
import org.apache.kafka.clients.consumer.*;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainTrackDriverLive {
    private static Properties props;
    private static String topicLogin=Config.TOPIC_LOGIN;
    private static String topicShift=Config.TOPIC_SHIFT;
    private static String topicDriver=Config.TOPIC_DRIVER;
    private static String topicDriverLocationChanged=Config.TOPIC_DRIVER_LOCATION_CHANGED;
    private static  String topicTrip=Config.TOPIC_TRIP;
    private static Logger logger=Logger.getLogger(MainTrackDriverLive.class.getClass().getName());

    private static FileHandler fh;

    static {
        try {
            fh = new FileHandler("/home/pickme-1031/IdeaProjects/TrackDriverLive/src/main/java/com/pickme/local/logger.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        //Display display = new Display();  Displaying filtering.
        //display.run();


        Dashboard dashboard = new Dashboard();
        dashboard.run();


       BasicConfigurator.configure();


        props = new Properties();

        props.put("enable.auto.commit", Config.AUTO_COMMIT);
        props.put("auto.commit.interval.ms", Config.AUTO_COMMIT_INTERVAL);
        props.put("session.timeout.ms", Config.SESSION_TIME_OUT);

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS_CONFIG);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Config.GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Config.KEY_DESERIALIZER_CLASS_CONFIG);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Config.VALUE_DESERIALIZER_CLASS_CONFIG);
        props.put("schema.registry.url",Config.SCHEMA_URL);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, Config.AUTO_OFFSET_RESET_CONFIG);
        // Send logger output to our FileHandler.
        logger.addHandler(fh);
        // Request that every detail gets logged.
        logger.setLevel(Level.ALL);
        // Log a simple INFO message.
        logger.info("doing stuff");


        DriverAndTripConsumer driverConsumer= new DriverAndTripConsumer(props, topicLogin,topicShift,topicDriver,topicDriverLocationChanged,topicTrip);

        try {

            driverConsumer.getdata();

        }catch (Exception e){
            logger.log(Level.ALL,"ConsumerWork",e);
        }







    }


}
