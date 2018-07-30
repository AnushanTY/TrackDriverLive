
package com.pickme.local;

import it.unimi.dsi.fastutil.Hash;

import java.util.ArrayList;
import java.util.HashMap;

public class LocalStorage {
    private static ArrayList<Integer> loginArray;
    private static ArrayList<Integer>  shiftArray;
    private static ArrayList<Integer>  driverArray;
    private static ArrayList<Integer> lastHeartbeatArray;

    public LocalStorage(){
        loginArray = new ArrayList<>();
        shiftArray = new ArrayList<>();
        driverArray = new ArrayList<>();
        lastHeartbeatArray = new ArrayList<>();
    }

    public static void insertLogin(int driver_id){
        loginArray.add(driver_id);
    }

    public static void insertShift(int driver_id,String status){
        shiftArray.add(driver_id);
    }

    public static void insertDriver(int driver_id,String status){
        driverArray.add(driver_id);
    }
    public static void insertLastHeartBeat(int driver_id){
        lastHeartbeatArray.add(driver_id);
    }

}