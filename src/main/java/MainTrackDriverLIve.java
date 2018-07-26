import org.apache.kafka.clients.consumer.*;
import org.apache.log4j.BasicConfigurator;

import java.util.Properties;

public class MainTrackDriverLIve {
    public static Properties props;
    public static String topic1="driver_login_status";
    public static String topic2="driver_shift_status";
    public static String topic3="driver_status";
    public static void main(String[] args) {

        BasicConfigurator.configure();


        props = new Properties();

        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "104.154.186.117:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "TrackDriverLiveTest10");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        props.put("schema.registry.url", "http://35.184.181.97:8089");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");



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




    }


}
