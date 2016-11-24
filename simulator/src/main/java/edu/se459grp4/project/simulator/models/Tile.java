package edu.se459grp4.project.simulator.models;

import java.io.Serializable;

import edu.se459grp4.project.simulator.types.SurfaceType;

/**
 * Represents a floor tile object in the simulation environment
 *
 * @author whao
 * @version 1.8
 */
public class Tile implements Serializable{


	private static final long serialVersionUID = 1L;
	private int mx;
    private int my;
    private SurfaceType surfaceType;
    private int mnDirtVal;
    
    /**
     * Construct a new tile with the given x, y coordinates, surface type, and dirt amount
     * 
     * @param x the x coordinate of this tile
     * @param y the y coordinate of this tile
     * @param nStatus the surface type of this tile
     * @param nVal the dirt amount on this tile
     */
    public Tile(int x, int y, SurfaceType nStatus, int nVal){
        mx = x;
        my = y;
        surfaceType = nStatus;
        mnDirtVal = nVal;
    }
    
    /**
     * Get the x coordinate of this tile
     * 
     * @return the x coordinate
     */
    public int getX(){
        return mx;
    }
    
    /**
     * Get the y coordinate of this tile
     * 
     * @return the y coordinate
     */
    public int getY(){
        return my;
    }
    
    /**
     * Get the surface type of this tile
     * 
     * @return the TileStatus indicating the surface type
     * @see SurfaceType
     */
    public synchronized SurfaceType getSurfaceType(){
        return surfaceType;
    }
    
    /**
     * Set the surface type for a tile
     * 
     * @param type the type of floor this tile represents
     */
    public synchronized void setFloorType(SurfaceType type){
        surfaceType = type;
    }
    
    /**
     * Get the amount of dirt on this tile
     * 
     * @return the amount of dirt
     */
    public synchronized  int getDirtAmount(){
        return mnDirtVal;
    }
    
    /**
     * Set the amount of dirt that should be on this tile
     * 
     * @param nVal the amount of dirt that should be on this tile
     */
    public synchronized void setDirtAmount(int nVal){
        mnDirtVal = nVal;
    }
    
    /**
     * Removes an amount of dirt from this tile
     * 
     * @param nVal the amount of dirt to remove
     * @return the amount of dirt that was successfully removed
     */
    public synchronized int removeDirt(int nVal){
        if(mnDirtVal == 0){
            return 0;
        }
        
        else if(mnDirtVal > nVal){
            mnDirtVal -= nVal;
            return nVal;
        }
        
        else{
            int lTemp = mnDirtVal;
            mnDirtVal = 0;
            return lTemp;
        }
    }
    
    @Override
    public String toString(){
        return (getSurfaceType() == SurfaceType.CHARGING_STATION ? "ChargeStation" : "Tile") + " X "+ getX() + " Y " + getY();
    }
}
