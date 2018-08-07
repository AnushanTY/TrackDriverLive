package com.pickme.display_dashboard;

import com.datastax.driver.core.ResultSet;
import com.pickme.config.Config;
import com.pickme.dbhelper.DatabaseSwitcher;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class Dashboard {
    private JTextArea dashboard;
    private JPanel jpanel;
    private JTextField time_text;
    private JButton apply_show;
    private int waiting_time;   //time in minutes
    private DatabaseSwitcher databaseSwitcher;
    private ResultSet rs;
    private JFrame jFrame;;
    private Config config;



    public Dashboard() {

        config=new Config();
        databaseSwitcher= new DatabaseSwitcher("CASSANDRA");

        apply_show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    waiting_time = Integer.parseInt(time_text.getText());

                    if(time_text.getText() != null  && time_text.getText().matches("[0-9]+") ) {

                        showing_output(waiting_time);

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


    private void showing_output(int waiting_time){

        dashboard.setText("List of the drivers waiting more than "+waiting_time+" minutes");
        ArrayList<String> resultList=databaseSwitcher.selectDriver(waiting_time);
        for (String s:resultList){

            dashboard.setText(dashboard.getText() + "/n" + s);
        }

    }



}
