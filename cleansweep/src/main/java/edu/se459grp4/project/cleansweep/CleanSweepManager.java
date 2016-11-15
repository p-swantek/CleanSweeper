
package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;
import java.util.HashMap;
import java.util.Map;


public class CleanSweepManager {
    
    private static CleanSweepManager instance ;
    public static synchronized CleanSweepManager getInstance(){
        if(instance == null){
            instance = new CleanSweepManager();
        }
        return instance;
    }
    private Integer mnCurrentID = 0;
    private Map<Integer,CleanSweep> mMapCleanSweep = new HashMap<Integer,CleanSweep>();
    public int CreateCleanSweep(int x,int y)
    {
        int nID = mnCurrentID++;
        CleanSweep lCleanSweep = new CleanSweep(nID,100.0,1000,x,y);
        mMapCleanSweep.put(nID, lCleanSweep);
        return nID;
    }
    public CleanSweep GetCleanSweep(int nID)
    {
        return mMapCleanSweep.get(nID);
    }
    //start the control system
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
        (new Thread(lControlSystem)).start();
        return true;
    }
    
    //start the control system
    public boolean Stop(int nID)
    {
        return true;
    }
    
 
    
}
