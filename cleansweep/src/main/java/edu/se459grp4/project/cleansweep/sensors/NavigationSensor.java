package edu.se459grp4.project.cleansweep.sensors;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.FloorSimulator;

public class NavigationSensor implements Sensor {
    private FloorSimulator floorSimulator;
    private Direction direction;

    public NavigationSensor(FloorSimulator floorSimulator, Direction direction) {
    	this.floorSimulator = floorSimulator;
        this.direction = direction;
    }

    public boolean update(FloorUnit floorUnit) {
        // TODO: update appropriate FloorUnit variable based on sensor direction
    	
        return true;
    }
}
