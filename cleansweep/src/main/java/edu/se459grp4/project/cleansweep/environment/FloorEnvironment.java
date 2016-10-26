package edu.se459grp4.project.cleansweep.environment;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.models.Position;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.types.Border;

public class FloorEnvironment extends FloorGrid {
    private Position previousPosition;
    private Position currentPosition;
    private FloorUnit currentFloorUnit;
    private FloorGrid floorGrid;

    public FloorEnvironment(int maxXRange, int maxYRange) {
        super(maxXRange, maxYRange);
        this.currentPosition = new Position(0, 0);
    }

    public void addFloorUnit(FloorUnit floorUnit) {
        add(floorUnit, currentPosition);
        currentFloorUnit = floorUnit;
    }

    public void updatePosition(Direction movementDirection) {
        // TODO: assign currentPosition to previousPosition
        // TODO: update currentPosition based on direction
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public FloorUnit getCurrentFloorUnit() {
        return currentFloorUnit;
    }

    public FloorGrid getFloorGrid() {
        return floorGrid;
    }

    public boolean checkIfCleanable(Direction direction) {
        FloorUnit neighborFloorUnit = floorGrid.getRelativeCoordinate(currentPosition, direction.getPosition());
        if(neighborFloorUnit != null) {
            return neighborFloorUnit.isDirtPresent();
        }
        else if(currentFloorUnit.getBorder(direction) == Border.OPEN) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkIfPathCleanable(Direction direction, FloorUnit floorUnit) {
        FloorUnit neighborFloorUnit = floorGrid.getRelativeCoordinate(floorUnit.getPosition(), direction.getPosition());
        if(neighborFloorUnit != null) {
            if(neighborFloorUnit.isDirtPresent() == true) {
                return true;
            }
            else {
                return checkIfPathCleanable(direction, neighborFloorUnit);
            }
        }
        else if(floorUnit.getBorder(direction) == Border.OPEN) {
            return true;
        }
        else {
            return false;
        }
    }
}
