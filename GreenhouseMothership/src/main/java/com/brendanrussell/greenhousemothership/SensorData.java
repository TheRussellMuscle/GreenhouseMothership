/*
 * Copyright (c) 2018 Brendan Russell BrendanLeeRussell72@gmail.com
 */
package com.brendanrussell.greenhousemothership;

/**
 *
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 */
public class SensorData {
    private String timestamp;
    private String data;
    
    public SensorData(String timestamp, String data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }
    
}
