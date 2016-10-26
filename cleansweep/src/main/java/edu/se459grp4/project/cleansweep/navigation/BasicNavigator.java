package edu.se459grp4.project.cleansweep.navigation;

import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;

public class BasicNavigator extends Navigator {
    private State state = State.NORTH;
    private int retry = 0;

    public BasicNavigator(FloorEnvironment floorEnvironment) {
        super(floorEnvironment);
    }

    public Direction movementDirection(FloorUnit currentFloorUnit) {
        State lastVertical = State.NORTH;
        State defaultHorizontal = State.EAST;

        if(retry == 2) {
            return null;
        }

        if(state == State.NORTH) {
            if (floorEnvironment.checkIfPathCleanable(Direction.UP, currentFloorUnit)) {
                lastVertical = State.NORTH;
                return Direction.UP;
            } else {
                state = defaultHorizontal;
            }
        }
        if(state == State.SOUTH) {
            if (floorEnvironment.checkIfPathCleanable(Direction.DOWN, currentFloorUnit)) {
                lastVertical = State.SOUTH;
                return Direction.DOWN;
            } else {
                state = defaultHorizontal;
            }
        }
        if(state == State.EAST) {
            if (floorEnvironment.checkIfCleanable(Direction.RIGHT)) {
                if (lastVertical == State.NORTH) {
                    state = State.SOUTH;
                }
                else {
                    state = State.NORTH;
                }
                retry = 0;
                return Direction.RIGHT;
            }
            else {
                defaultHorizontal = State.WEST;
                state = defaultHorizontal;
                retry++;
            }
        }
        if(state == State.WEST) {
            if(floorEnvironment.checkIfCleanable(Direction.LEFT)) {
                if (lastVertical == State.NORTH) {
                    state = State.SOUTH;
                }
                else {
                    state = State.NORTH;
                }
                retry = 0;
                return Direction.LEFT;
            }
            else {
                defaultHorizontal = State.EAST;
                state = defaultHorizontal;
                retry++;
            }
        }

        return null;
    }

    private enum State {
        NORTH, EAST, WEST, SOUTH
    }
}
