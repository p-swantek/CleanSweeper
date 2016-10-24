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

    public FloorTile[][] getFloorTiles() {
        return floorTiles;
    }

    public FloorTile getFloorTile(int x, int y) {
        return floorTiles[x][y];
    }

    public FloorTile getCleanSweepFloorTile() {
        int cleanSweepX = cleanSweepPosition.getLocation().x;
        int cleanSweepY = cleanSweepPosition.getLocation().y;
        return floorTiles[cleanSweepX][cleanSweepY];
    }
    
    public Border getBorder(String edge){
    	return null;
    }
    
    public int getDirtAmount(){
    	return -1;
    }
    
    public Tile getTileType(){
    	return null;
    }
    
    /*
     * clients call this method with a string representing a direction to move,
     * returns true or false depending on whether the movement can be completed successfully
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
    			if (northBorder == Border.OPEN_DOOR || northBorder == null){ //check if the desired direction is open or not, if it is...
    				canMove = true;  //we can move
    				cleanSweepPosition.setLocation(cleanSweepPosition.getX(), cleanSweepPosition.getY()+1); //update the clean sweep point
    				gui.setCleanSweep(cleanSweepPosition); //tell the gui that clean sweep has a new location
    				gui.refreshGUI(); //refresh the gui to update changes
    			}
    			break;
    		
    		case "DOWN":
    			Border southBorder = csTile.getSouthBorder();
    			if (southBorder == Border.OPEN_DOOR || southBorder == null){
    				canMove = true; 
    				cleanSweepPosition.setLocation(cleanSweepPosition.getX(), cleanSweepPosition.getY()-1);
    				gui.setCleanSweep(cleanSweepPosition); 
    				gui.refreshGUI(); 
    			}
    			break;
    			
    		case "LEFT":
    			Border westBorder = csTile.getWestBorder();
    			if (westBorder == Border.OPEN_DOOR || westBorder == null){
    				canMove = true;
    				cleanSweepPosition.setLocation(cleanSweepPosition.getX()-1, cleanSweepPosition.getY());
    				gui.setCleanSweep(cleanSweepPosition); 
    				gui.refreshGUI(); 
    			}
    			break;
    			
    		case "RIGHT":
    			Border eastBorder = csTile.getEastBorder();
    			if (eastBorder == Border.OPEN_DOOR || eastBorder == null){
    				canMove = true; 
    				cleanSweepPosition.setLocation(cleanSweepPosition.getX()+1, cleanSweepPosition.getY());
    				gui.setCleanSweep(cleanSweepPosition);
    				gui.refreshGUI();
    			}
    			break;
    	
    	}
    	
    	return canMove;
    }
    
    public boolean clean(){
    	return false;
    }
    
    public void update(){
    	gui.refreshGUI();
    }

}
