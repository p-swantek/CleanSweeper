package edu.se459grp4.project.cleansweep;
import edu.se459grp4.project.cleansweep.logger.Logging;

import java.io.IOException;


public class LogTest {

	public static void main(String[] args) throws IOException {
		Logging.writeToBatteryLog(30);
		Logging.writeToBatteryLog(20);
		Logging.writeToDevicePowerLog(true);
		Logging.writeToDevicePowerLog(false);
		Logging.writeToObstacleSensorFile("north", 2, 4);
		Logging.writeToDirtSensorLog(5, 2, 2);
		Logging.writeToDirtCapacityLog(50, 50, 2, 2);
		Logging.writeToObstacleSensorFile("north", 2, 2);
		Logging.writeToMovementSensorFile("east", 2, 2);
	}

}
