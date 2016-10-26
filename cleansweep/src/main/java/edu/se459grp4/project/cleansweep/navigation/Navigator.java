package edu.se459grp4.project.cleansweep.navigation;

import edu.se459grp4.project.cleansweep.environment.Environment;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;

public abstract class Navigator {
    Environment environment;

    public Navigator(Environment environment) {
        this.environment = environment;
    }

    public abstract Direction movementDirection(FloorUnit currentFloorUnit);
}
