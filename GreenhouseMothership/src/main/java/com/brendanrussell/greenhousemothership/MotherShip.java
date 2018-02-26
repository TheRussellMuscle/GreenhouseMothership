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

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * This program is the main runnable thread of the Greenhouse MotherShip.
 * MotherShip's task is to collect data from each of the nodes, format it into
 * JSON format, and send it to the server for storage and analytics.
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 * @version 1.0
 */
public class MotherShip implements Runnable {
    
    //Declare nodes here, nodes only require a string URL, instructions for 
    //creating a node URL will be included above their initialization below.
    private final String nodeOneURL;
    private final String nodeTwoURL;
    private final String nodeThreeURL;
    
    //Each Sensor should be declared here and be of type Sensor
    private final BluetoothInterface nodeOneTemp;
    private final BluetoothInterface nodeTwoTemp;
    private final BluetoothInterface nodeThreeTemp;

    /**
     * Initializes the nodes and sensors
     */
    public MotherShip() {
        //Node URLs are initialized
        //to create a Node URL copy
        //"btspp://<YOUR BT MAC>:1;authenticate=true;encrypt=true;master=false"
        //and replace <YOUR BT MAC> with the MAC address of your Bluetooth
        //module minus the dashes or spaces. Refrence the code below to double
        //check your work
        nodeOneURL = "btspp://000666D0E48B:1;authenticate=true;"
                + "encrypt=true;master=false";
        nodeTwoURL = "btspp://000666D0E8C4:1;authenticate=true;"
                + "encrypt=true;master=false";
        nodeThreeURL = "btspp://000666D0E433:1;authenticate=true;"
                + "encrypt=true;master=false";

        //Here sensors are initialized
        //To initialize a sensor include the URL of its Node, its full id,
        //and its type
        nodeOneTemp 
                = new BluetoothInterface(nodeOneURL, "a01-01", "temperature");
        nodeTwoTemp 
                = new BluetoothInterface(nodeTwoURL, "a02-01", "temperature");
        nodeThreeTemp 
                = new BluetoothInterface(nodeThreeURL, "a03-01", "temperature");

    }

    /**
     * Retrieves the data from nodeOne and returns it as a string array
     * @return All data from node one
     */
    private String nodeOneData() {
        String temperature = this.nodeOneTemp.getTemp();
        int counter = 0;
        while (temperature.equals("Connection Fail")) {
            counter++;
            if (counter == 6) {
                System.out.println("Node 1 Connection Fail, Moving On");
                break;
            }
            System.out.println("Node 1 Connection Fail, Trying Again");
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException ex) {
                System.out.println("Sleep fail");
            }
            temperature = this.nodeOneTemp.getTemp();

        }
        return temperature;
    }

    private String nodeTwoData() {
        String nodeTwoTemp = this.nodeTwoTemp.getTemp();
        int counter = 0;
        while (nodeTwoTemp.equals("Connection Fail")) {
            counter++;
            if (counter == 6) {
                System.out.println("Node 2 Connection Fail, Moving On");
                break;
            }
            System.out.println("Node 2 Connection Fail, Trying Again");
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException ex) {
                System.out.println("Sleep fail");
            }
            nodeTwoTemp = this.nodeTwoTemp.getTemp();
        }
        return nodeTwoTemp;
    }

    private String nodeThreeData() {
        String nodeThreeTemp = this.nodeThreeTemp.getTemp();
        int counter = 0;
        while (nodeThreeTemp.equals("Connection Fail")) {
            counter++;
            if (counter == 6) {
                System.out.println("Node 3 Connection Fail, Moving On");
                break;
            }
            System.out.println("Node 3 Connection Fail, Trying Again");
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException ex) {
                System.out.println("Sleep fail");
            }
            nodeThreeTemp = this.nodeThreeTemp.getTemp();
        }
        return nodeThreeTemp;
    }

    /**
     * Creates the data and returns json
     *
     * @return
     */
    private String formatData(ArrayList<BluetoothInterface> nodes) {

        JSONGenerator json = new JSONGenerator(nodes);

        String jsonString = json.getJSONAsString();

        return jsonString;

    }

    private void sendData(String jsonString) {
        System.out.println(jsonString);
        int timeout = 20;
        int CONNECTION_TIMEOUT_MS = timeout * 1000; // Timeout in millis.
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS)
                .setConnectTimeout(CONNECTION_TIMEOUT_MS)
                .setSocketTimeout(CONNECTION_TIMEOUT_MS)
                .build();

        HttpClient httpClient = HttpClientBuilder.create().build(); // Use this instead

        try {

            HttpPost request = new HttpPost("http://10.66.3.1:25561/sensorData");
            request.setConfig(requestConfig);
            StringEntity params = new StringEntity(jsonString);
            request.addHeader("sensor-data", "application/x-www-form-urlencoded");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.toString());

            // handle response here...
        } catch (Exception ex) {

            // handle exception here
            System.out.println("Server Connection Timeout");

        } finally {
            // do nothing
        }

    }

    @Override
    public void run() {
        //Display results
        String[] data = new String[3];
        data[0] = nodeOneData();
        data[1] = nodeTwoData();
        data[2] = nodeThreeData();

        ArrayList<BluetoothInterface> nodes = new ArrayList<>();
        int count = 0;
        for (String d : data) {
            if (!d.equals("Connection Fail") && count == 0) {
                nodes.add(nodeOneTemp);
            } else if (!d.equals("Connection Fail") && count == 1) {
                nodes.add(nodeTwoTemp);
            } else if (!d.equals("Connection Fail") && count == 2) {
                nodes.add(nodeThreeTemp);
            }
            count++;
        }

        sendData(formatData(nodes));
        //System.out.println(formatData(data));
    }

}
