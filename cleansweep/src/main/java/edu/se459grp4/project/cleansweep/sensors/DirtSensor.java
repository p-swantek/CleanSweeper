package edu.se459grp4.project.cleansweep.sensors;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.simulator.FloorSimulator;
import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.logger.*;
import edu.se459grp4.project.cleansweep.managers.DirtManager;

import java.io.IOException;

import edu.se459grp4.*;

public class DirtSensor implements Sensor {
    private FloorSimulator floorSimulator;
    FloorEnvironment surrounding =new FloorEnvironment(0, 0);
	

    public DirtSensor(FloorSimulator floorSimulator) {
        this.floorSimulator = floorSimulator;
    }

    public boolean update(FloorUnit floorUnit) {
        // TODO: call clean method on floor simulator and update whether dirt present
    	//check if we want this to return true if dirt is present?
    	if (floorUnit != null){
        	floorUnit.setDirtPresent(floorSimulator.clean());
        	DirtManager.dirtCleaned();
        	return true;
    	}
    	else
    		return false;
    }
    
}
