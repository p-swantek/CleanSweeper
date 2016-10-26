package edu.se459grp4.project.cleansweep.environment;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.models.Position;
import edu.se459grp4.project.cleansweep.types.Direction;

public class Environment {
    private Position previousPosition;
    private Position currentPosition;
    private FloorGrid floorGrid;

    public Environment() {
        this.currentPosition = new Position(0, 0);
        floorGrid = new FloorGrid(2000, 2000);
    }

    public void addFloorUnit(FloorUnit floorUnit) {
        floorGrid.add(floorUnit, currentPosition);
    }

    public void updatePosition(Direction movementDirection) {
        // TODO: assign currentPosition to previousPosition
        // TODO: update currentPosition based on direction
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public FloorGrid getFloorGrid() {
        return floorGrid;
    }
}
