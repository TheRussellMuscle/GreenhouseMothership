/**
 * Copyright (c) 2018 Brendan Russell BrendanLeeRussell72@gmail.com
 */

package com.brendanrussell.greenhousemothership;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This File Needs to be changed for expansion
 * 
 * @author Brendan Russell BrendanLeeRussell72@gmail.com 
 * @version 1.0
 */
public class JSONGenerator {

    private String json = "";

    /**
     * Constructor for JSONGenerator, takes an array of nodes
     * @param nodes an arrayList of BluetoothIterface Objects
     */
    public JSONGenerator(ArrayList<BluetoothInterface> nodes) {

        /**
         * HashMap<String, String> allSensors,
         * ArrayList<String> sensorIds, HashMap<String, String> dataMap
         */
        setJSON("{\r\n"
                + "	\"date\": \"");
        setJSON(getJSON() + getCurrentTime());
        setJSON(getJSON() + "\",\r\n"
                + "	\"sensors\": [");

        for (int i = 0; i < nodes.size(); i++) {

            String identification = nodes.get(i).getId();
            String sensorType = nodes.get(i).getType();
            String data = nodes.get(i).getData();

            setJSON(getJSON() + "{\r\n"
                    + "		\"id\": \"");
            setJSON(getJSON() + identification);
            setJSON(getJSON() + "\",\r\n"
                    + "		\"type\": \"");
            setJSON(getJSON() + sensorType);
            setJSON(getJSON() + "\",\r\n"
                    + "		\"value\": ");
            setJSON(getJSON() + data);
            setJSON(getJSON() + "\r\n"
                    + "	}, ");

        }
        setJSON(getJSON().substring(0, getJSON().length() - 2)); 
        // removes ", " from last sensor to make the array proper

        setJSON(getJSON() + "]\r\n"
                + "}");

    }

    /**
     * gets the current time
     * @return the current time and date in format "yyyy-MM-dd'T'HH:mm:ss-05:00"
     */
    private String getCurrentTime() {
        String currentTime;
        // Collects and sets the current Time
        DateTimeFormatter dtf = 
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss-05:00");
        LocalDateTime now = LocalDateTime.now();
        currentTime = dtf.format(now); // 2016/11/16 12:08:43
        return currentTime;
    }

    /**
     * ###################################################
     * code below this point is terrible should be changed asap
     */
    
    /**
     * Returns the current JSON string
     * @return the JSON string
     */
    private String getJSON() {
        return json;
    }

    /**
     * Sets the current JSON string
     * @param json the JSON string to be replace the old one
     */
    private void setJSON(String json) {
        this.json = json;
    }

    /**
     * Public access to JSON string
     * @return the current JSON string
     */
    public String getJSONAsString() {
        return json;
    }
}

