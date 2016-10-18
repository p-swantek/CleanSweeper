package edu.se459grp4.project.cleansweep.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public final class Logging {

	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static final Logging instance = new Logging();
	private Logging(){

	}
	public static Logging getInstance() {
		return instance;
	}
	private static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	public static void writeToBatteryLog(int power) throws IOException
	{
		File filesub = new File("Log/PowerManagement/BatteryLog.txt");
		File filemain = new File("Log/MainLog.txt");
		FileWriter fwm = null;
		FileWriter fws = null;

		if(filesub.exists())
		{
			fws = new FileWriter(filesub,true);
		}
		else
		{
			filesub.getParentFile().mkdirs();
			filesub.createNewFile();
			fws = new FileWriter(filesub);
		}
		if(filemain.exists())
		{
			fwm = new FileWriter(filemain,true);
		}
		else
		{
			filemain.getParentFile().mkdirs();
			filemain.createNewFile();
			fwm = new FileWriter(filemain);
		}
		String time=now();
		String output = "<"+time+"> Clean Sweep power status is "+power+".";
		fws.append(System.lineSeparator());
		fws.append(output);
		fwm.append(System.lineSeparator());
		fwm.append(output);
		fws.close();
		fwm.close();
	}

	public static void writeToDevicePowerLog(boolean deviceOn) throws IOException
	{

		File filesub = new File("Log/PowerManagement/DevicePowerLog.txt");
		File filemain = new File("Log/MainLog.txt");
		FileWriter fwm = null;
		FileWriter fws = null;

		String status;
		if(deviceOn)
			status = "on";
		else
			status = "off";
		
		if(filesub.exists())
		{
			fws = new FileWriter(filesub,true);
		}
		else
		{
			filesub.getParentFile().mkdirs();
			filesub.createNewFile();
			fws = new FileWriter(filesub);
		}
		if(filemain.exists())
		{
			fwm = new FileWriter(filemain,true);
		}
		else
		{
			filemain.getParentFile().mkdirs();
			filemain.createNewFile();
			fwm = new FileWriter(filemain);
		}

		String time=now();
		String output = "<"+time+"> Clean Sweep was turned "+status+".";
		fws.append(System.lineSeparator());
		fws.append(output);
		fwm.append(System.lineSeparator());
		fwm.append(output);
		fws.close();
		fwm.close();
	}
	public static void writeToDirtCapacityLog(int currentDirt,int maxDirt,int x,int y) throws IOException
	{
		File filesub = new File("Log/DirtManagement/DirtCapacityLog.txt");
		File filemain = new File("Log/MainLog.txt");
		FileWriter fwm = null;
		FileWriter fws = null;
		String output;

		if(filesub.exists())
		{
			fws = new FileWriter(filesub,true);
		}
		else
		{
			filesub.getParentFile().mkdirs();
			filesub.createNewFile();
			fws = new FileWriter(filesub);
		}
		if(filemain.exists())
		{
			fwm = new FileWriter(filemain,true);
		}
		else
		{
			filemain.getParentFile().mkdirs();
			filemain.createNewFile();
			fwm = new FileWriter(filemain);
		}

		String time=now();
		if(maxDirt-currentDirt==0)
			output = "<"+time+"> Clean Sweep dirt capacity has been reached("+currentDirt+"/"+maxDirt+") at tile ("+x+","+y+").";
		else
			output = "<"+time+"> Clean Sweep is cleaning dirt("+currentDirt+"/"+maxDirt+") at tile ("+x+","+y+").";
		fws.append(System.lineSeparator());
		fws.append(output);
		fwm.append(System.lineSeparator());
		fwm.append(output);
		fws.close();
		fwm.close();
	}
	public static void writeToDirtSensorLog(int dirtdensity,int x,int y) throws IOException
	{
		File filesub = new File("Log/Sensors/DirtSensorLog.txt");
		File filemain = new File("Log/MainLog.txt");
		FileWriter fwm = null;
		FileWriter fws = null;
		String output;

		if(filesub.exists())
		{
			fws = new FileWriter(filesub,true);
		}
		else
		{
			filesub.getParentFile().mkdirs();
			filesub.createNewFile();
			fws = new FileWriter(filesub);
		}
		if(filemain.exists())
		{
			fwm = new FileWriter(filemain,true);
		}
		else
		{
			filemain.getParentFile().mkdirs();
			filemain.createNewFile();
			fwm = new FileWriter(filemain);
		}

		String time=now();
		
		output = "<"+time+"> Clean Sweep dirt sensor has detected dirt("+dirtdensity+") at tile ("+x+","+y+").";
		
		fws.append(System.lineSeparator());
		fws.append(output);
		fwm.append(System.lineSeparator());
		fwm.append(output);
		fws.close();
		fwm.close();
	}
	public static void writeToMovementSensorFile(String direction,int x, int y) throws IOException
	{
		File filesub = new File("Log/Sensors/MovementSensorLog.txt");
		File filemain = new File("Log/MainLog.txt");
		FileWriter fwm = null;
		FileWriter fws = null;
		String output;

		if(filesub.exists())
		{
			fws = new FileWriter(filesub,true);
		}
		else
		{
			filesub.getParentFile().mkdirs();
			filesub.createNewFile();
			fws = new FileWriter(filesub);
		}
		if(filemain.exists())
		{
			fwm = new FileWriter(filemain,true);
		}
		else
		{
			filemain.getParentFile().mkdirs();
			filemain.createNewFile();
			fwm = new FileWriter(filemain);
		}

		String time=now();
		
		output = "<"+time+"> Clean Sweep dirt sensor is moving one tile to the "+direction+" to tile ("+x+","+y+").";
		
		fws.append(System.lineSeparator());
		fws.append(output);
		fwm.append(System.lineSeparator());
		fwm.append(output);
		fws.close();
		fwm.close();
	}
	public static void chargeStationLog(int x,int y) throws IOException
	{
		File filesub = new File("Log/Sensors/ChargeStationSensorLog.txt");
		File filemain = new File("Log/MainLog.txt");
		FileWriter fwm = null;
		FileWriter fws = null;
		String output;

		if(filesub.exists())
		{
			fws = new FileWriter(filesub,true);
		}
		else
		{
			filesub.getParentFile().mkdirs();
			filesub.createNewFile();
			fws = new FileWriter(filesub);
		}
		if(filemain.exists())
		{
			fwm = new FileWriter(filemain,true);
		}
		else
		{
			filemain.getParentFile().mkdirs();
			filemain.createNewFile();
			fwm = new FileWriter(filemain);
		}

		String time=now();
		
		output = "<"+time+"> Clean sweep charge station sensor detected charging station at tile ("+x+","+y+").";
		
		fws.append(System.lineSeparator());
		fws.append(output);
		fwm.append(System.lineSeparator());
		fwm.append(output);
		fws.close();
		fwm.close();
	}
	public static void writeToObstacleSensorFile(String direction,int x, int y) throws IOException
	{
		File filesub = new File("Log/Sensors/ObstacleSensorLog.txt");
		File filemain = new File("Log/MainLog.txt");
		FileWriter fwm = null;
		FileWriter fws = null;
		String output;
		if(filesub.exists())
		{
			fws = new FileWriter(filesub,true);
		}
		else
		{
			filesub.getParentFile().mkdirs();
			filesub.createNewFile();
			fws = new FileWriter(filesub);
		}
		if(filemain.exists())
		{
			fwm = new FileWriter(filemain,true);
		}
		else
		{
			filemain.getParentFile().mkdirs();
			filemain.createNewFile();
			fwm = new FileWriter(filemain);
		}

		String time=now();
		
		output = "<"+time+"> Clean Sweep obstacle sensor detected obstacle to the "+direction+" of tile ("+x+","+y+").";
		
		fws.append(System.lineSeparator());
		fws.append(output);
		fwm.append(System.lineSeparator());
		fwm.append(output);
		fws.close();
		fwm.close();
	}


}


