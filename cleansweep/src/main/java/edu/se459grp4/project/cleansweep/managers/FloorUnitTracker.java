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
	
	public FloorUnit backTrack(){
		for(FloorUnit f:backTrack)
		{
			
			if(f.getNorthBorder()==Border.OPEN||f.getSouthBorder()==Border.OPEN||f.getEastBorder()==Border.OPEN||f.getWestBorder()==Border.OPEN)
				{
				return f;
				}
			else
				backTrack.pop();
				
		}
		return null;					
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
