package edu.se459grp4.project.cleansweep.sensors;

import java.io.IOException;

import edu.se459grp4.project.cleansweep.logger.Logger;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.FloorSimulator;
import edu.se459grp4.project.simulator.types.Border;

public class NavigationSensor implements Sensor {
    private FloorSimulator floorSimulator;
    private Direction direction;
    private String directionStatus;

    public NavigationSensor(FloorSimulator floorSimulator, Direction direction) {
    	this.floorSimulator = floorSimulator;
        this.direction = direction;
    }

    public boolean update(FloorUnit floorUnit) {
        // TODO: update appropriate FloorUnit variable based on sensor direction
    	/*
    	 * Still trying to find x and y. Could use the floor plan to get log x and y but that would defeat the purpose.
    	 */
    	floorUnit.setNorthBorder(floorSimulator.getBorder("North"));
    	checkStatus(floorUnit);
    	try {
			Logger.writeToNavigationSensorLog("North",directionStatus, floorSimulator.getCleanSweepPosition().x , floorSimulator.getCleanSweepPosition().y);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   
    	floorUnit.setSouthBorder(floorSimulator.getBorder("South"));
    	checkStatus(floorUnit);
    	try {
			Logger.writeToNavigationSensorLog("South",directionStatus, floorSimulator.getCleanSweepPosition().x , floorSimulator.getCleanSweepPosition().y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	floorUnit.setEastBorder(floorSimulator.getBorder("East"));
    	checkStatus(floorUnit);
    	try {
			Logger.writeToNavigationSensorLog("East",directionStatus, floorSimulator.getCleanSweepPosition().x , floorSimulator.getCleanSweepPosition().y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	floorUnit.setWestBorder(floorSimulator.getBorder("West"));
    	checkStatus(floorUnit);
    	try {
			Logger.writeToNavigationSensorLog("West",directionStatus, floorSimulator.getCleanSweepPosition().x , floorSimulator.getCleanSweepPosition().y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return true;
    }
    
    
    private void checkStatus(FloorUnit floorUnit)
    {
    	if(floorUnit.getBorder(direction)==Border.OPEN)
    		directionStatus="Open";
 
    	else 
    		directionStatus="Blocked";
    }
}
