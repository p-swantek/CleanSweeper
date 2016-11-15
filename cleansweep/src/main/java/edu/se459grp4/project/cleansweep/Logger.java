package edu.se459grp4.project.cleansweep;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public final class Logger {

	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static final String ROOT_PATH = "Log/";
	private static final String MAIN_LOG_NAME = "MainLog.txt";
	private static final Logger instance = new Logger();
	
	private Logger(){}

	public static Logger getInstance() {
		return instance;
	}

	private static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

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
