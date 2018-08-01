package com.pickme.dbhelper;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class TripLive_Cassandra {
    private Cluster cluster;
    private Session session;


    public TripLive_Cassandra(String node, int port){
        connect(node,port);
    }

    public void connect(String node , Integer port){
       Cluster.Builder builder =  Cluster.builder().addContactPoint(node);
       if(builder != null){
           builder.withPort(port);
       }
       cluster = builder.build();
       session = cluster.connect();
    }

    public Session getSession(){
        return this.session;
    }

    public void close(){    //closing server
        session.close();
        cluster.close();
    }

    public void insert_trip_start(int driver_id , long trip_start){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Triplive").append("(driver_id,trip_start,trip_end ) ")
                .append("VALUES (").append(driver_id)
                .append(", ").append(trip_start).append(",")
                .append((String) null).append(");");

        String query = sb.toString();
        session.execute(query);
    }

    public void insert_trip_end(int driver_id , long trip_end){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Triplive").append("(driver_id,trip_start,trip_end ) ")
                .append("VALUES (").append(driver_id)
                .append(",").append((String)null)
                .append(", ").append(trip_end).append(");");

        String query = sb.toString();
        session.execute(query);
    }



}
