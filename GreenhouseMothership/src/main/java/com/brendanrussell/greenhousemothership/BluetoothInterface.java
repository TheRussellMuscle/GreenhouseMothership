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
     *
     * @param URL
     */
    public BluetoothInterface(String URL, String id, String type) {
        NodeURL = URL;
        this.id = id;
        this.type = type;
        

    }
    
    public String getTemp() {
        String temp;
        try {
            temp = go();
        } catch (Exception ex) {
            //Logger.getLogger(Mothership.class.getName()).log(Level.SEVERE, null, ex);
            temp = "Connection Fail";
        }
        data = temp;
        return temp;
    }
    
    public String getId() {
        return id;
    }
    
    public String getType() {
        return type;
    }
    
    public String getData() {
        return data;
    }
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
