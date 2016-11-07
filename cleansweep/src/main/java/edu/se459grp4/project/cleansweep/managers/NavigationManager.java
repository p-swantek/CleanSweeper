package edu.se459grp4.project.cleansweep.managers;

import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.navigation.BasicNavigator;
import edu.se459grp4.project.cleansweep.navigation.Navigator;
import edu.se459grp4.project.cleansweep.navigation.ShortestPath;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.FloorSimulator;

public class NavigationManager {
	private FloorSimulator floorSimulator;
	private FloorEnvironment floorEnvironment;
	private PowerManager powerManager;
	private Navigator navigator;
	private ShortestPath navShortestPath;
	private Direction direction;


	public NavigationManager(FloorSimulator floorSimulator, FloorEnvironment floorEnvironment, PowerManager powerManager) {
		this.navigator = new BasicNavigator(floorEnvironment);
		this.floorSimulator = floorSimulator;
		this.floorEnvironment = floorEnvironment;
		this.powerManager = powerManager;
	}

	public Direction move(FloorUnit currentFloorUnit) {
		
//		if(DirtManager.checkCapacity() || powerManager.checkPower()){
//			this.navShortestPath= new ShortestPath(currentFloorUnit,floorEnvironment.getPowerStation(), floorEnvironment );
//			Direction directionShortestPath = navShortestPath.movementDirection(currentFloorUnit);
//			return directionShortestPath;
//		}
		direction = navigator.movementDirection(currentFloorUnit);
		if(floorSimulator.move(direction.getValue()))
				{
					return direction;
				}
		else
			return null;
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}
}
