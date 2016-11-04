package edu.se459grp4.project.cleansweep.navigation;

import java.lang.Thread.State;

import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.managers.FloorUnitTracker;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;

public class NewNavigator  extends Navigator {
	
	private static State lastPosition=null;

	public NewNavigator(FloorEnvironment floorEnvironment) {
		super(floorEnvironment);
	}

	public Direction movementDirection(FloorUnit currentFloorUnit) {

		if (floorEnvironment.checkIfCleanable(Direction.UP) && lastPosition!=State.NORTH) {
			lastPosition = State.SOUTH;
			FloorUnitTracker.add(currentFloorUnit);
			return Direction.UP;
		} 
		else 
			if(floorEnvironment.checkIfCleanable(Direction.DOWN) && lastPosition!=State.SOUTH) {
				lastPosition=State.NORTH;
				FloorUnitTracker.add(currentFloorUnit);
				return Direction.DOWN;
			}
			else
				if(floorEnvironment.checkIfCleanable(Direction.RIGHT) && lastPosition!=State.EAST) {
					lastPosition=State.WEST;
					FloorUnitTracker.add(currentFloorUnit);
					return Direction.RIGHT;
				}
				else
					if(floorEnvironment.checkIfCleanable(Direction.LEFT) && lastPosition!=State.WEST) {
						lastPosition=State.EAST;
						FloorUnitTracker.add(currentFloorUnit);
						return Direction.LEFT;
					}
		return null;

	}

private enum State {
	NORTH, EAST, WEST, SOUTH
}

}
