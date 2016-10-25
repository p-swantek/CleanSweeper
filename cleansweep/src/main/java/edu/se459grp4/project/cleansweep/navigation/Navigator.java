package edu.se459grp4.project.cleansweep.navigation;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.models.Position;
import edu.se459grp4.project.cleansweep.types.Direction;

public interface Navigator {
    // Sends movement to simulator and returns direction of chosen movement
    Direction move(Position currentPosition, FloorUnit currentFloorUnit);
}
