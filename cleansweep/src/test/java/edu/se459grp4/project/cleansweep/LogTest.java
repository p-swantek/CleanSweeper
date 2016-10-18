package edu.se459grp4.project.cleansweep;
import edu.se459grp4.project.cleansweep.logger.Logger;

import java.io.IOException;


public class LogTest {

	public static void main(String[] args) throws IOException {
		Logger.writeToBatteryLog(30);
		Logger.writeToBatteryLog(20);
		Logger.writeToDevicePowerLog(true);
		Logger.writeToDevicePowerLog(false);
		Logger.writeToObstacleSensorFile("north", 2, 4);
		Logger.writeToDirtSensorLog(5, 2, 2);
		Logger.writeToDirtCapacityLog(50, 50, 2, 2);
		Logger.writeToObstacleSensorFile("north", 2, 2);
		Logger.writeToMovementSensorFile("east", 2, 2);
	}

}
