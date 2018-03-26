/*
 * Copyright (c) 2018 Brendan Russell BrendanLeeRussell72@gmail.com
 */
package com.brendanrussell.greenhousemothership;

import java.util.ArrayList;

/**
 *
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 */
public class Node {

    private String btURL;
    private String nodeId;
    private BluetoothInterface btInterface;
    private ArrayList<Sensor> sensors;
    private boolean dataAvailable;

    public Node(String ID, String URL) {
        this.btURL = URL;
        this.nodeId = ID;
        this.btInterface = new BluetoothInterface(this.btURL);
        this.sensors = new ArrayList<>();
        dataAvailable = false;
    }

    public boolean isDataAvailable() {
        return dataAvailable;
    }

    public String getNodeId() {
        return nodeId;
    }

    public boolean addSensor(String type) {
        int sensorNum = this.sensors.size() + 1;
        String id = getNodeId() + "-" + sensorNum;
        this.sensors.add(new Sensor(id, type));
        return true;
    }

    public boolean update() {
        int counter = 0;
        String tmpData = btInterface.getData();
        while (tmpData.equals("Connection Fail")) {
            counter++;
            if (counter == 6) {
                System.out.println("Node " + getNodeId()
                        + " Connection Fail, Moving On");
                return false;
            }
            System.out.println("Node " + getNodeId()
                    + " Connection Fail, Trying Again");
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                System.out.println("Sleep fail");
            }
            tmpData = btInterface.getData();
        }

        String dataArr[] = tmpData.split(";");
        int updateNum = this.sensors.size();
        if (this.sensors.size() != dataArr.length) {
            System.out.println("Node " + getNodeId()
                    + " Detecting " + dataArr.length + " Sensors, "
                    + "Only " + this.sensors.size() + " Registered!");
            if (dataArr.length < this.sensors.size()) {
                updateNum = dataArr.length;
            }
        }

        for (int i = 0; i < updateNum; i++) {
            this.sensors.get(i).setData(dataArr[i]);
        }
        
        dataAvailable = true;

        return true;

    }

    public boolean resetAllSensors() {
        this.sensors.forEach((s) -> {
            s.resetData();
        });
        
        dataAvailable = false;
        return true;
    }

    public String getData() {
        String nl = System.lineSeparator();
        String data = "";
        for (Sensor sensor : this.sensors) {
            for (SensorData sd : sensor.getData()) {
                String identification = sensor.getId();
                String sensorType = sensor.getType();
                data = data + "{" + nl
                        + "		\"id\": \"";
                data = data + identification;
                data = data + "\"," + nl
                        + "		\"date\": \"";
                data = data + sd.getTimestamp();
                data = data + "\"," + nl
                        + "		\"type\": \"";
                data = data + sensorType;
                data = data + "\"," + nl
                        + "		\"value\": ";
                data = data + sd.getData();
                data = data + nl
                        + "	}, ";
            }
        }
        return data;
    }

}
