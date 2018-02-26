package com.brendanrussell.greenhousemothership;

/*
 * The MIT License
 *
 * Copyright 2018 Brendan Russell BrendanLeeRussell72@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 */
public class JSONGenerator {

    private String xml = "";

    public JSONGenerator(ArrayList<BluetoothInterface> nodes) {

        //HashMap<String, String> allSensors,
        //ArrayList<String> sensorIds, HashMap<String, String> dataMap
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
        setJSON(getJSON().substring(0, getJSON().length() - 2)); // removes ", " from last sensor to make the array proper

        setJSON(getJSON() + "]\r\n"
                + "}");

    }

    private String getCurrentTime() {
        String currentTime;
        // Collects and sets the current Time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss-05:00");
        LocalDateTime now = LocalDateTime.now();
        currentTime = dtf.format(now); // 2016/11/16 12:08:43
        return currentTime;
    }

    private String getJSON() {
        return xml;
    }

    private void setJSON(String xml) {
        this.xml = xml;
    }

    /**
     * getting the xml string
     *
     * @return
     */
    public String getJSONAsString() {
        return xml;
    }
}

