package edu.se459grp4.project.cleansweep.sensors;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.sensors.NavigationSensor;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.FloorSimulator;
import edu.se459grp4.project.simulator.types.Border;

import java.util.HashMap;

public class NavigationSensorSystem {
    private FloorSimulator floorSimulator;

    public NavigationSensorSystem(FloorSimulator floorSimulator) {
        this.floorSimulator = floorSimulator;
    }

    public boolean update(FloorUnit floorUnit) {
        HashMap<Direction, Border> boundaries = new HashMap<>();
        NavigationSensor navigationSensor = null;
        for(Direction direction : Direction.values()) {
            navigationSensor = new NavigationSensor(floorSimulator, direction);
            navigationSensor.update(floorUnit);
        }
        return true;
    }
}
