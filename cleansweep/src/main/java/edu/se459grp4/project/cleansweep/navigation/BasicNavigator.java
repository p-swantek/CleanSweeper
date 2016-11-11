package edu.se459grp4.project.cleansweep.navigation;

import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.managers.FloorUnitTracker;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;

/**
 * Determines which direction to move following a basic pattern.
 * Attempts to move up as far as possible, then right one, then
 * down as far as possible, then right one again. If unable to move
 * right then switches default to left one.
 */
public class BasicNavigator extends Navigator {

	private int retry = 0;
	private State lastPosition;

	public BasicNavigator(FloorEnvironment floorEnvironment) {
		super(floorEnvironment);
	}

	public Direction movementDirection(FloorUnit currentFloorUnit) {

		if(retry == 2) {
			return null;
		}

		if(lastPosition != State.NORTH && floorEnvironment.checkIfPathCleanable(Direction.UP, currentFloorUnit)) {

			lastPosition = State.SOUTH;
			FloorUnitTracker.add(currentFloorUnit,Direction.DOWN);
			return Direction.UP;
		}
			else {

				if(lastPosition != State.SOUTH && floorEnvironment.checkIfPathCleanable(Direction.DOWN, currentFloorUnit)) {

					lastPosition = State.NORTH;
					FloorUnitTracker.add(currentFloorUnit,Direction.UP);
					return Direction.DOWN;
				} else {

					if(lastPosition != State.EAST && floorEnvironment.checkIfPathCleanable(Direction.RIGHT, currentFloorUnit)) {

						lastPosition = State.WEST;
						FloorUnitTracker.add(currentFloorUnit,Direction.LEFT);
						return Direction.RIGHT;
					} else {
						if(lastPosition != State.WEST && floorEnvironment.checkIfPathCleanable(Direction.LEFT, currentFloorUnit)) {
							lastPosition = State.EAST;
							FloorUnitTracker.add(currentFloorUnit,Direction.RIGHT);
							return Direction.LEFT;
						} else {

							return null;
						}
					}
				}
			}
		}
	
			
			

	private enum State {
		NORTH, EAST, WEST, SOUTH
	}
	}