package com.pickme.dbhelper;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;


public class DriverLiveCassandra extends DriverLiveDatabase {
    private Cluster cluster;
    private Session session;

    public DriverLiveCassandra(String node, int port){
        connect(node,port);
    }

    public void connect(String node, Integer port){    //connection cassandra server with a node and a port
        Cluster.Builder builder = Cluster.builder().addContactPoint(node);
        if(port != null){
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

    @Override
    public ResultSet selectDriver(){
        String query = "SELECT driver_id,trip_end from TrackDriverLive.Driverlive WHERE driverstatus='A' AND loginstatus='A' AND shiftstatus='I' AND last_heartbeat<=20 AND trip_start=0 allow filtering";
        return session.execute(query);
    }

   @Override
    public void insertShiftStatus(int driver_id , String status){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id,shiftstatus ) ")
                .append("VALUES (").append(driver_id)
                .append(", '").append(status).append("');");

        String query = sb.toString();
        session.execute(query);
    }
    @Override
    public void insertLoginStatus(int driver_id , String status){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id,loginstatus ) ")
                .append("VALUES (").append(driver_id)
                .append(", '").append(status).append("');");

        String query = sb.toString();
        session.execute(query);
    }
    @Override
    public void insertDriverStatus(int driver_id , String status){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id,driverstatus ) ")
                .append("VALUES (").append(driver_id)
                .append(", '").append(status).append("');");

        String query = sb.toString();
        session.execute(query);
    }

    @Override
    public  void insertDriverlocationchanged(int driver_id, long time){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id,last_heartbeat ) ")
                .append("VALUES (").append(driver_id)
                .append(", ").append(time).append(");");

        String query = sb.toString();
        session.execute(query);
    }



    @Override
    public void insertTripStart(int driver_id , long trip_start){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id,trip_start,trip_end ) ")
                .append("VALUES (").append(driver_id)
                .append(", ").append(trip_start).append(",")
                .append(0).append(");");

        String query = sb.toString();
        session.execute(query);
    }

    @Override
    public void insertTripEnd(int driver_id , long trip_end){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id,trip_start,trip_end ) ")
                .append("VALUES (").append(driver_id)
                .append(",").append(0)
                .append(", ").append(trip_end).append(");");

        String query = sb.toString();
        session.execute(query);
    }





}

