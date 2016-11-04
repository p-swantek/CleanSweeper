package edu.se459grp4.project.cleansweep.sensors;

import java.io.IOException;

import edu.se459grp4.project.cleansweep.logger.Logger;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.FloorSimulator;
import edu.se459grp4.project.simulator.types.Border;

public class NavigationSensor implements Sensor {
    private FloorSimulator floorSimulator;
    private Direction direction;

    public NavigationSensor(FloorSimulator floorSimulator, Direction direction) {
    	this.floorSimulator = floorSimulator;
        this.direction = direction;
    }

    public boolean update(FloorUnit floorUnit) {
        boolean success = false;

		switch(direction) {
			case UP:
				floorUnit.setNorthBorder(floorSimulator.getBorder(direction.getValue()));
				success = true;
				break;
			case DOWN:
				floorUnit.setSouthBorder(floorSimulator.getBorder(direction.getValue()));
				success = true;
				break;
			case LEFT:
				floorUnit.setWestBorder(floorSimulator.getBorder(direction.getValue()));
				success = true;
				break;
			case RIGHT:
				floorUnit.setEastBorder(floorSimulator.getBorder(direction.getValue()));
				success = true;
				break;
		}

    	try {
			Logger.writeToNavigationSensorLog(direction.getValue(), String.valueOf(success), floorSimulator.getCleanSweepPosition().x , floorSimulator.getCleanSweepPosition().y);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return success;
    }

}
