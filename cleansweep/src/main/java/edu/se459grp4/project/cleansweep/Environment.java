package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.models.Position;
import edu.se459grp4.project.cleansweep.types.Direction;

import java.util.HashMap;

public class Environment {
    private Position previousPosition;
    private Position currentPosition;
    private HashMap<Position, FloorUnit> floorMap;

    public Environment() {
        this.currentPosition = new Position(0, 0);
        this.floorMap = new HashMap<>();
    }

    public boolean addFloorUnit(FloorUnit floorUnit) {
        // TODO: add currentPosition with floorUnit into floorMap
        return true;
    }

    public void updatePosition(Direction movementDirection) {
        // TODO: assign currentPosition to previousPosition
        // TODO: update currentPosition based on direction
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }
}
