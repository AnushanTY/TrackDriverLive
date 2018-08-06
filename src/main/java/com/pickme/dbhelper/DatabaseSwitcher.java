package com.pickme.dbhelper;

import com.pickme.config.Config;

public class DatabaseSwitcher {

   DriverLiveDatabase driverLiveDatabase;
   int DATABASEMODE = 0;
   Config config;
   public DatabaseSwitcher(String database)
   {
       config = new Config();
       selectDatabase(database);
   }

    public int getDATABASEMODE() {
        return DATABASEMODE;
    }

    public void selectDatabase(String database){
        if(database=="CASSANDRA"){
            driverLiveDatabase = new DriverLiveCassandra((String) config.getProp().getProperty("ADDRESS"),Integer.parseInt(config.getProp().getProperty("PORT")));
            DATABASEMODE = 1;
        }
    }

    public void insertDriverStatus(int driver_id, String status){
        driverLiveDatabase.insertDriverStatus(driver_id,status);
    }

    public void insertDriverlocationchanged(int driver_id,long time){
       driverLiveDatabase.insertDriverlocationchanged(driver_id,time);
    }

    public Object selectDriver(){
       com.datastax.driver.core.ResultSet object = null;
       if(DATABASEMODE ==1){
           object = (com.datastax.driver.core.ResultSet)driverLiveDatabase.selectDriver();
       }
       return object;
    }



    public void insertShiftStatus(int driver_id, String status){
        driverLiveDatabase.insertShiftStatus(driver_id,status);
    }

    public void insertLoginStatus(int driver_id, String status){
        driverLiveDatabase.insertLoginStatus(driver_id,status);
    }

    public void insertTripStart(int driver_id, long time){
        driverLiveDatabase.insertTripStart(driver_id,time);
    }


    public void insertTripEnd(int driver_id, long time){
        driverLiveDatabase.insertTripEnd(driver_id,time);
    }





}
