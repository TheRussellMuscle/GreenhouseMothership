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
 * BluetoothInterface handles the connection to individual nodes
 *
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 * @version 1.0
 */
public class BluetoothInterface {

    private String NodeURL;
    private String data;

    /**
     * Creates a BluetoothInterface Object
     *
     * @param URL a string containing the Bluetooth URL necessary for connection
     */
    public BluetoothInterface(String URL) {
        this.NodeURL = URL;
    }

    /**
     * A public method for getting the data from a node
     *
     * @return returns a comma delineated string containing the node's data
     */
    public String getData() {
        try {
            this.data = go();
        } catch (Exception ex) {
            this.data = "Connection Fail";
        }
        return this.data;
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
        pWriter.write("ready\n\n"); //Super secret password
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
