import com.pickme.config.Config;
import com.pickme.drivertrack.consumers.DriverLocationChanged;
import com.pickme.drivertrack.consumers.DriverLoginStatus;
import com.pickme.drivertrack.consumers.DriverShiftStatus;
import com.pickme.drivertrack.consumers.DriverStatus;
import org.apache.kafka.clients.consumer.*;
import org.apache.log4j.BasicConfigurator;

import java.util.Properties;

public class MainTrackDriverLive {
    public static Properties props;
    public static String topic1=Config.TOPIC_LOGIN;
    public static String topic2=Config.TOPIC_SHIFT;
    public static String topic3=Config.TOPIC_DRIVER;
    public static String topic4=Config.TOPIC_DRIVER_LOCATION_CHANGED;
    public static void main(String[] args) {


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



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    DriverLoginStatus driverLoginStatus= new DriverLoginStatus(props,topic1);
                    driverLoginStatus.getdata();

                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    DriverShiftStatus driverShiftStatu=new DriverShiftStatus(props,topic2);
                    driverShiftStatu.getdata();


                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    DriverStatus driverStatus = new DriverStatus(props, topic3);
                    driverStatus.getdata();
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    DriverLocationChanged driverLocationChanged=new DriverLocationChanged(props,topic4);
                    driverLocationChanged.getdata();
                }
            }
        }).start();


    }


}
