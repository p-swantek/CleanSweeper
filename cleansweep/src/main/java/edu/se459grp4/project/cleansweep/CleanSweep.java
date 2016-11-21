package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;
import edu.se459grp4.project.simulator.types.TileStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


/**
 * Represents a CleanSweep vacuum robot.  The robot contains a vacuum to suck up dirt off the floor, 
 * as well as a variety of sensors to sense various aspects of the floor which it is cleaning.  The clean 
 * sweep can use sensor data to gather information on directions it can travel as well as the floor type
 * of the floor being traveled as well as the dirtiness of a spot on the floor. Clean sweep maintains
 * an internal power supply and needs to be recharged to keep working.  
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public class CleanSweep extends Observable {
	
	private static final double DEFAULT_POWER = 100.0;
	private static final int DEFAULT_VAC_CAPACITY = 1000;
	private static final int DEFAULT_X = 0;
	private static final int DEFAULT_Y = 0;
	
    private final int maxVacuumCapacity;
    private final double maxPowerCapacity;

    private final int id;
    private int currX;
    private int currY;
    private double currentPower;
    private int currentVacuumCapacity;
    
    //  each clean sweep has 4 navigation sensor, a dirt sensor, and a surface sensor
    private final NavigationSensor leftSensor = new NavigationSensor(Direction.Left);
    private final NavigationSensor rightSensor = new NavigationSensor(Direction.Right);
    private final NavigationSensor upSensor = new NavigationSensor(Direction.Up);
    private final NavigationSensor downSensor = new NavigationSensor(Direction.Down);
    private final DirtSensor dirtSensor = new DirtSensor();
    private final SurfaceSensor surfaceSensor = new SurfaceSensor();

    /**
     * Constructs a new CleanSweep. CleanSweep has a unique id number, a starting power level, vacuum capacity,
     * and a starting (x,y) coordinate
     * 
     * @param newId the id of this CleanSweep
     * @param powerAmount the starting power level of this CleanSweep
     * @param vacuumCapacity the max capacity of this CleanSweep's vacuum
     * @param newX starting coordinate x value
     * @param newY starting coordinate y value
     */
    public CleanSweep(int newId, double powerAmount, int vacuumCapacity, int newX, int newY){
        currentPower = powerAmount;
        maxPowerCapacity = powerAmount;
        currentVacuumCapacity = vacuumCapacity;
        maxVacuumCapacity = vacuumCapacity;
        id = newId;
        currX = newX;
        currY = newY;
    }
    
    /**
     * Create a clean sweep with the given id, sets the power level and vacuum capacity to default
     * stating values of 100.0 and 1000 respectively. Will set the location to be a default of
     * (0,0)
     * 
     * @param newId the id of this clean sweep
     */
    public CleanSweep(int newId){
    	this(newId, DEFAULT_POWER, DEFAULT_VAC_CAPACITY, DEFAULT_X, DEFAULT_Y);
    }
    
    
    /**
     * Get the current x coordinate of this sweep
     * 
     * @return the x coordinate
     */
    public synchronized int getX(){
        return currX;
    }

    /**
     * Get the current y coordinate of this sweep
     * 
     * @return the y coordinate
     */
    public synchronized int getY(){
        return currY;
    }
    
    /**
     * Get the id number of this sweep
     * 
     * @return the int id number of the sweep
     */
    public int getID(){
        return id;
    }

    /**
     * Generates a list of directions that can be traveled from the current location. 
     * A direction can be traveled if the path status of that direction is open
     * 
     * @return the list of possible directions
     */
    public List<Direction> getValidDirections(){
        List<Direction> results = new ArrayList<>();
        if (PathStatus.Open == checkAbleToMove(Direction.Left)) {
            results.add(Direction.Left);
        }
        if (PathStatus.Open == checkAbleToMove(Direction.Up)) {
            results.add(Direction.Up);
        }
        if (PathStatus.Open == checkAbleToMove(Direction.Right)) {
            results.add(Direction.Right);
        }

        if (PathStatus.Open == checkAbleToMove(Direction.Down)) {
            results.add(Direction.Down);
        }
        return results;

    }
    
    /**
     * Generates a list of the directions that can not be traveled based on the 
     * current location. A direction can't be traveled if the path status in that
     * direction is not open
     * 
     * @return the list of directions in which the sweep can't travel
     */
    public List<Direction> getInvalidDirections(){
        List<Direction> results = new ArrayList<>();
        if (PathStatus.Open != checkAbleToMove(Direction.Left)) {
            results.add(Direction.Left);
        }
        if (PathStatus.Open != checkAbleToMove(Direction.Up)) {
            results.add(Direction.Up);
        }
        if (PathStatus.Open != checkAbleToMove(Direction.Right)) {
            results.add(Direction.Right);
        }

        if (PathStatus.Open != checkAbleToMove(Direction.Down)) {
            results.add(Direction.Down);
        }
        return results;

    }
    
    /**
     * Checks a direction to see if you can move in that direction. Queries the
     * sensors to get the status of the path to the given direction.
     * 
     * @param nDirection the movement direction that should be checked
     * @return the PathStatus for the given direction
     * @see PathStatus
     */
    public synchronized PathStatus checkAbleToMove(Direction nDirection){
        if (nDirection == Direction.Left) {
            return leftSensor.getSensorData(currX, currY);
        }
        if (nDirection == Direction.Right) {
            return rightSensor.getSensorData(currX, currY);
        }
        if (nDirection == Direction.Up) {
            return upSensor.getSensorData(currX, currY);
        }
        if (nDirection == Direction.Down) {
            return downSensor.getSensorData(currX, currY);
        }

        return PathStatus.UNKNOWN;
    }


    /**
     * Tells whether the sweep can move to the given x and y coordinates. sweep will update its position
     * if the move is successful
     * 
     * @param x the new x coordinate
     * @param y the new y coordinate
     * @return true if the move succeeded, false otherwise
     */
    public synchronized boolean moveToLoc(int x, int y){
    	
    	if (Math.abs(getX()-x) > 1 || Math.abs(getY()-y) > 1){
    		throw new IllegalArgumentException("moveToLoc() got passed an invalid x or y coordinate.");
    	}
        
        if(x == currX){
            if(PathStatus.Open != checkAbleToMove(y < currY ? Direction.Up : Direction.Down)){
                  return false;
            }
        }
        else{
        	if(PathStatus.Open != checkAbleToMove(x < currX ? Direction.Left : Direction.Right)){
                  return false;
        	}
        }
        currX = x;
        currY = y;
        setChanged();
        notifyObservers(this);
        return true;
    }

    /**
     * Use the surface sensor to get the floor tile type of its current location
     * 
     * @return the TileStatus of the current floor tile on which the sweep is located
     * @see TileStatus
     */
    public TileStatus senseFloorSurface(){
        return surfaceSensor.getSensorData(currX, currY);
    }

    /**
     * Use the dirt sensor to get the dirt amount on its current location
     * 
     * @return an integer representing the amount of dirt on the floor tile
     */
    public int senseDirtAmount(){
        return dirtSensor.getSensorData(currX, currY);
    }

    /**
     * Have the sweep clean up an amount of dirt from its location
     * 
     * @param toRemove the amount of dirt the sweep up
     * @return the amount of dirt that was succesfully removed from the sweeps position
     * @see Simulator
     */
    public synchronized int cleanDirt(int toRemove){
        int dirtRemoved = Simulator.getInstance().SweepUp(currX, currY, toRemove);
        fillUpVacuum(dirtRemoved);
        return dirtRemoved;
    }

    /**
     * Gets the current power remaining in this sweep
     * 
     * @return a double representing the current power level
     */
    public synchronized double getCurrPower(){
        return currentPower;
    }
    
    /**
     * Have this clean sweep expend some of its power supply
     * 
     * @param powerUsed the amount of power to decrease from the current power supply
     * @return the new amount of power remaining after exhausing resources
     */
    public synchronized double usePowerAmount(double powerUsed){
        currentPower -= powerUsed;
        setChanged();
        notifyObservers(this);
        Logger.writeToBatteryLog(currentPower);
        return currentPower;
    }
    
    /**
     * Get the vacuum capacity for this sweep
     * 
     * @return the the sweep's vacuum's capacity
     */
    public synchronized int getCurrVacuumCapacity(){
        return currentVacuumCapacity;
    }
    
    /**
     * Decrease the amount of space inside the sweep's vacuum 
     *
     * @param amountAdded the amount of space to decrease the vacuum's capacity by
     * @return the new capacity of the vacuum
     */
    public synchronized int fillUpVacuum(int amountAdded){
        currentVacuumCapacity -= amountAdded;
        setChanged();
        notifyObservers(this);
		Logger.writeToDirtSensorLog(amountAdded, currX, currY);
		Logger.writeToDirtCapacityLog(currentVacuumCapacity, maxVacuumCapacity, currX, currY);
        return currentVacuumCapacity;
    }
    
   
    /**
     * Empty the vacuum, reset its capacity to the default max capacity
     * 
     * @return the capacity of the vacuum after it has been emptied
     */
    public synchronized int emptyVacuum(){
        currentVacuumCapacity = maxVacuumCapacity;
        setChanged();
        notifyObservers(this);
		Logger.writeToDirtCapacityLog(0, maxVacuumCapacity, currX, currY);
        return currentVacuumCapacity;
    }
    
    /**
     * Recharge the power on this sweep to the default max power level
     * 
     * @return the amount of power in the sweep after it has been recharged
     */
     public synchronized double rechargePower(){
        currentPower = maxPowerCapacity;
        setChanged();
        notifyObservers(this);
		Logger.writeToBatteryLog(maxPowerCapacity);
        return currentPower;
    }

	@Override
	public int hashCode() {
		int result = 17;
		return 31 * result + getID();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof CleanSweep)){
			return false;
		}
		
		else if (obj == this){
			return true;
		}
		CleanSweep otherCS = (CleanSweep)obj;
		return getID() == otherCS.getID();
	}

	@Override
	public String toString() {
		return String.format("CleanSweep[ID: %d]\nPower Status --> %.2f/%.2f\nVacuum Status --> %d/%d", getID(), getCurrPower(), maxPowerCapacity, getCurrVacuumCapacity(), maxVacuumCapacity);
	}
     
     
   
}
