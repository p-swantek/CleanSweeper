package edu.se459grp4.project.cleansweep.navigation;

import java.lang.Thread.State;

import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.managers.FloorUnitTracker;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.types.Border;

public class NewNavigator  extends Navigator {
	
	private static State lastPosition=null;

	public NewNavigator(FloorEnvironment floorEnvironment) {
		super(floorEnvironment);
	}

	public Direction movementDirection(FloorUnit currentFloorUnit) {

		if (currentFloorUnit.getBorder(Direction.UP)==Border.OPEN && FloorUnitTracker.search(currentFloorUnit)) {
			lastPosition = State.SOUTH;
			FloorUnitTracker.add(currentFloorUnit);
			return Direction.UP;
		} 
		else 
			if(currentFloorUnit.getBorder(Direction.DOWN)==Border.OPEN && FloorUnitTracker.search(currentFloorUnit)) {
				lastPosition=State.NORTH;
				FloorUnitTracker.add(currentFloorUnit);
				return Direction.DOWN;
			}
			else
				if(currentFloorUnit.getBorder(Direction.RIGHT)==Border.OPEN && FloorUnitTracker.search(currentFloorUnit)) {
					lastPosition=State.WEST;
					FloorUnitTracker.add(currentFloorUnit);
					return Direction.RIGHT;
				}
				else
					if(currentFloorUnit.getBorder(Direction.LEFT)==Border.OPEN && FloorUnitTracker.search(currentFloorUnit)) {
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
