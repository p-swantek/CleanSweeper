package edu.se459grp4.project.cleansweep.navigation;

import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;

public class ShortestPath extends Navigator {


	private FloorUnit start;
	private FloorUnit end;
	private FloorUnit powerStation;

	public ShortestPath(FloorUnit start,FloorUnit end, FloorEnvironment floorEnvironment)
	{
		super(floorEnvironment);
//		this.powerStation=floorEnvironment.getPowerStation();
		this.start=start;
		this.end=end;
	}


	@Override
	public Direction movementDirection(FloorUnit currentFloorUnit) {
		// TODO Auto-generated method stub
		return null;
	}




}
