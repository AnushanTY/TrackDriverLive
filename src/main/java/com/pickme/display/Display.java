package com.pickme.display;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.pickme.config.Config;
import com.pickme.dbhelper.DriverLive_Cassandra;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.event.*;

public class Display implements ActionListener{
    private JCheckBox check_driver_status;
    private JCheckBox check_login_status;
    private JCheckBox check_shift_status;
    private JCheckBox check_not_on_trip;
    private JCheckBox check_heartbeat;
    private JFrame jFrame;
    DefaultListModel<String> model;
    private JPanel jPanel;
    private JLabel jlable;
    private JTextArea driver_text;
    private JButton button1;
    private String[] query;
    private Boolean[] selected;
    private DriverLive_Cassandra db;
    private ResultSet rs; //query results
    private Config config;

    public Display() {

        config=new Config();
        db= new DriverLive_Cassandra((String) config.getProp().getProperty("ADDRESS"),Integer.parseInt(config.getProp().getProperty("PORT")));



        query= new String[]{"driverstatus='A'", "loginstatus='A'", "shiftstatus='I'", "last_heartbeat<=20", "trip_start='no'"};
        selected= new Boolean[]{false, false, false, false, false};

        check_shift_status.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                  selected[2]=true;
                  process(selected);

                }

                if(e.getStateChange() == ItemEvent.DESELECTED){
                    selected[2]=false;
                    process(selected);
                }

            }
        });



        check_login_status.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    selected[1]=true;
                    process(selected);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED){
                    selected[1]=false;
                    process(selected);
                }
            }

        });


        check_driver_status.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    selected[0]=true;
                    process(selected);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED){
                    selected[0]=false;
                    process(selected);
                }
            }
        });


        check_not_on_trip.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    selected[4]=true;
                    process(selected);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED){
                    selected[4]=false;
                    process(selected);
                }
            }
        });


        check_heartbeat.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    selected[3]=true;
                    process(selected);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED){
                    selected[3]=false;
                    process(selected);
                }
            }
        });






    }

    private void process(Boolean[] selected) {
        String sql="select driver_id from TrackDriverLive.Driverlive where ";

        for (int i=0;i<selected.length;i++){
            if(selected[i]){


                    sql+=query[i]+"  AND ";

                  /*  if(i==4){
                        db.getSession().execute("SELECT trip_end from TrackDriverLive.Driverlive WHERE "+query[i]);
                    }
*/
            }

        }
        sql=sql.substring(0,sql.length()-5)+" allow filtering";

        driver_text.setText("List of Drivers (driver_id): \n");

        rs = db.getSession().execute(sql);
        int count = 1;
        for(Row row : rs){


            int driver_id = row.getInt("driver_id");
            driver_text.setText(driver_text.getText()+"\n"+ count+". "+driver_id+"");
            count++;

        }

    }


    public void run(){
        jFrame = new JFrame("Driver Tracker");
        jFrame.setContentPane(new Display().jPanel);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(1600,1600);
        jFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
      if(check_shift_status.isSelected()){
          driver_text.setText("Shift Selected");
          System.out.println("Shift");
      }
      else if(!check_shift_status.isSelected()){
          driver_text.setText(null);
      }


    }
}

