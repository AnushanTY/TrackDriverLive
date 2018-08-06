package com.pickme.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    String file = "/home/pickme-1031/IdeaProjects/TrackDriverLive/src/prop.properties";
    Properties prop = new Properties();
    InputStream input = null;

    public Config(){
        load();
    }

    private void load(){
        try {

            input = new FileInputStream(file);

            // load a properties file
            prop.load(input);


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Properties getProp(){
        return prop;
    }

}
