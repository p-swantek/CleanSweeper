package edu.se459grp4.project.cleansweep.navigation;

import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;

public abstract class Navigator {
    FloorEnvironment floorEnvironment;

    public Navigator(FloorEnvironment floorEnvironment) {
        this.floorEnvironment = floorEnvironment;
    }

    public abstract Direction movementDirection(FloorUnit currentFloorUnit);
}
