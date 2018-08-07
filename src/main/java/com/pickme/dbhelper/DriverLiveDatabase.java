
package com.pickme.dbhelper;

import java.util.ArrayList;

public abstract class DriverLiveDatabase {
    public abstract void insertShiftStatus(int driver_id , String status);
    public abstract void insertLoginStatus(int driver_id , String status);
    public abstract void insertDriverStatus(int driver_id , String status);
    public abstract void insertDriverlocationchanged(int driver_id, long time);
    public abstract void insertTripStart(int driver_id , long trip_start);
    public abstract void insertTripEnd(int driver_id , long trip_end);
    public abstract void insertVehicleAssignStatus(int driver_id ,  String status);
    public abstract ArrayList<String> selectDriver(int waitingTime);


}