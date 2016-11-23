package edu.se459grp4.project.simulator.models;

import edu.se459grp4.project.simulator.types.*;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

/**
 * Floor plan that is used to represent the environment that the clean sweeper will be operating in.  The simulator utilizes the floor plan
 * as the environment used in the simulation.
 * 
 * @author Group 4
 * @version 1.8
 */
public class FloorPlan  extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	private int mnTileSquareNum;
    private int mnTileSize;
    private int mnWallWidth;
    private int mnDoorLength;
    private Map<String,Tile> mMapTiles;
    private Map<String,Wall> mMapWalls;
    
    
    public FloorPlan(int nSquareNum, int nTileSize, int nWallWidth, int nDoorLength){
        mnTileSquareNum = nSquareNum;
        mnTileSize = nTileSize;
        mnWallWidth = nWallWidth;
        mnDoorLength = nDoorLength;
        mMapTiles = new HashMap<>();
        mMapWalls = new HashMap<>();
    }
    
    private String generateTileKey(int x,int y){
        return "Tile"+x+" " + y;
    }
    
    private String generateWallKey(int nBase,int x,int y,boolean nVer){
        return "Wall"+x+"_"+y+"_"+nBase+"_"+nVer;
    }
    
    /**
     * Get the count of the amount of tiles contained per row of this floorplan
     * 
     * @return the amount of tiles in a row
     */
    public int getRowLength(){
        return mnTileSquareNum;
    }
    
    /**
     * Get a list of all the floor tiles currently contained in the floor plan
     * 
     * @return a list of all the tiles
     */
    public List<Tile> getAllTiles(){
        List<Tile> lList = new ArrayList<>();
        for(Entry<String, Tile> entry : mMapTiles.entrySet()){
           lList.add(entry.getValue());
        }
        return lList;
    }
    
    /**
     * Get a list of all the walls currently contained in the floor plan
     * 
     * @return a list of all the walls
     */
    public List<Wall> getAllWalls(){
        List<Wall> lList = new ArrayList<>();
        for(Entry<String, Wall> entry : mMapWalls.entrySet()){
           lList.add(entry.getValue());
        }
        return lList;
    }
    
    /**
     * Get a list of all the doors currently contained in the floor plan
     * 
     * @return a list of all the doors
     */
    public List<Door> getAllDoors(){
        List<Door> lList = new ArrayList<>();
        for(Entry<String, Wall> entry : mMapWalls.entrySet()){
           lList.addAll(entry.getValue().GetAllDoors());
        }
        return lList;
    }
    
    /**
     * Get a list of all the charge stations in the floorplan
     * 
     * @return a list of all charging stations
     */
    public List<Tile> getAllChargeStations(){
        List<Tile> lList = new ArrayList<>();
        for(Entry<String, Tile> entry : mMapTiles.entrySet()){
           if(entry.getValue().GetStatus() == TileStatus.CHARGING_STATION){
             lList.add(entry.getValue());
           }
        }
        return lList;
    }
    
    /**
     * Add a floor tile to the floor plan
     * 
     * @param x the x coordinate of the tile to add
     * @param y the y coordinate of the tile to add
     * @param nStatus the type of this tile
     * @param nDirVal the amount of dirt on this tile
     * @return true if the tile was successfully added, false otherwise
     */
    public boolean addTile(int x, int y, TileStatus nStatus, int nDirVal){
        if (!areCoordinatesValid(x, y)){
        	return false;
        }
        
        //should check if x,y existed
        mMapTiles.put(generateTileKey(x,y), new Tile(x,y,nStatus,nDirVal));
        return true;
    }
    
    /**
     * Add a wall to the floor plan
     * 
     * @param isVertical true if this is a vertical wall, false otherwise
     * @param nBase the length of the wall
     * @param x the x coordinate of the beginning of the wall
     * @param y the y coordinate of the end of the wall
     * @return true if the wall was successfully added, false otherwise
     */
    public boolean addWall(boolean isVertical, int nBase, int x, int y){
    	if (!areCoordinatesValid(x, y)){
        	return false;
        }
    	
        //should check if x,y existed
        mMapWalls.put(generateWallKey(nBase,x,y,isVertical), new Wall(isVertical,nBase,x,y));
        return true;
    }
    
    /**
     * Add a door to the floor plan
     * 
     * @param isVertical true if this door is a vertical door, false otherwise
     * @param nBase 
     * @param x
     * @param y
     * @param isOpen true if this door starts out open, false otherwise
     * @return true if the door was successfully added, false otherwise
     */
    public boolean addDoor(boolean isVertical, int nBase, int x, int y, boolean isOpen){
    	if (!areCoordinatesValid(x, y)){
        	return false;
        }
    	
        //Find the wall include x,y
        Wall lWall = null;
        for(Entry<String, Wall> entry : mMapWalls.entrySet()){
            if(entry.getValue().CheckCanBuildDoor(isVertical,nBase,x,y)){
                lWall = entry.getValue();
                break;
            }
        }
        //Add the door to that wall
        if(lWall != null){
        	return lWall.AddDoor(nBase,x,y,isOpen);
        }
        
        return false;
    }
    
    private boolean areCoordinatesValid(int x, int y){
    	if(x < 0 || x >= getRowLength()){
            return false;
        }
        if(y < 0 || y >= getRowLength()){
            return false;
        }
        
        return true;
    }
    
    /**
     * Checks that status of a path between 2 spots on the floor plan
     * 
     * @param x the starting x coordinate
     * @param y the starting y coordinate
     * @param nDestX the destination x coordinate
     * @param nDestY the destination y coordinate
     * @return the PathStatus between the 2 locations
     * @see PathStatus
     */
    public PathStatus checkPath(int x, int y, int nDestX, int nDestY){
        //check x,y existing
        Tile lTile = mMapTiles.get(generateTileKey(nDestX,nDestY));
        if(lTile == null){
            return PathStatus.Blocked;
        }
        
        //Check Stair
        if(lTile.GetStatus() == TileStatus.STAIRS){
            return PathStatus.Stair;
        }
        
        //check if there is a wall 
        boolean lbVertical = (x == nDestX ? false : true);
        int nBase = (lbVertical == false ? Math.min(y, nDestY) : Math.min(x, nDestX));
        int nFrom = (lbVertical == false ? Math.min(x, nDestX) : Math.min(y, nDestY));
        int nTo = nFrom;

        //Find the wall include lbVertical,x,y
        boolean lbRet = true;
        for(Entry<String, Wall> entry : mMapWalls.entrySet()){
            if(!entry.getValue().CheckCanPass(lbVertical, nBase, nFrom, nTo)){
                lbRet = false;
                break;
            }
        }
        
        return lbRet ? PathStatus.Open : PathStatus.Blocked;
    }
    
    
    /**
     * Get the amount of dirt contained on a section of the floor
     * 
     * @param x the x coordinate of the location to check
     * @param y the y coordinate of the location to check
     * @return the amount of dirt present at that location
     */
    public int getDirtAmount(int x, int y){
        Tile lTile = mMapTiles.get(generateTileKey(x,y));
        if(lTile != null){
            return lTile.GetDirtVal();
        }
        
        return 0;
    }
    
    /**
     * Set the amount of dirt that should be on the floor at the given location
     * 
     * @param x the x coordinate of the location to set dirt
     * @param y the y coordinate of the location to set dirt
     * @param nVal the amount of dirt to put at that spot
     * @return true if the dirt was successfully added, false otherwise
     */
    public boolean setDirtAmount(int x, int y, int nVal){
        Tile lTile = mMapTiles.get(generateTileKey(x,y));
        if(lTile != null){
            return lTile.SetDirtVal(nVal);
        }
        return false;
    }
    
    /**
     * Get the type of floor at the given location
     * 
     * @param x the x coordinate to check
     * @param y the y coordinate to check
     * @return the TileStatus indicating the type of floor at this spot
     * @see TileStatus
     */
    public TileStatus getSurfaceType(int x,int y){
        Tile lTile = mMapTiles.get(generateTileKey(x,y));
        if(lTile != null){
            return lTile.GetStatus();
        }
        return TileStatus.BARE_FLOOR;
    }
    
    /**
     * Sets the type of floor at the given location
     * 
     * @param x the x coordinate of the spot to set
     * @param y the y coordinate of the spot to set
     * @param surfaceType the type of tile that this section should be
     * @return true if the surface was successfully set, false otherwise
     */
    public boolean setSurfaceType(int x, int y, TileStatus surfaceType){
        Tile lTile = mMapTiles.get(generateTileKey(x,y));
        if(lTile != null){
            lTile.SetStatus(surfaceType);
            setChanged();
            notifyObservers(lTile);
            return true;
        }
        return false;
    }
    
    /**
     * Removes an amount of dirt from the floor at the given location
     * 
     * @param x the x coordinate of the spot to clean
     * @param y the y coordinate of the spot to clean
     * @param amtToRemove the amount of dirt to remove
     * @return the amount of dirt that was removed from this spot of the floor
     */
    public int removeDirt(int x, int y, int amtToRemove){
        Tile lTile = mMapTiles.get(generateTileKey(x,y));
        if(lTile != null){
            int lnVal = lTile.Sweep(amtToRemove);
            setChanged();
            notifyObservers(lTile);
            return lnVal;
        }
        return 0;
    }
    
    /**
     * Allows for a door in the floor plan to be open or closed as desired
     * 
     * @param isVertical true if this door is vertical, false otherwise
     * @param nBase
     * @param x
     * @param y
     * @param nbVal
     * @return true if the door was successfully operated, false otherwise
     */
    public boolean OperateDoor(boolean isVertical, int nBase, int x, int y, boolean nbVal){
        //find the door from the wall
        boolean lbRet = false;
        
        for(Entry<String,Wall> entry : mMapWalls.entrySet()){
            if(entry.getValue().OperateDoor(isVertical, nBase, x, y, nbVal)){
                lbRet = true;
                setChanged();
                notifyObservers(entry.getValue());
                break;
            }
        }
        return lbRet;
    }
}
