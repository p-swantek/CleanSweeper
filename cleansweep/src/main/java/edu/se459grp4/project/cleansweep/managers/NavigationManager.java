package edu.se459grp4.project.cleansweep.managers;

import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.navigation.BasicNavigator;
import edu.se459grp4.project.cleansweep.navigation.Navigator;
import edu.se459grp4.project.cleansweep.navigation.ReturnToBaseStation;
import edu.se459grp4.project.cleansweep.navigation.ShortestPath;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.FloorSimulator;

public class NavigationManager {
	private FloorSimulator floorSimulator;
	private FloorEnvironment floorEnvironment;
	private PowerManager powerManager;
	private Navigator navigator;
	private ShortestPath shortPath;

	

	public NavigationManager(FloorSimulator floorSimulator, FloorEnvironment floorEnvironment, PowerManager powerManager) {
		this.navigator = new BasicNavigator(floorEnvironment);
		this.floorSimulator = floorSimulator;
		this.floorEnvironment = floorEnvironment;
		this.powerManager = powerManager;
	}

	public Direction move(FloorUnit currentFloorUnit) {

		if(DirtManager.checkCapacity() || powerManager.checkPower())
		{
			this.shortPath= new ShortestPath(currentFloorUnit,floorEnvironment.getPowerStation(),floorEnvironment);
			Direction direction =shortPath.movementDirection(currentFloorUnit);
			floorSimulator.move(direction.getValue());
			return direction;
		}else
		{
		Direction direction = navigator.movementDirection(currentFloorUnit);
		floorSimulator.move(direction.getValue());
		return direction;
		}
	}


}
