/**
 * Copyright (c) 2018 Brendan Russell BrendanLeeRussell72@gmail.com
 */

package com.brendanrussell.greenhousemothership;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This is the main class and will create a thread to run the program on a loop.
 * 
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
