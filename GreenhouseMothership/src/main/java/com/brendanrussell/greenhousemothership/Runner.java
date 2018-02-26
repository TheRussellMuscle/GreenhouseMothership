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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This is the main class and will create a thread to run the program on a loop.
 * @author Brendan Russell BrendanLeeRussell72@gmail.com
 * @version 1.0
 */
public class Runner {
    private static int runCounter;

    /**
     * The main() method calls the run method
     * @param args no arguments necessary
     */
    public static void main(String[] args) {
        runCounter = 0;
        run();
    }

    /**
     * The run() method creates an object of type MotherShip which will activate
     * all functions of the program when called.
     */
    public static void run() {
        //adds one to the amount of times this program has been run
        //since last reboot
        runCounter++; 
        
        
        MotherShip m = new MotherShip(); //create MotherShip object
        //Creates executor service to run MotherShip thread
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        
        try {
            //Schedules the executor to run Mothership every 570 seconds(9.5min)
            executor.scheduleAtFixedRate(m, 0L, 570L, TimeUnit.SECONDS);
            //Tells runner thread to sleep for roughly 27 billion years
            //afterwards the program will shut down
            Thread.sleep(TimeUnit.DAYS.toMillis(9999999999999L));
        } catch (InterruptedException ex) {
            //If the process is interrupted the program will attempt to restart
            //itself a maximum of 10 times
            if (runCounter <= 10) {
                run();
            }
        } finally {
            //Shuts down the executor service, ends the program
            System.out.println("Program Shutting Down");
            executor.shutdown();
        }
    }
}
