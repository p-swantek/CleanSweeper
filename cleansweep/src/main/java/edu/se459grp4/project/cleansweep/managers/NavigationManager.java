package edu.se459grp4.project.cleansweep.managers;

import edu.se459grp4.project.cleansweep.Environment;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.models.Position;
import edu.se459grp4.project.cleansweep.navigation.BasicNavigator;
import edu.se459grp4.project.cleansweep.navigation.Navigator;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.FloorSimulator;

public class NavigationManager {
    private FloorSimulator floorSimulator;
    private Environment environment;
    private PowerManager powerManager;
    private Navigator navigator;

    public NavigationManager(FloorSimulator floorSimulator, Environment environment, PowerManager powerManager) {
        this.navigator = new BasicNavigator(environment);
        this.floorSimulator = floorSimulator;
        this.environment = environment;
        this.powerManager = powerManager;
    }

    public Direction move(FloorUnit currentFloorUnit) {
        // TODO: add logic to set Navigator
        return navigator.movementDirection(currentFloorUnit);
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
