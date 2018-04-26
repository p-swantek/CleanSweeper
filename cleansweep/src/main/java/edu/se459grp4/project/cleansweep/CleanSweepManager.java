package edu.se459grp4.project.cleansweep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This manages the various clean sweeps that are currently cleaning the floor. Will handle creating new clean sweeps and 
 * will either start them on a cleaning cycle or end their cleaning cycles
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public class CleanSweepManager {
    
    private static CleanSweepManager instance;
    private static final double INIT_POWER_LEVEL = 100.0;
    private static final int INIT_VACUUM_CAPACITY = 1000;
    
    private static int csIdCounter;
    private final Map<Integer,CleanSweep> allSweeps;
    private final Map<Integer, Thread> workingSweeps; //a record of the sweeps currently running a cleaning cycle
    
    /*
     * initilize the counter to start with 0 as first id for a sweep, initialize the maps that hold
     * the info about all the working sweeps
     */
    private CleanSweepManager(){
    	csIdCounter = 0;
    	allSweeps = new HashMap<>();
        workingSweeps = new HashMap<>();
    }
    
    /**
     * Get a reference to the instance of the CleanSweepManager Singleton
     * 
     * @return a reference to the single instance
     */
    public static synchronized CleanSweepManager getInstance(){
        if(instance == null){
            instance = new CleanSweepManager();
        }
        return instance;
    }
    
    /**
     * Creates a new clean sweep and starts it off on the given x and y coordinates. Keeps track of this sweep 
     * in its internal record
     * 
     * @param x the x coordinate for the new sweep
     * @param y the y coordinate for the new sweep
     * @return the id that was given to the sweep which was just created
     */
    public synchronized int createCleanSweep(int x,int y){
        int nID = csIdCounter++;
        CleanSweep cs = new CleanSweep(nID,INIT_POWER_LEVEL,INIT_VACUUM_CAPACITY,x,y);
        allSweeps.put(nID, cs);
        return nID;
    }
    
    /**
     * Gets the CleanSweep that is associated with the given id number
     * 
     * @param nID the id number for the CleanSweep you want to obtain
     * @return the CleanSweep that has the corresponding id number
     */
    public synchronized CleanSweep getCleanSweep(int nID){
        return allSweeps.get(nID);
    }

    /**
     * Gets the CleanSweep corresponding to the given id and starts it on a cleaning cycle
     * 
     * @param nID the id of the clean sweep which should be started on its cleaning cycle
     */
    public synchronized void startCleanCycle(int nID){
        CleanSweep cs = allSweeps.get(nID);
        
        if(cs == null){
            return;
        }
        
        Navigator navController = new Navigator(cs);
        
        Thread t = new Thread(navController);
        workingSweeps.put(nID, t);
        t.start();
    }
    
    /**
     * Gets the CleanSweep corresponding to the given id and stops it from cleaning further
     * 
     * @param nID the id of the clean sweep which should be stopped
     */
    public synchronized void stopCleanCycle(int nID){
        Thread cs = workingSweeps.get(nID); //get the clean sweep to stop
        
        if(cs == null){
            return;
        }
        
        cs.interrupt(); //interrupt that Thread
    }
    
    /**
     * Provides a list of all the ID numbers of the clean sweeps currently in service
     * 
     * @return a list of the integer ID numbers of all the current clean sweeps
     */
    public synchronized List<Integer> getAllSweeps(){
    	return new ArrayList<>(allSweeps.keySet());
    }

}
