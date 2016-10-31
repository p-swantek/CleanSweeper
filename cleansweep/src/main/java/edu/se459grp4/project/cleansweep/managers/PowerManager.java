package edu.se459grp4.project.cleansweep.managers;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.simulator.types.Tile;

public class PowerManager {
    private int powerCapacity;
    private int currentPower;
    private int threshold;

    public PowerManager(int powerCapacity, int currentPower) {
        this.powerCapacity = powerCapacity;
        this.currentPower = currentPower;
    }

    public PowerManager(int powerCapacity, int currentPower, int threshold) {
        this.powerCapacity = powerCapacity;
        this.currentPower = currentPower;
        this.threshold = threshold;
    }

    public boolean updatePower(FloorUnit previousFloorUnit, FloorUnit currentFloorUnit) {
        // TODO: update currentPower based on average of floor types
    	if (previousFloorUnit != null && currentFloorUnit != null){

    		int average = (powerRequirement(previousFloorUnit) + powerRequirement(currentFloorUnit)) / 2;
    		this.currentPower -= average;
    		
    		return true;
    	}

    	else{
    		return false;
    	}
    }
    
    private int powerRequirement(FloorUnit f){
    	int powerNeeded = 0;
    	
    	switch (f.getTileType()){
    	
	    	case BARE_FLOOR:
	    		powerNeeded = 1;
	    		break;
	    		
	    	case LOW_CARPET:
	    		powerNeeded = 2;
	    		break;
	    		
	    	case HIGH_CARPET:
	    		powerNeeded = 3;
	    		break;
	    		
	    	default:
	    		break;
    	
    	}
    	
    	return powerNeeded;
    }
}
