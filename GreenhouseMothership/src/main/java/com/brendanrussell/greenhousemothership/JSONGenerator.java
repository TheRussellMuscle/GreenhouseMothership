/**
 * Copyright (c) 2018 Brendan Russell BrendanLeeRussell72@gmail.com
 */
package com.brendanrussell.greenhousemothership;

/**
 * This File Needs to be changed for expansion
 *
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 * @version 1.0
 */
public class JSONGenerator {

    private String json = "";
    private String nl = System.lineSeparator();

    /**
     * Constructor for JSONGenerator, takes an array of nodes
     *
     * @param nodes an arrayList of BluetoothIterface Objects
     */
    public JSONGenerator(Node[] nodes) {
        setJSON("{" + nl
                + "	\"data\": [");

        for (Node node : nodes) {
            setJSON(getJSON() + node.getData());
        }
        
        // removes ", " from last sensor to make the array proper
        setJSON(getJSON().substring(0, getJSON().length() - 2));
        
        setJSON(getJSON() + "]" + nl
                + "}");

    }

    /**
     * Returns the current JSON string
     *
     * @return the JSON string
     */
    private String getJSON() {
        return json;
    }

    /**
     * Sets the current JSON string
     *
     * @param json the JSON string to replace the old one
     */
    private void setJSON(String json) {
        this.json = json;
    }

    /**
     * Public access to JSON string
     *
     * @return the current JSON string
     */
    public String getJSONAsString() {
        return json;
    }
}
