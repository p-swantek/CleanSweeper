package edu.se459grp4.project.cleansweep.managers;

import java.util.Stack;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.types.Border;

public class FloorUnitTracker {
	
	private static final FloorUnitTracker instance = new FloorUnitTracker();

	private FloorUnitTracker(){};
	
	public FloorUnitTracker getInstance(){
		return instance;
	}
	
	private static Stack<FloorUnit> backTrack =new Stack<FloorUnit>();
	
	public static void add(FloorUnit f){
		backTrack.push(f);
	}
	public static FloorUnit returnLast()
	{
		FloorUnit last = backTrack.pop();
		return last;
	}
	public static boolean search(FloorUnit f)
	{
		if(backTrack.search(f)==-1)
		{
			return true;
		}
		else
			return false;
	}

}
