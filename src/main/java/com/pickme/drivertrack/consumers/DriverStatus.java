package com.pickme.drivertrack.consumers;

import com.datastax.driver.core.Session;
import com.pickme.dbhelper.CassandraConnector;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Properties;

public class DriverStatus {
    private CassandraConnector cassandraConnector;
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
            cassandraConnector= new CassandraConnector("127.0.0.1",9042);

            final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(Arrays.asList(topic));
            try {
                while (true) {
                    ConsumerRecords<String, GenericRecord> records = consumer.poll(100000);
                    for (ConsumerRecord<String, GenericRecord> record : records) {

                        JSONObject jsonObject= new JSONObject(record.value().get("body").toString());
                        cassandraConnector.insertDriverStatus((int)jsonObject.get("id"),(String) jsonObject.get("status"));




                    }



                }

            } finally {
                consumer.close();

            }
        }




}
