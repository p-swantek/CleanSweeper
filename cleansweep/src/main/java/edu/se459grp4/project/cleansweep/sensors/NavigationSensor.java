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
		Border borderType = floorSimulator.getBorder(direction.getValue());

		switch(direction) {
			case UP:
				floorUnit.setNorthBorder(borderType);
				break;
			case DOWN:
				floorUnit.setSouthBorder(borderType);
				success = true;
				break;
			case LEFT:
				floorUnit.setWestBorder(borderType);
				success = true;
				break;
			case RIGHT:
				floorUnit.setEastBorder(borderType);
				success = true;
				break;
		}

    	try {
			Logger.writeToNavigationSensorLog(direction.getValue(), borderType.name(), floorSimulator.getCleanSweepPosition().x , floorSimulator.getCleanSweepPosition().y);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return success;
    }

}
