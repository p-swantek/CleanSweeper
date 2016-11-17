package edu.se459grp4.project.simulator.models;

import java.io.Serializable;

/**
 * Represents a door object in the simulation environment
 *
 * @author Group 4
 * @version 1.8
 */
public class Door implements Serializable  {
	
    private boolean mbVertical;
    private int mnBase;
    private int mnFrom;
    private int mnTo;
    private boolean mbOpen;
  
    /**
     * Construct a new door object to be used in the simulation
     *  
     * @param nVer true if the door is a vertical door, false otherwise
     * @param nBase the length of the door
     * @param nFrom the starting coordinate of the door
     * @param nTo the ending coordinate of the door
     * @param nbOpen true if the door is open, false otherwise
     */
    public Door(boolean nVer,int nBase,int nFrom,int nTo,boolean nbOpen)
    {
        mbVertical = nVer;
        mnBase = nBase;
        mnFrom = nFrom;
        mnTo = nTo;
        mbOpen = nbOpen;
    }
    
    /**
     * Get the length of this door
     * 
     * @return the door's length
     */
    public int GetBase()
    {
        return mnBase;
    }
    
    /**
     * Gets the starting coordinate of this door
     * 
     * @return the starting coordinate
     */
    public int GetFrom()
    {
        return mnFrom;
    }
    
    /**
     * Get the ending coordinate of this door
     * 
     * @return the ending coordinate
     */
    public int GetTo()
    {
        return mnTo;
    }
    
    /**
     * Gets whether this is a vertical or a horizontal door
     * 
     * @return true if this is a vertical door, false otherwise
     */
    public boolean GetVertical()
    {
        return mbVertical;
    }
    
    /**
     * Tells whether this door is open or not
     * 
     * @return true if the door is currently open, false otherwise
     */
    public synchronized  boolean GetIsOpened()
    {
        return mbOpen;
    }
    
    /**
     * Sets a door to be open
     * 
     * @return true if the door is successfully set to be open
     */
    public synchronized  boolean Open()
    {
        mbOpen = true;
        return true;
    }
    
    /**
     * Sets a door to be closed
     * 
     * @return true if the door is successfully closed, false otherwise
     */
    public synchronized  boolean Close()
    {
         mbOpen = false;
         return true;
    }
    
    /**
     * Checks if you can pass through this door
     * 
     * @param nFrom the source
     * @param nTo the destination
     * @return true if able to pass through, false otherwise
     */
    public synchronized  boolean CheckPass(int nFrom,int nTo)
    {
        if(mbOpen != false &&
          (nFrom == mnFrom || nTo == mnTo) )
            return true;
        return false;
        
    }
    
    
    @Override
    public String toString()
    {
        return  "Door"+(mbVertical?"Vertical":"Horizontal") + "Base " + mnBase+" From "+mnFrom+" To "+mnTo;
    }
    
}
