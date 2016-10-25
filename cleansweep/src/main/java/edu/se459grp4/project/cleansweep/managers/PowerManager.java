package edu.se459grp4.project.cleansweep.managers;

import edu.se459grp4.project.cleansweep.models.FloorUnit;

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
        return true;
    }
}
