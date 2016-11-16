package edu.se459grp4.project.cleansweep;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Deals with logging all the various aspects of the clean sweep as it cleans a floor. The logger
 * will create a text file that contains a log of the clean sweeps current position as it cleans a floor,
 * its power level, and the amount of dirt it currently has in its vacuum. Log messages are time stamped
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public final class Logger {

	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static final String ROOT_PATH = "Log/";
	private static final String MAIN_LOG_NAME = "MainLog.txt";
	private static final Logger instance = new Logger();
	
	private Logger(){}

	/**
	 * Gets the single instance of this singleton Logger
	 * 
	 * @return the instance of the logger
	 */
	public static Logger getInstance() {
		return instance;
	}

	private static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	/**
	 * Writes the clean sweep's current power to the BatteryLog.txt file
	 * 
	 * @param power the current power of the sweep to write to the log
	 */
	public static void writeToBatteryLog(double power) 
	{
		try{
		String filesub = "PowerManagement/BatteryLog.txt";
		String output = "Clean Sweep power status is "+power+".";
		writeToLogs(output, "POWER", filesub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Write to the DevicePowerLog.txt file whether or not the clean
	 * sweep is currently turned on
	 * 
	 * @param deviceOn whether or not the clean sweep is switched on
	 */
	public static void writeToDevicePowerLog(boolean deviceOn) 
	{
		try{
		String filesub = "PowerManagement/DevicePowerLog.txt";

		String status;
		if(deviceOn)
			status = "on";
		else
			status = "off";

		String output = "Clean Sweep was turned "+status+".";
		writeToLogs(output, "POWER", filesub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Write the status of how much dirt out of how much dirt maximum the clean sweep's 
	 * vacuum contains. If the dirt capacity is reached, the coordinates at which that
	 * occured are logged as well
	 * 
	 * @param currentDirt the current amount of dirt in the vacuum
	 * @param maxDirt the max dirt the vacuum can actually hold
	 * @param x the x coordinate of the clean sweep
	 * @param y the y coordinate of the clean sweep
	 */
	public static void writeToDirtCapacityLog(int currentDirt,int maxDirt,int x,int y) 
	{
		try{
		String filesub = "DirtManagement/DirtCapacityLog.txt";

		String output;
		if(maxDirt-currentDirt==0)
			output = "Clean Sweep dirt capacity has been reached("+currentDirt+"/"+maxDirt+") at tile ("+x+","+y+").";
		else
			output = "Clean Sweep is cleaning dirt("+currentDirt+"/"+maxDirt+") at tile ("+x+","+y+").";
		writeToLogs(output, "POWER", filesub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Writes if the clean sweep has detected dirt on a section of floor
	 * 
	 * @param dirtdensity the amount of dirt that is on the floor at this point
	 * @param x the x coordinate of the floor spot
	 * @param y the y coordinate of the floor spot
	 */
	public static void writeToDirtSensorLog(int dirtdensity,int x,int y)
	{
		try{
		String filesub = "Sensors/DirtSensorLog.txt";

		String output = "Clean Sweep dirt sensor has detected dirt("+dirtdensity+") at tile ("+x+","+y+").";
		writeToLogs(output, "DIRT", filesub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Writes if the clean sweep was able to find a path in the given direction
	 * 
	 * 
	 * @param direction the direction that was sensed
	 * @param directionStatus the status of the direction that was sensed
	 * @param x the x coordinate of the clean sweep
	 * @param y the y coordinate of the clean sweep
	 */
	public static void writeToNavigationSensorLog(String direction, String directionStatus,int x, int y) 
	{
		try{
		String filesub = "Sensors/NavigationSensorLog.txt";

		String output = "Clean Sweep Navigation sensor has detected Path("+direction+") is "+directionStatus+" at tile ("+x+","+y+").";
		writeToLogs(output, "NAVIGATION", filesub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the status when the clean sweep moves in a certain direction
	 * 
	 * @param direction the direction which the sweep moved
	 * @param x the x coordinate of the tile the sweep moved to
	 * @param y the y coordinate of the tile the sweep moved to
	 */
	public static void writeToMovementSensorFile(String direction,int x, int y) 
	{
		try{
		String filesub = "Sensors/MovementSensorLog.txt";

		String output = "Clean Sweep dirt sensor is moving one tile to the "+direction+" to tile ("+x+","+y+").";
		writeToLogs(output, "DIRT", filesub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Writes the status if the clean sweep detected a charging station at a certain tile
	 * 
	 * @param x the x coordinate at which the charge station was located
	 * @param y the y coordinate at which the charge station was located
	 */
	public static void chargeStationLog(int x,int y)
	{
		try{
		String filesub = "Sensors/ChargeStationSensorLog.txt";

		String output = "Clean sweep charge station sensor detected charging station at tile ("+x+","+y+").";
		writeToLogs(output, "CHARGING_STATION", filesub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Writes to the log when the clean sweep detects some obstacle while moving and which direction that
	 * obstacle is in
	 * 
	 * @param direction the direction of the obstacle
	 * @param x the x coordinate of the clean sweep
	 * @param y the y coordinate of the clean sweep
	 */
	public static void writeToObstacleSensorFile(String direction,int x, int y) 
	{
		try{
		String filesub = "Sensors/ObstacleSensorLog.txt";

		String output = "Clean Sweep obstacle sensor detected obstacle to the "+direction+" of tile ("+x+","+y+").";
		writeToLogs(output, "SENSOR", filesub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void writeToLogs(String output, String eventType, String subLogPath)
	{
		try{
		FileWriter fwm = null;
		FileWriter fws = null;

		String time=now();
		output = time + " " + eventType + " : " + output;

		File filemain = new File(ROOT_PATH + MAIN_LOG_NAME);
		writeToFile(filemain, output);

		if(subLogPath != null) {
			File filesub = new File(ROOT_PATH + subLogPath);
			writeToFile(filesub, output);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void writeToFile(File file, String output)
	{
		FileWriter fileWriter = null;

		try
		{
			if (file.exists()) {
				fileWriter = new FileWriter(file, true);
			} else {
				file.getParentFile().mkdirs();
				file.createNewFile();
				fileWriter = new FileWriter(file);
			}
			fileWriter.append(System.lineSeparator());
			fileWriter.append(output);
			fileWriter.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}


}
