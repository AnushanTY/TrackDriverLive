package com.pickme.drivertrack.consumers;

import com.datastax.driver.core.Session;
import com.pickme.config.Config;
import com.pickme.dbhelper.DatabaseSwitcher;
import com.pickme.dbhelper.DriverLiveCassandra;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;

public class DriverAndTripConsumer {
        private DatabaseSwitcher databaseSwitcher;
        private Config config;
        private Session session;
        private Properties properties;
        private  String topicLogin;
        private String topicShift;
        private String topicDriver;
        private String topicDriverLocationChanged;
        private String topicTrip;
        private String[] status_array;

        public DriverAndTripConsumer(Properties properties, String topicLogin, String topicShift, String topicDriver, String topicDriverLocationChanged, String topicTrip) {
            status_array = new String[2];
            this.properties = properties;
            this.topicDriver=topicDriver;
            this.topicDriverLocationChanged=topicDriverLocationChanged;
            this.topicLogin=topicLogin;
            this.topicShift=topicShift;
            this.topicTrip=topicTrip;

        }

        public void getdata(){
            config=new Config();
            databaseSwitcher= new DatabaseSwitcher("CASSASNDRA");
            final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(Arrays.asList(topicLogin,topicDriver,topicShift,topicDriverLocationChanged,topicTrip));
            try {
                while (true) {
                    ConsumerRecords<String, GenericRecord> records = consumer.poll(100000);
                    for (ConsumerRecord<String, GenericRecord> record : records) {
                        System.out.println(record.value().get("type"));

                        if (record.value().get("type").toString().equals("driver_status_changed")) {



                            JSONObject jsonObject = new JSONObject(record.value().get("body").toString());
                            databaseSwitcher.insertDriverStatus((int) jsonObject.get("id"), (String) jsonObject.get("status"));


                        }

                        if(record.value().get("type").toString().equals("driver_shift_status_changed")) {



                            JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                            databaseSwitcher.insertShiftStatus((int) jsonObject.get("driver_id"), (String) jsonObject.get("status"));


                        }if(record.value().get("type").toString().equals("driver_location_changed")) {


                            JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                            Long timestap = (Long) record.value().get("created_at")/1000000;

                            Long currentTme  = Calendar.getInstance().getTimeInMillis();

                            Long activeTime =  (currentTme - timestap);

                            System.out.println(activeTime+ "     timeStam   "+ timestap +" current time"+ currentTme);

                            databaseSwitcher.insertDriverlocationchanged((int) jsonObject.get("driver_id"), activeTime);
                        }


                        if(record.value().get("type").toString().equals("driver_login_status_changed")) {



                            JSONObject jsonObject= new JSONObject(record.value().get("body").toString());

                            databaseSwitcher.insertLoginStatus((int)jsonObject.get("id"),(String) jsonObject.get("status"));
                        }


                        if(record.value().get("type").toString().equals("trip_started")){



                            JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                            Long timestap = (Long) record.value().get("created_at");



                            databaseSwitcher.insertTripStart((int) jsonObject.get("driver_id"), timestap);
                        }

                        if(record.value().get("type").toString().equals("trip_ended")){


                            JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                            Long timestap = (Long) record.value().get("created_at");



                            databaseSwitcher.insertTripEnd((int) jsonObject.get("driver_id"), timestap);
                        }





                    }
                }

            } finally {
                consumer.close();

            }
        }




}
