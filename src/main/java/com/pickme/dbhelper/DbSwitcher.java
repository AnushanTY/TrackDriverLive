package com.pickme.dbhelper;

public class DbSwitcher {
    private CassandraConnector cassandraConnector;
    private String DB_NAME = null;


    public DbSwitcher(){
        cassandraConnector = new CassandraConnector();
    }
    public void select_db(String db_name){
        this.DB_NAME = db_name;

    }
    public void insert(int driver_id, long last_time){
        if(DB_NAME == null){
            System.out.println("Please select database");
        }
        if(DB_NAME == "CASS"){
            cassandra_connection(driver_id,last_time);
        }

    }
    private void cassandra_connection(int driver_id,long last_time){
        cassandraConnector.connect("127.0.0.1",9042);
        cassandraConnector.createKeyspace("DriveSpace","SimpleStrategy",3);
        cassandraConnector.createTable();
        cassandraConnector.insertData(driver_id,last_time);


    }
    public void close(){
        cassandraConnector.close();
    }
}
