package com.pickme.drivertrack.consumers;


import com.pickme.config.Config;
import com.pickme.dbhelper.CassandraConnector;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;

public class DriverLocationChanged {


    private CassandraConnector cassandraConnector;
    private Properties properties;
    private  String topic;
    private String[] driver_location_changed_status_array;
    public DriverLocationChanged(Properties properties, String topic) {
        this.properties = properties;
        this.topic = topic;
        driver_location_changed_status_array = new String[2];
    }

    public void getdata() {


        cassandraConnector = new CassandraConnector(Config.ADDRESS, Config.PORT);
        final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true) {
                ConsumerRecords<String, GenericRecord> records = consumer.poll(10000);
                for (ConsumerRecord<String, GenericRecord> record : records) {


                    JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                    Long timestap = (Long) record.value().get("created_at")/1000000;

                    Long currentTme  = Calendar.getInstance().getTimeInMillis();

                    Long ActiveTime = (Long) (currentTme - timestap);

                    System.out.println(ActiveTime+ "     timeStam   "+ timestap +" current time"+ currentTme);



                    cassandraConnector.insertDriverlocationchanged((int) jsonObject.get("driver_id"), (Long)ActiveTime);


                }


            }
        } finally {
            consumer.close();

        }

    }




    }
