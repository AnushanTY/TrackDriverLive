package com.pickme.drivertrack.consumers;

import com.datastax.driver.core.Session;
import com.pickme.config.Config;
import com.pickme.dbhelper.DriverLiveDatabase;
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
    private DriverLiveDatabase driverLiveDatabase1, driverLiveDatabase2;

    private Config config;
    private Session session;
    private Properties properties;
    private  String topicLogin;
    private String topicShift;
    private String topicDriver;
    private String topicDriverLocationChanged;
    private String topicTrip;
    private String topicVehicleAssign;
    private  String topicDHstatus;
    private String[] status_array;

    public DriverAndTripConsumer(DriverLiveDatabase driverLiveDatabase1,DriverLiveDatabase driverLiveDatabaseh2 ,Config config, Properties properties, String topicLogin, String topicShift, String topicDriver, String topicDriverLocationChanged, String topicTrip, String topicVehicleAssign, String topicDHstatus) {
        status_array = new String[2];
        this.properties = properties;
        this.driverLiveDatabase1 = driverLiveDatabase1;
        this.driverLiveDatabase2=driverLiveDatabaseh2;
        this.config = config;
        this.topicDriver=topicDriver;
        this.topicDriverLocationChanged=topicDriverLocationChanged;
        this.topicLogin=topicLogin;
        this.topicShift=topicShift;
        this.topicTrip=topicTrip;
        this.topicDHstatus=topicDHstatus;
        this.topicVehicleAssign=topicVehicleAssign;

    }

    public void getdata(){


        final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicLogin,topicDriver,topicShift,topicDriverLocationChanged,topicTrip,topicDHstatus,topicVehicleAssign));
        try {
            while (true) {
                ConsumerRecords<String, GenericRecord> records = consumer.poll(100000);
                for (ConsumerRecord<String, GenericRecord> record : records) {
                    System.out.println(record.value().get("type"));

                    if (record.value().get("type").toString().equals("driver_status_changed")) {



                        JSONObject jsonObject = new JSONObject(record.value().get("body").toString());
                        driverLiveDatabase1.insertDriverStatus((int) jsonObject.get("id"), (String) jsonObject.get("status"));



                    }

                    if(record.value().get("type").toString().equals("driver_shift_status_changed")) {



                        JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                        driverLiveDatabase1.insertShiftStatus((int) jsonObject.get("driver_id"), (String) jsonObject.get("status"));


                    }if(record.value().get("type").toString().equals("driver_location_changed")) {


                        JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                        Long timestap = (Long) record.value().get("created_at")/1000000;

                        Long currentTme  = Calendar.getInstance().getTimeInMillis();

                        Long activeTime =  (currentTme - timestap);

                        System.out.println(activeTime+ "     timeStam   "+ timestap +" current time"+ currentTme);

                        driverLiveDatabase1.insertDriverlocationchanged((int) jsonObject.get("driver_id"), activeTime);
                    }


                    if(record.value().get("type").toString().equals("driver_login_status_changed")) {



                        JSONObject jsonObject= new JSONObject(record.value().get("body").toString());

                        driverLiveDatabase1.insertLoginStatus((int)jsonObject.get("id"),(String) jsonObject.get("status"));
                    }


                    if(record.value().get("type").toString().equals("trip_started")){



                        JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                        Long timestap = (Long) record.value().get("created_at");



                        driverLiveDatabase1.insertTripStart((int) jsonObject.get("driver_id"), timestap);
                    }

                    if(record.value().get("type").toString().equals("trip_ended")){


                        JSONObject jsonObject = new JSONObject(record.value().get("body").toString());

                        Long timestap = (Long) record.value().get("created_at");



                        driverLiveDatabase1.insertTripEnd((int) jsonObject.get("driver_id"), timestap);
                    }

                    if(record.value().get("type").toString().equals("driver_vehicle_assigned")){


                        JSONObject jsonObject = new JSONObject(record.value().get("body").toString());




                        driverLiveDatabase1.insertVehicleAssignStatus((int) jsonObject.get("driver_id"), ( String)jsonObject.get("status"));
                    }

                    if(record.value().get("type").toString().equals("driver_dh_status_changed")){


                        JSONObject jsonObject = new JSONObject(record.value().get("body").toString());




                        driverLiveDatabase1.insertDirectionalHire((int) jsonObject.get("driver_id"), ( int)jsonObject.get("status"));
                    }





                }
            }

        } finally {
            consumer.close();

        }
    }




}
