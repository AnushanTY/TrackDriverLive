package com.pickme.drivertrack.consumers;

import com.datastax.driver.core.Session;
import com.pickme.config.Config;
import com.pickme.dbhelper.DriverLive_Cassandra;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;

public class DriverConsumer {
        private DriverLive_Cassandra driverLive_cassandra;
        private Session session;
        private Properties properties;
        private  String topicLogin;
        private String topicShift;
        private String topicDriver;
        private String topicDriverLocationChanged;
        private String[] status_array;

        public DriverConsumer(Properties properties, String topicLogin,String topicShift,String topicDriver,String topicDriverLocationChanged) {
            status_array = new String[2];
            this.properties = properties;
            this.topicDriver=topicDriver;
            this.topicDriverLocationChanged=topicDriverLocationChanged;
            this.topicLogin=topicLogin;
            this.topicShift=topicShift;

        }

        public void getdata(){
            driverLive_cassandra= new DriverLive_Cassandra(Config.ADDRESS,Config.PORT);

            final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(Arrays.asList(topicLogin,topicDriver,topicShift,topicDriverLocationChanged));
            try {
                while (true) {
                    ConsumerRecords<String, GenericRecord> records = consumer.poll(100000);
                    for (ConsumerRecord<String, GenericRecord> record : records) {


                        if (record.value().get("type").equals("driver_status_changed")) {

                            JSONObject jsonObject = new JSONObject(record.value().get("body").toString());
                            driverLive_cassandra.insertDriverStatus((int) jsonObject.get("id"), (String) jsonObject.get("status"));


                        }

                        if(record.value().get("type").equals("driver_shift_status_changed")) {

                            JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                            driverLive_cassandra.insertShiftStatus((int) jsonObject.get("driver_id"), (String) jsonObject.get("status"));


                        }if(record.value().get("type").equals("driver_location_changed")) {


                            JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                            Long timestap = (Long) record.value().get("created_at")/1000000;

                            Long currentTme  = Calendar.getInstance().getTimeInMillis();

                            Long activeTime =  (currentTme - timestap);

                            System.out.println(activeTime+ "     timeStam   "+ timestap +" current time"+ currentTme);

                            driverLive_cassandra.insertDriverlocationchanged((int) jsonObject.get("driver_id"), activeTime);
                        }


                        if(record.value().get("type").equals("driver_login_status_changed")) {

                            JSONObject jsonObject= new JSONObject(record.value().get("body").toString());

                            driverLive_cassandra.insertLoginStatus((int)jsonObject.get("id"),(String) jsonObject.get("status"));
                        }

                    }
                }

            } finally {
                consumer.close();

            }
        }




}
