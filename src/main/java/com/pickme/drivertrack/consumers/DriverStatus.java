package com.pickme.drivertrack.consumers;

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

public class DriverStatus {
        private Session session;
        private Properties properties;
        private  String topic;
        private String[] status_array;

        public DriverStatus(Properties properties, String topic) {
            status_array = new String[2];
            this.properties = properties;
            this.topic = topic;
        }

        public void getdata(){

            ConnectCassandra client = new ConnectCassandra();
            client.connect("127.0.0.1", 9042);
            session = client.getSession();
            final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(Arrays.asList(topic));
            try {
                while (true) {
                    ConsumerRecords<String, GenericRecord> records = consumer.poll(100000);
                    for (ConsumerRecord<String, GenericRecord> record : records) {

                        JSONObject jsonObject= new JSONObject(record.value().get("body").toString());


                        StringBuilder sb = new StringBuilder("INSERT INTO ")
                                .append("TrackDriverLive")
                                .append(".").append("Driverlive").append("(driver_id, driverstatus) ")
                                .append("VALUES (").append(jsonObject.get("id"))
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
