package edu.se459grp4.project.cleansweep.sensors;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.simulator.FloorSimulator;

public class DirtSensor implements Sensor {
    private FloorSimulator floorSimulator;

    public DirtSensor(FloorSimulator floorSimulator) {
        this.floorSimulator = floorSimulator;
    }

    public boolean update(FloorUnit floorUnit) {
        // TODO: call clean method on floor simulator and update whether dirt present
        return true;
    }
}