/**
 * Copyright (c) 2018 Brendan Russell BrendanLeeRussell72@gmail.com
 */
package com.brendanrussell.greenhousemothership;

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
 *
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 * @version 1.0
 */
public class MotherShip implements Runnable {

    ArrayList<Node> nodes;
    ArrayList<Node> data;
    
    /**
     * Declare node URLs here, instructions for
     * creating a node URL will be included above their initialization below.
     */
    private final String nodeOneBTURL;
    private final String nodeTwoBTURL;
    private final String nodeThreeBTURL;

    /**
     * Initializes the nodes and sensors
     */
    public MotherShip() {
        /**
         * Node URLs are initialized to create a Node URL copy:
         * "btspp://<YOUR BT MAC>:1;authenticate=true;encrypt=true;master=false"
         * and replace <YOUR BT MAC> with the MAC address of your Bluetooth
         * module minus the dashes or spaces Refrence the code below to double
         * check your work
         */
        nodeOneBTURL = "btspp://000666D0E48B:1;authenticate=true;"
                + "encrypt=true;master=false";
        nodeTwoBTURL = "btspp://000666D0E8C4:1;authenticate=true;"
                + "encrypt=true;master=false";
        nodeThreeBTURL = "btspp://000666D0E433:1;authenticate=true;"
                + "encrypt=true;master=false";

        /**
         * Here Nodes are initialized and sensors are added. To initialize a 
         * Node include the URL. To add a sensor, name its type.
         * 
         * Note: sensors will show up in the order added and be matched to 
         * data in that same order.
         */
        nodes = new ArrayList<>();
        addNode(nodeOneBTURL);
        addNode(nodeTwoBTURL);
        addNode(nodeThreeBTURL);
        
        //Add a temperature sensor to each node
        nodes.forEach((n) -> {
            n.addSensor("temperature");
        });
        
        data = new ArrayList<>();
        
    }
    
    private void addNode(String URL) {
        int nodeNum = this.nodes.size() + 1;
        String id;
        if (nodeNum < 10) {
            id = "a0" + nodeNum;
        } else {
            id = "a" + nodeNum;
        }
        nodes.add(new Node(id, URL));
    }

    /**
     * Creates the data and returns a json string
     *
     * @return a json formatted string ready to be sent to the server
     */
    private String formatData(Node[] nodes) {

        JSONGenerator json = new JSONGenerator(nodes);

        String jsonString = json.getJSONAsString();

        return jsonString;

    }

    /**
     * Sends data to the server
     *
     * @param jsonString a string formatted and ready to be sent to the server
     */
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

    /**
     * The main loop of the program Gathers data from nodes, makes sure data is
     * valid, calls methods to send data to server
     */
    @Override
    public void run() {
        nodes.forEach((n) -> {
            n.update();
        });
        
        data.clear();
        
        nodes.forEach((n) -> {
            if (n.isDataAvailable()) {
                data.add(n);
                n.resetAllSensors();
            }
        });

        sendData(formatData((Node[])data.toArray()));
    }

}
