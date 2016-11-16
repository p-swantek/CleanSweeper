package edu.se459grp4.project.cleansweep;



import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;

/**
 * A navigation sensor that is part of the clean sweep. A navigation sensor is associated
 * with a direction and is responsible for providing data
 * 
 * @author Group 4
 * @version 1.8
 */
public class NavigationSensor  {
	
    private Direction direction;

    /**
     * Constructs a navigation sensor to sense the given direction
     * 
     * @param direction the direction that this sensor gets sensor data from
     */
    public NavigationSensor( Direction direction) {
        this.direction = direction;
    }

    /**
     * Sense in a certain direction to check the path status of that direction
     * 
     * @param x the x coordinate of the floor spot to sense
     * @param y the y coordinate of the floor spot to sense
     * @return the path status of the direction that is sensed by this sensor at the given x,y coordinate
     * @see PathStatus
     */
    public PathStatus GetSensorData(int x,int y)
    {
        return Simulator.getInstance().ProvideDirectionSensorData(direction, x, y);
    }
   
}