
package edu.se459grp4.project.cleansweep;

import java.util.HashMap;
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
    
    private static CleanSweepManager instance ;
    private static final Double INIT_POWER = 100.0;
    private static final int VACUUM_CAPACITY = 1000;
    
    private Integer mnCurrentID = 0;
    private Map<Integer,CleanSweep> mMapCleanSweep = new HashMap<>();
    private Map<Integer, Thread> workingSweeps = new HashMap<>(); //a record of the sweeps currently running a cleaning cycle
    
    private CleanSweepManager(){}
    
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
    public int CreateCleanSweep(int x,int y)
    {
        int nID = mnCurrentID++;
        CleanSweep lCleanSweep = new CleanSweep(nID,INIT_POWER,VACUUM_CAPACITY,x,y);
        mMapCleanSweep.put(nID, lCleanSweep);
        return nID;
    }
    
    /**
     * Gets the CleanSweep that is associated with the given id number
     * 
     * @param nID the id number for the CleanSweep you want to obtain
     * @return the CleanSweep that has the corresponding id number
     */
    public CleanSweep GetCleanSweep(int nID)
    {
        return mMapCleanSweep.get(nID);
    }

    /**
     * Gets the CleanSweep corresponding to the given id and starts it on a cleaning cycle
     * 
     * @param nID the id of the clean sweep which should be started on its cleaning cycle
     * @return true if the sweep was successfully located and started, false otherwise
     */
    public boolean StartCleanCycle(int nID)
    {
        CleanSweep lCleanSweep = mMapCleanSweep.get(nID);
        if(lCleanSweep==null)
            return false;
        
        //check power and capacity
        
        //check from the charge station
        
        //
        Navigator lControlSystem = new Navigator(lCleanSweep);
       // lControlSystem.start();
        
        Thread t = new Thread(lControlSystem);
        workingSweeps.put(nID, t);
        
        t.start();
       
        return true;
    }
    
    /**
     * Gets the CleanSweep corresponding to the given id and stops it from cleaning further
     * 
     * @param nID the id of the clean sweep which should be stopped
     * @return true if the sweep was able to be located and stopped, false otherwise
     */
    public boolean stopCleanCycle(int nID){
        Thread worker = workingSweeps.get(nID); //get the clean sweep to stop
        
        if(worker == null){
            return false;
        }
        
        worker.interrupt(); //interrupt that Thread
        return true;
    }

}
