package com.pickme.dbhelper;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.Calendar;


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
    public ArrayList<String> selectDriver(int waitingTime){
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long validatingTime = currentTime - waitingTime*60*1000;
        ArrayList<String> driver_ids = new ArrayList<>();


        StringBuilder sb = new StringBuilder("SELECT driver_id from TrackDriverLive.Driverlive WHERE driverstatus='A' AND loginstatus='A' AND shiftstatus='I' AND last_heartbeat<=20 AND trip_start=0 AND vehicleassignstatus='A' AND directionalhire =0 AND trip_end<=")
        .append(validatingTime).append("allow filtering;");
        String query = sb.toString();
        ResultSet rs = session.execute(query);


        for(Row row : rs){
            driver_ids.add(""+row.getInt("driver_id"));
        }
        return driver_ids;

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

    @Override
    public void insertVehicleAssignStatus(int driver_id , String status){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id, vehicleassignstatus) ")
                .append("VALUES (").append(driver_id)
                .append(", '").append(status).append("');");

        String query = sb.toString();
        session.execute(query);
    }

    @Override
    public void insertDirectionalHire(int driver_id, int status) {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id, directionalhire) ")
                .append("VALUES (").append(driver_id)
                .append(", ").append(status).append(");");

        String query = sb.toString();
        session.execute(query);
    }
}

