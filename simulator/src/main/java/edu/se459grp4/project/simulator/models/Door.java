package edu.se459grp4.project.simulator.models;

import java.io.Serializable;

/**
 * Represents a door object in the simulation environment
 *
 * @author Group 4
 * @version 1.8
 */
public class Door implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private boolean isVertical;
    private int mnBase;
    private int mnFrom;
    private int mnTo;
    private boolean isOpen;
  
    /**
     * Construct a new door object to be used in the simulation
     *  
     * @param nVer true if the door is a vertical door, false otherwise
     * @param nBase the length of the door
     * @param nFrom the starting coordinate of the door
     * @param nTo the ending coordinate of the door
     * @param nbOpen true if the door is open, false otherwise
     */
    public Door(boolean nVer,int nBase,int nFrom,int nTo,boolean nbOpen){
        isVertical = nVer;
        mnBase = nBase;
        mnFrom = nFrom;
        mnTo = nTo;
        isOpen = nbOpen;
    }
    
    /**
     * Get the length of this door
     * 
     * @return the door's length
     */
    public int getBase(){
        return mnBase;
    }
    
    /**
     * Gets the starting coordinate of this door
     * 
     * @return the starting coordinate
     */
    public int getFrom(){
        return mnFrom;
    }
    
    /**
     * Get the ending coordinate of this door
     * 
     * @return the ending coordinate
     */
    public int getTo(){
        return mnTo;
    }
    
    /**
     * Gets whether this is a vertical or a horizontal door
     * 
     * @return true if this is a vertical door, false otherwise
     */
    public boolean isVertical(){
        return isVertical;
    }
    
    /**
     * Tells whether this door is open or not
     * 
     * @return true if the door is currently open, false otherwise
     */
    public synchronized  boolean isOpen(){
        return isOpen;
    }
    
    /**
     * Sets a door to be open
     * 
     * @return true or false on whether the door got opened
     * 
     */
    public synchronized boolean openDoor(){
        isOpen = true;
        return true;
    }
    
    /**
     * Sets a door to be closed
     * 
     * @return true if the door is successfully closed, false otherwise
     */
    public synchronized  boolean closeDoor(){
         isOpen = false;
         return true;
    }
    
    /**
     * Checks if you can pass through this door
     * 
     * @param nFrom the source
     * @param nTo the destination
     * @return true if able to pass through, false otherwise
     */
    public synchronized  boolean canPassThrough(int nFrom,int nTo){
        if(isOpen && (nFrom == mnFrom || nTo == mnTo)){
            return true;
        }
        
        return false;
        
    }
    
    
    @Override
    public String toString(){
        return  "Door"+(isVertical ? "Vertical" : "Horizontal") + "Base " + mnBase + " From " + mnFrom + " To " + mnTo;
    }
    
}
