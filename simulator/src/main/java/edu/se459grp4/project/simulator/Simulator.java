package edu.se459grp4.project.simulator;


import edu.se459grp4.project.simulator.types.TileStatus;
import edu.se459grp4.project.simulator.models.*;
import edu.se459grp4.project.simulator.types.*;
import java.io.*;
import java.util.List;

/**
 * Singleton that represents the simulator.  The clean sweep interacts with the simulator to retrieve relevant data such
 * as the amount of dirt on the floor, the type of floor it is on, as well as detecting obstacles on the floor
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public class Simulator {
	
    private static Simulator instance ;
    private FloorPlan floorPlan;
    
    
    private Simulator(){
       floorPlan = null; 
    }
    
    /**
     * Gets a reference to the single instance of the simulator
     * 
     * @return the single reference to the simulator
     */
    public static synchronized Simulator getInstance(){
        if(instance == null){
            instance = new Simulator();
        }
        return instance;
    }
    
    
    
    /**
     * Loads a floorplan file into the simulator
     * 
     * @param fileLocation the file path that points to the location of the floor plan to load
     * @return a FloorPlan object based off the data from the file
     * @see FloorPlan
     */
    public FloorPlan loadFloorPlan(String fileLocation) {
     // FloorPlan lLoadedFloorPlan = null;
      floorPlan = null;
      try {
         FileInputStream fileIn = new FileInputStream(fileLocation);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         floorPlan = (FloorPlan) in.readObject();
         in.close();
         fileIn.close();
         return floorPlan;
      }catch(IOException i) {
        i.printStackTrace();
         return null;
      }catch(ClassNotFoundException c) {
         System.out.println("./example.flr class not found");
         c.printStackTrace();
         return null;
      }
    }

    /**
     * Provides directional data of a certain spot on the floor
     * 
     * @param nDirection the direction to check
     * @param x the x coordinate of the location being checked from
     * @param y the y coordinate of the location being checked from
     * @return the PathStatus in the desired direction at the given coordinate point
     */
    public PathStatus getDirectionalData(Direction nDirection,int x,int y){
        int nDestX = x;
        int nDestY = y;
        
        if(nDirection == Direction.Up){
            nDestY--;
        }
        
        if(nDirection == Direction.Down){
            nDestY++;
        }
        
        if(nDirection == Direction.Left){
            nDestX--;
        }
        
        if(nDirection == Direction.Right){
           nDestX++;
        }
        
        //Check if there is path from (x,y) to (nDestX,nDestY)
        if(floorPlan != null){
            return floorPlan.checkPath(x, y, nDestX, nDestY);
        }
        
        return PathStatus.Blocked;
    }
    
    /**
     * Provides data on the amount of dirt on a location of the floor
     * 
     * @param x the x coordinate of the location being checked
     * @param y the y coordinate of the location being checked
     * @return the amount of dirt at the given location
     */
    public int getDirtData(int x,int y){
        if(floorPlan != null){
            return floorPlan.getDirtAmount(x, y);
        }
          
        return 0;
    }
    
    
    /**
     * Provides data on the type of floor at a certain location on the floor
     * 
     * @param x the x coordinate of the location to check
     * @param y the y coordinate of the location to check
     * @return the TileStatus at the given location
     */
    public TileStatus getSurfaceData(int x,int y){
        if(floorPlan != null){
            return floorPlan.getSurfaceType(x, y);
        }
         
        return TileStatus.BARE_FLOOR;
    }
    
    /**
     * Removes some dirt from the given section of floor
     * 
     * @param x the x coordiante to remove dirt from
     * @param y the y coordinate to remove dirt from
     * @param nVal the amount of dirt to remove
     * @return the amount of dirt that was cleaned
     */
    public int removeDirt(int x,int y,int nVal){
    	if(floorPlan != null){
    		return floorPlan.removeDirt(x, y,nVal);
        }
    	
        return 0;
    }
    
    /**
     * Allows a user to open or close a door in the simulation
     * 
     * @param nVer whether or not this is a horizontal door or a vertical door
     * @param nBase the length of the door to operate
     * @param nFrom starting coordinates of the door
     * @param nTo ending coordinates of the door
     * @param bOpen is the door open or not
     * @return true if the door was able to be successfully opened/closed, false otherwise
     */
    public boolean operateDoor(boolean nVer,int nBase,int nFrom,int nTo,boolean bOpen){
    	if(floorPlan != null){
    		return floorPlan.operateDoor(nVer, nBase, nFrom, nTo, bOpen);
        }
          
        return false;
    }
   
    /**
     * Add a new charging station to the simulation environment
     * 
     * @param nX the x coordinate of the charging station
     * @param nY the y coordinate of the charging
     * @return true if the charge station was successfully added, false otherwise
     */
    public boolean addChargeStation(int nX,int nY){
         if(floorPlan == null){
             return false;
         }
         
         return floorPlan.setSurfaceType(nX, nY, TileStatus.CHARGING_STATION);
        
    }
    
    /**
     * Remove a charge station from the simulation environment
     * 
     * @param nX the x coordinate of the charge station to remove
     * @param nY the y coordinate of the charge station to remove
     * @return true if the charge station was successfully removed, false otherwise
     */
    public boolean removeChargeStation(int nX,int nY){
        if(floorPlan == null){
             return false;
        }
        return floorPlan.setSurfaceType(nX, nY, TileStatus.BARE_FLOOR);
    }
    
    /**
     * Get a list of all the doors currently in the simulation
     * 
     * @return a list of all the doors
     */
    public List<Door> getAllDoors(){
    	if(floorPlan == null){
    		return null;
    	}
    	
        return floorPlan.getAllDoors();
    }
    
    /**
     * Get a list of all the charge stations currently in the simulation
     * 
     * @return the list of all the charge stations
     */
    public List<Tile> getAllChargeStations(){
       if(floorPlan == null){
             return null;
       }
       
       return floorPlan.getAllChargeStations();
    }
   
}
