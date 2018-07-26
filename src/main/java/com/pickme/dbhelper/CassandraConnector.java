package com.pickme.dbhelper;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;


public class CassandraConnector {
    private Cluster cluster;
    private Session session;
    private static final String TABLE_NAME = "drivers_waiting_time";
    private String KEY_SPACE;

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

   /* public void createKeyspace(String keyspacename , String replicationStratergy, int replicationFactor){
        //to run the query we should create a string builder and then can execute that
        StringBuilder stringBuilder = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
                .append(keyspacename).append(" WITH REPLICATION = {'class':'")
                .append(replicationStratergy)
                .append("','replication_factor':").append(replicationFactor)
                .append("};");
        this.KEY_SPACE = keyspacename;

        String query = stringBuilder.toString();
        session.execute(query);
    }*/

   /* public void createTable(){
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(KEY_SPACE).append(".")
                .append(TABLE_NAME).append("(")
                .append("driver_id int PRIMARY KEY, ")
                .append("last_time text);" );
        String query = stringBuilder.toString();
        session.execute(query);
    }*/

   
    public void insertShiftStatus(int driver_id , String status){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id,shiftstatus ) ")
                .append("VALUES (").append(driver_id)
                .append(", '").append(status).append("');");

        String query = sb.toString();
        session.execute(query);
    }
    public void insertLoginStatus(int driver_id , String status){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id,shiftstatus ) ")
                .append("VALUES (").append(driver_id)
                .append(", '").append(status).append("');");

        String query = sb.toString();
        session.execute(query);
    }
    public void insertDriverStatus(int driver_id , String status){
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("TrackDriverLive")
                .append(".").append("Driverlive").append("(driver_id,shiftstatus ) ")
                .append("VALUES (").append(driver_id)
                .append(", '").append(status).append("');");

        String query = sb.toString();
        session.execute(query);
    }





}

