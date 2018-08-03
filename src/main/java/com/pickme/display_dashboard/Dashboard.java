package com.pickme.display_dashboard;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.pickme.config.Config;
import com.pickme.dbhelper.DriverLive_Cassandra;
import com.pickme.display.Display;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Dashboard {
    private JTextArea dashboard;
    private JPanel jpanel;
    private JTextField time_text;
    private JButton apply_show;
    private int waiting_time;   //time in minutes
    private DriverLive_Cassandra driverLive_cassandra;
    private ResultSet rs;
    private JFrame jFrame;




    public Dashboard() {
        driverLive_cassandra = new DriverLive_Cassandra(Config.ADDRESS,Config.PORT);

        apply_show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    waiting_time = Integer.parseInt(time_text.getText());

                    if(time_text.getText() != null  && time_text.getText().matches("[0-9]+") ) {

                        query(waiting_time);

                    }
                    else{
                        JOptionPane.showMessageDialog(null,  "Sorry, invalid values", "InfoBox: " + "Invalid", JOptionPane.INFORMATION_MESSAGE);
                    }

            }
        });
    }

    public void run(){

        jFrame = new JFrame("Driver Tracker");
        jFrame.setContentPane(new Dashboard().jpanel);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(1600,1600);
        jFrame.setVisible(true);

    }


    private void query(int waiting_time){
        dashboard.setText("List of the drivers waiting more than "+waiting_time);

        String query = "SELECT driver_id,trip_end from TrackDriverLive.Driverlive WHERE driverstatus='A' AND loginstatus='A' AND shiftstatus='I' AND last_heartbeat<=20 AND trip_start=0 allow filtering";
        rs = driverLive_cassandra.getSession().execute(query);

        for(Row row : rs){
            if(check_eligible(row.getLong("trip_end"),waiting_time)){
                dashboard.setText(dashboard.getText() + "/n" + row.getInt("driver_id"));
            }
        }


    }

    private boolean check_eligible(long trip_end,int waiting_time){
        boolean return_element = false;
        long current_time =  Calendar.getInstance().getTimeInMillis( );
        if(trip_end-current_time>=waiting_time*60*1000){
            return_element = true;
        }
        return return_element;
    }


}
