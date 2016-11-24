package edu.se459grp4.project.simulator.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents a wall object in the simulation environment
 *
 * @author whao
 * @version 1.8
 */
public class Wall implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean isVertical;
    private int mnBase; 
    private int mnFrom;
    private int mnTo;
    private Map<String,Door> mDoors;
    
    public Wall(boolean isVert, int nBase, int nFrom, int nTo){
        mnBase = nBase;
        isVertical = isVert;
        mnFrom = nFrom;
        mnTo = nTo;
        mDoors = new HashMap<>();
    }
      
    private String generateDoorKey(int nBase, int x, int y){
        return "Door"+ nBase + "_" + x+ "_" + y;
    }
    
    public int getBase(){
        return mnBase;
    }
    
    public int getFrom(){
        return mnFrom;
    }
    
    public int getTo(){
        return mnTo;
    }
    
    /**
     * Indicates whether this wall is a vertical wall or a horizontal wall
     * 
     * @return true if the wall is vertical, false if it is horizontal
     */
    public boolean isVertical(){
        return isVertical;
    }
    
    /**
     * Get all the doors currently associated with this wall
     * 
     * @return a list of Doors that are contained within the wall
     * @see Door
     */
    public List<Door> getAllDoors(){
        List<Door> lList = new ArrayList<>();
        for(Entry<String, Door> entry : mDoors.entrySet()){
           lList.add(entry.getValue());
        }
        return lList;
    }
    
    /**
     * Add a door into this wall
     * 
     * @param nBase todo
     * @param nFrom todo
     * @param nTo todo
     * @param isOpen true is this door should be open, false if it should be closed
     * @return true if the door was successfully added, false otherwise
     */
    public boolean addDoor(int nBase, int nFrom, int nTo, boolean isOpen){
        if(nFrom < mnFrom || nTo > mnTo){
            return false;
        }
        
        else if(nTo - nFrom > 1){
            return false;
        }
        mDoors.put(generateDoorKey(nBase,nFrom,nTo), new Door(isVertical,nBase,nFrom,nTo,isOpen));
        return true;
    }
    
    
    public boolean canBuildDoor(boolean nVer, int nBase, int nX, int nY){
        if(isVertical == nVer && mnBase == nBase && nX >= mnFrom && nY <= mnTo){
            return true;
        }
        
        return false;
    }
    
    
    public boolean canPassThrough(boolean nVer, int nBase, int nFrom, int nTo){
        if(isVertical != nVer || mnBase != nBase){
            return true;
        }
        
        else if(nFrom >= mnFrom && nTo <= mnTo ){
            //check if there is a open door
            for(Entry<String, Door> entry : mDoors.entrySet()){
                if(entry.getValue().canPassThrough(nFrom,nTo)){
                    return true;
                }       
            }
            return false;
        }
            
        return true;
    }
   
    public boolean operateDoor(boolean nbVer, int nBase, int x, int y, boolean shouldOpen){
    	if(nbVer != isVertical){
    		return false;
        }
    	//find the door
    	Door lDoor = mDoors.get(generateDoorKey(nBase,x,y));
    	if(lDoor == null){
    		return false;
    	}
       
       return !shouldOpen ? lDoor.closeDoor() : lDoor.openDoor();
    }
}
