package edu.se459grp4.project.simulator;

import edu.se459grp4.project.simulator.gui.SimulatorGUI;
import edu.se459grp4.project.simulator.models.FloorTile;
import edu.se459grp4.project.simulator.types.Border;
import edu.se459grp4.project.simulator.types.Tile;
import edu.se459grp4.project.simulator.util.FloorPlanReader;

import java.awt.*;
import java.io.*;

public class FloorSimulator {
    static final String DEFAULT_FLOOR_PLAN = "/sample-floor-plan.csv";
    FloorTile[][] floorTiles;
    Point cleanSweepPosition;
    SimulatorGUI gui;

    public FloorSimulator(int cleanSweepX, int cleanSweepY, String floorPlanLocation) {
        cleanSweepPosition = new Point(cleanSweepX, cleanSweepY);
        loadFloorPlan(floorPlanLocation);
        gui = new SimulatorGUI(getFloorTiles());
        gui.start();
    }

    /**
     * read in a floor plan from a CSV file and create a floor based on that file's data
     * 
     * @param fileLocation the location of the file on which the floor plan is based
     */
    public void loadFloorPlan(String fileLocation) {
        InputStream inputStream = null;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;

        // Use default file in "resources" directory if no file location provided
        if(fileLocation == null) {
            inputStream = getClass().getResourceAsStream(FloorSimulator.DEFAULT_FLOOR_PLAN);
            inputStreamReader = new InputStreamReader(inputStream);
        }
        else {
            try {
                fileInputStream = new FileInputStream(fileLocation);
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            inputStreamReader = new InputStreamReader(fileInputStream) ;
        }

        FloorPlanReader floorPlanReader = new FloorPlanReader(inputStreamReader);
        this.floorTiles = floorPlanReader.getTileElements();
    }

    /**
     * get the full floor
     * 
     * @return a 2D array of floor tiles that represents the currnt floor in the simulator
     */
    public FloorTile[][] getFloorTiles() {
        return floorTiles;
    }

    /**
     * get a particular floor tile using x,y coordinates
     * 
     * @param x the x coordinate of the tile in the floor
     * @param y the y coordinate of the tile in the floor
     * @return the floor tile at this x,y coordinate
     */
    public FloorTile getFloorTile(int x, int y) {
        return floorTiles[x][y];
    }

    /**
     * get the tile on which the clean sweep is currently located
     * 
     * @return the floor tile where the clean sweep currently is
     */
    public FloorTile getCleanSweepFloorTile() {
        int cleanSweepX = cleanSweepPosition.getLocation().x;
        int cleanSweepY = cleanSweepPosition.getLocation().y;
        return floorTiles[cleanSweepX][cleanSweepY];
    }
    

  
    /**
     * returns the Border type for a given edge of the tile the clean sweep is on
     * 
     * @param edge string representing the edge of the tile to check (can be North, South, East, West)
     * @return the Border type on a particular edge of the tile
     */
    public Border getBorder(String edge){
    	
    	FloorTile csTile = getCleanSweepFloorTile();
    	Border border = null;
    	
    	switch(edge){
    		
    		case "UP":
    			border = csTile.getNorthBorder();
    			break;
    		case "DOWN":
    			border = csTile.getSouthBorder();
    			break;
    		case "LEFT":
    			border = csTile.getEastBorder();
    			break;
    		case "RIGHT":
    			border = csTile.getWestBorder();
    			break;
    
    	}
    	
    	return border;
    }
    
    /**
     * gets the amount of dirt on the tile the clean sweep is on
     * 
     * @return the amount of dirt on the tile
     */
    public int getDirtAmount(){
    	return getCleanSweepFloorTile().getDirtAmount();
    }
    
    /**
     * gets the type of floor tile that the clean sweep is currently on (bare floor, low pile, high pile)
     * 
     * @return the type of floor tile the clean sweep is on
     */
    public Tile getTileType(){
    	return getCleanSweepFloorTile().getTileType();
    }
    
    /**
     * clients call this method with a string representing a direction to move,
     * returns true or false depending on whether the movement can be completed successfully
     * 
     * @param direction string representing the direction of movement to attempt (can be UP, DOWN, LEFT, or RIGHT)
     * @return true if the clean sweep can move in the specified direction, false if it can not
     */
    public boolean move(String direction){
    	
    	FloorTile csTile = getCleanSweepFloorTile();
    	boolean canMove = false;
    	
    	//check the direction, adjust the cleansweep point accordingly and register if the movement succeeded
    	//checking for either an open door or null at the moment (null at the moment seems to signify an open path)
    	//possibly change the null check to a more appropriate Border type
    	switch(direction){
    	
    		case "UP":
    			Border northBorder = csTile.getNorthBorder();
    			if (northBorder == Border.OPEN_DOOR || northBorder == Border.OPEN){ //check if the desired direction is open or not, if it is...
    				canMove = true;  //we can move
    				cleanSweepPosition.setLocation(cleanSweepPosition.getX(), cleanSweepPosition.getY()+1); //update the clean sweep point
    				gui.setCleanSweep(cleanSweepPosition); //tell the gui that clean sweep has a new location
    			}
    			break;
    		
    		case "DOWN":
    			Border southBorder = csTile.getSouthBorder();
    			if (southBorder == Border.OPEN_DOOR || southBorder  == Border.OPEN){
    				canMove = true; 
    				cleanSweepPosition.setLocation(cleanSweepPosition.getX(), cleanSweepPosition.getY()-1);
    				gui.setCleanSweep(cleanSweepPosition); 
    			}
    			break;
    			
    		case "LEFT":
    			Border westBorder = csTile.getWestBorder();
    			if (westBorder == Border.OPEN_DOOR || westBorder == Border.OPEN){
    				canMove = true;
    				cleanSweepPosition.setLocation(cleanSweepPosition.getX()-1, cleanSweepPosition.getY());
    				gui.setCleanSweep(cleanSweepPosition); 
    			}
    			break;
    			
    		case "RIGHT":
    			Border eastBorder = csTile.getEastBorder();
    			if (eastBorder == Border.OPEN_DOOR || eastBorder == Border.OPEN ){
    				canMove = true; 
    				cleanSweepPosition.setLocation(cleanSweepPosition.getX()+1, cleanSweepPosition.getY());
    				gui.setCleanSweep(cleanSweepPosition);
    			}
    			break;
    	
    	}
    	
    	return canMove;
    }
    
    /**
     * tells whether the tile the clean sweep is on is clean or not
     * 
     * @return true if the tile is cleaned (dirt amount is <= 0), false otherwise
     */
    public boolean clean(){
    	return getCleanSweepFloorTile().getDirtAmount() <= 0;
    }
    
    /**
     * clients call this if they need to update the floor simulator at all
     */
    public void update(){
    	gui.refreshGUI();

    }

}
