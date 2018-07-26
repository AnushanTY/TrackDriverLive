package com.pickme.drivertrack.consumers;


import com.pickme.dbhelper.CassandraConnector;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Properties;

public class DriverLoginStatus {
    private CassandraConnector cassandraConnector;
    private Properties properties;
    private  String topic;
    private String[] login_status_array;
    public DriverLoginStatus(Properties properties, String topic) {
        this.properties = properties;
        this.topic = topic;
        login_status_array = new String[2];
    }

    public void getdata(){


        cassandraConnector= new CassandraConnector("127.0.0.1",9042);
        final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true) {
                ConsumerRecords<String, GenericRecord> records = consumer.poll(10000);
                for (ConsumerRecord<String, GenericRecord> record : records) {


                    JSONObject jsonObject= new JSONObject(record.value().get("body").toString());

                    cassandraConnector.insertLoginStatus((int)jsonObject.get("id"),(String) jsonObject.get("status"));


                }



            }
        } finally {
            consumer.close();

        }






    }



}