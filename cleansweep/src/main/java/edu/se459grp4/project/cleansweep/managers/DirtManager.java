package edu.se459grp4.project.cleansweep.managers;

import edu.se459grp4.project.cleansweep.logger.Logger;

import java.io.IOException;

import edu.se459grp4.project.cleansweep.*;
import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;

public class DirtManager {
	private static final DirtManager instance = new DirtManager();

	private DirtManager(){}

	private static int dirtMaxCapacity=100;
	private static int dirtCurrentCapacity=0;
	private static FloorEnvironment floorUnit;
	private static int x;
	private static int y;

	/*
	 * check if the dirtCapacity has been reached or not.
	 */
	public static boolean checkCapacity() {
		if (dirtCurrentCapacity>=dirtMaxCapacity)
			return true;
		else
			return false;
	}
	/*
	 * Assuming one unit of dirt is cleaned at a time
	 */
	public static void dirtCleaned(){
		if(checkCapacity()){
			x =floorUnit.getCurrentPosition().getX();
			y =floorUnit.getCurrentPosition().getY();
			dirtCurrentCapacity++;
			try {
				Logger.writeToDirtCapacityLog(dirtCurrentCapacity, dirtMaxCapacity, x, y);
			} catch (IOException e) {
				System.out.println("Could not write to dirtCapacityLog "+ dirtCurrentCapacity+"  "+dirtMaxCapacity+"  "+x+"  "+y);
			}
		}
		else
			emptyDirt();
	}
	/*
	 * Use this when floor shortest path to cleaner is available to empty the clean sweep
	 */
	public static void emptyDirt(){
		x =floorUnit.getCurrentPosition().getX();
		y =floorUnit.getCurrentPosition().getY();
		try {
			Logger.writeToDirtCapacityLog(dirtCurrentCapacity, dirtMaxCapacity, x, y);
		} catch (IOException e) {
			System.out.println("Could not write to dirtCapacityLog "+ dirtCurrentCapacity+"  "+dirtMaxCapacity+"  "+x+"  "+y);
		}
		dirtCurrentCapacity=0;
	}

}


