import CassandraDBHelper.ConnectCassandra;
import com.datastax.driver.core.Session;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Properties;

public class DriverShiftStatus {
        private Session session;
        private Properties properties;
        private  String topic;
        private String[] shift_status_array;

        public DriverShiftStatus(Properties properties, String topic) {
            this.properties = properties;
            this.topic = topic;
            shift_status_array = new String[2];
        }

        public void getdata(){
            ConnectCassandra client = new ConnectCassandra();
            client.connect("127.0.0.1", 9042);
            session = client.getSession();
            final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(Arrays.asList(topic));
            try {
                while (true) {
                    ConsumerRecords<String, GenericRecord> records = consumer.poll(10000);
                    for (ConsumerRecord<String, GenericRecord> record : records) {



                        JSONObject jsonObject= new JSONObject(record.value().get("body").toString());

                        StringBuilder sb = new StringBuilder("INSERT INTO ")
                                .append("TrackDriverLive")
                                .append(".").append("Driverlive").append("(driver_id,shiftstatus ) ")
                                .append("VALUES (").append(jsonObject.get("driver_id"))
                                .append(", '").append(jsonObject.get("status")).append("');");

                        String query = sb.toString();
                        session.execute(query);


                    }



                }
            } finally {
                consumer.close();

            }
        }






    }
