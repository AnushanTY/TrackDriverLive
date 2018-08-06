package com.pickme.process;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.pickme.dbhelper.DatabaseSwitcher;

import java.util.ArrayList;
import java.util.Calendar;

public class Calculation{

    ArrayList<String> driver_ids;


    public boolean check_eligible(long trip_end,int waiting_time){
        boolean return_element = false;
        long current_time =  Calendar.getInstance().getTimeInMillis( );
        if(trip_end-current_time>=waiting_time*60*1000){
            return_element = true;
        }
        return return_element;
    }

    public ArrayList<String> processDriverID(int waiting_time , DatabaseSwitcher dbswitcher){

        driver_ids = new ArrayList<>();
        if(dbswitcher.getDATABASEMODE() == 1) {
            ResultSet rs = (ResultSet) dbswitcher.selectDriver();
            for(Row row : rs){
                if(check_eligible(row.getLong("trip_end"),waiting_time)){
                   // dashboard.setText(dashboard.getText() + "/n" + row.getInt("driver_id"));
                    driver_ids.add(""+row.getInt("driver_id"));

                }
            }
        }

        return driver_ids;

    }


}
