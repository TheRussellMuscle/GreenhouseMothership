/**
 * Copyright (c) 2018 Brendan Russell BrendanLeeRussell72@gmail.com
 */
package com.brendanrussell.greenhousemothership;

import java.util.ArrayList;

/**
 * Sensor class
 *
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 * @version 1.0
 */
public class Sensor {
    private String id;
    private String type;
    private ArrayList<SensorData> data;
    
    
    public Sensor(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public SensorData[] getData() {
        return (SensorData[]) data.toArray();
    }
    
    public void resetData() {
        this.data.clear();
    }
    
    public void setData(String data) {
        String[] sarr = data.split(",");
        int count = 0;
        String timestamp = "";
        String dataString;
        for(String s : sarr) {
           if (count == 0) {
               timestamp = s;
               count++;
           } else if (count == 1) {
               dataString = s;
               this.data.add(new SensorData(timestamp, dataString));
               count = 0;
           }
        }
    }
    
}
