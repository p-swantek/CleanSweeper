package edu.se459grp4.project.sensors;

import edu.se459grp4.project.simulator.Simulator;


/**
 * A sensor on the clean sweep robot that can sense the amount of dirt from the floor
 * 
 * @author Group 4
 * @version 1.8
 *
 */
class DirtSensor {
    
	/**
	 * Sense the amount of dirt at the given x, y coordinate
	 * 
	 * @param x the x coordinate of the location to sense
	 * @param y the y coordinate of the location to sense
	 * @return the amount of dirt present at that location
	 */
    public int getSensorData(int x,int y){
        return Simulator.getInstance().getDirtData(x, y);
    }
}
