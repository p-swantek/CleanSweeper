package edu.se459grp4.project.cleansweep.environment;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.models.Position;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.types.Border;

public class FloorEnvironment extends FloorGrid {
	private static Position previousPosition;
	private static Position currentPosition;
	private FloorUnit currentFloorUnit;

	public FloorEnvironment(int maxXRange, int maxYRange) {
		super(maxXRange, maxYRange);
		this.currentPosition = new Position(0, 0);

	}

	public void addFloorUnit(FloorUnit floorUnit) {
		add(floorUnit, currentPosition);
		currentFloorUnit = floorUnit;
	}

	public void updatePosition(Direction movementDirection) {
		previousPosition = currentPosition;
		currentPosition = Position.getRelativePosition(currentPosition, movementDirection.getPosition());
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public FloorUnit getCurrentFloorUnit() {
		return currentFloorUnit;
	}

	public boolean checkIfCleanable(Direction direction) {
		FloorUnit neighborFloorUnit = getRelativeCoordinate(currentPosition, direction.getPosition());
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
	
			if(floorUnit.getBorder(direction) == Border.OPEN) {
				return true;
			}
			else {
				return false;
			}
	
	}
}
