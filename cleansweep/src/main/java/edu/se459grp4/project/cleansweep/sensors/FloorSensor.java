package edu.se459grp4.project.cleansweep.sensors;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.simulator.FloorSimulator;
import edu.se459grp4.project.simulator.types.Tile;

public class FloorSensor implements Sensor {
    private FloorSimulator floorSimulator;

    public FloorSensor(FloorSimulator floorSimulator) {
        this.floorSimulator = floorSimulator;
    }

    public boolean update(FloorUnit floorUnit) {
        // TODO: get tile type from floor simulator and update FloorUnit
    	Tile tile = floorSimulator.getTileType();
        return true;
    }
}
