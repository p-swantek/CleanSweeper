package edu.se459grp4.project.cleansweep.sensors;

import edu.se459grp4.project.cleansweep.models.FloorUnit;

public interface Sensor {
    // Update FloorUnit variables using received sensor data
    boolean update(FloorUnit floorUnit);
}
