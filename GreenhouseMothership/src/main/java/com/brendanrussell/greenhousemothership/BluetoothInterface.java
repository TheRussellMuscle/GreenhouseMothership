/**
 * Copyright (c) 2018 Brendan Russell BrendanLeeRussell72@gmail.com
 */
package com.brendanrussell.greenhousemothership;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 */
public class BluetoothInterface {

    private String NodeURL;
    private String id;
    private String type;
    private String data;

    /**
     * Creates a BluetoothInterface Object
     *
     * @param URL a string containing the Bluetooth URL necessary for connection
     * @param id a String containing the ID of the sensor
     * @param type a String containing the type of the sensor
     */
    public BluetoothInterface(String URL, String id, String type) {
        NodeURL = URL;
        this.id = id;
        this.type = type;

    }

    /**
     * A public method for getting the temperature from a sensor
     *
     * @return returns a string containing the temperature
     */
    public String getTemp() {
        String temp;
        try {
            temp = go();
        } catch (Exception ex) {
            temp = "Connection Fail";
        }
        data = temp;
        return temp;
    }

    /**
     * Public method for retrieving the ID string
     *
     * @return the ID string
     */
    public String getId() {
        return id;
    }

    /**
     * Public method for retrieving the Type string
     *
     * @return the Type string
     */
    public String getType() {
        return type;
    }

    /**
     * Public method for retrieving the Data string
     *
     * @return the Data string
     */
    public String getData() {
        return data;
    }

    /**
     * Method connects to node over Bluetooth and retrieves its data
     *
     * @return the data from the node
     * @throws Exception
     */
    private String go() throws Exception {
        StreamConnection streamConnection = (StreamConnection) Connector.open(NodeURL);
        OutputStream os = streamConnection.openOutputStream();
        InputStream is = streamConnection.openInputStream();

        PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(os));
        pWriter.write("ready\n\n");
        pWriter.flush();
        pWriter.close();
        Thread.sleep(200);

        BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
        String lineRead = bReader.readLine();
        //System.out.println(lineRead);
        bReader.close();
        is.close();
        os.close();
        streamConnection.close();
        return lineRead;

    }
}
