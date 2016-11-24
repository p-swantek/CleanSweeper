package edu.se459grp4.project.cleansweep.sensors;

import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;
import edu.se459grp4.project.simulator.types.SurfaceType;

/**
 * Sensor panel that the clean sweep can access to get information from any of its internal sensors
 * 
 * @author Peter Swantek
 * @version 1.8
 *
 */
public class SensorPanel {
	
    // each clean sweep has 4 navigation sensor, a dirt sensor, and a surface sensor
    private final NavigationSensor leftSensor;
    private final NavigationSensor rightSensor;
    private final NavigationSensor upSensor;
    private final NavigationSensor downSensor;
    private final DirtSensor dirtSensor;
    private final SurfaceSensor surfaceSensor;
    
    
    /**
     * Initialize the sensor panel, panel contains navigator sensors, dirt sensor, and a floor surface sensor
     * 
     */
    public SensorPanel(){
    	leftSensor = new NavigationSensor(Direction.LEFT);
        rightSensor = new NavigationSensor(Direction.RIGHT);
        upSensor = new NavigationSensor(Direction.UP);
        downSensor = new NavigationSensor(Direction.DOWN);
        dirtSensor = new DirtSensor();
        surfaceSensor = new SurfaceSensor();
    }
    
    /**
     * Use the navigation sensor of the sensor panel
     * 
     * @param d the direction to sense
     * @param x the x coordinate of the current location
     * @param y the y coordinate of the current location
     * @return the PathStatus for the given direction based on the current location
     * @see PathStatus
     */
    public PathStatus useNavigationSensor(Direction d, int x, int y){
    	
    	PathStatus pathStatus = PathStatus.UNKNOWN;
    	if (d == null){
    		return pathStatus;
    	}
    	
    	switch (d){
    		case LEFT:
    			pathStatus = leftSensor.getSensorData(x, y);
    			break;
    		
    		case RIGHT:
    			pathStatus = rightSensor.getSensorData(x, y);
    			break;
    		
    		case UP:
    			pathStatus = upSensor.getSensorData(x, y);
    			break;
    		
    		case DOWN:
    			pathStatus = downSensor.getSensorData(x, y);
    			break;
    			
    		default:
    			break;
    	}

        return pathStatus;
    	
    }
    
    /**
     * Use the surface sensor of the sensor panel to sense the surface type of the floor
     * 
     * @param x the x coordinate of the current location
     * @param y the y coordinate of the current location
     * @return the SurfaceType of the floor at this location
     * @see SurfaceType
     */
    public SurfaceType useSurfaceSensor(int x, int y){
    	return surfaceSensor.getSensorData(x, y);
    }
    
    /**
     * Use the dirt sensor of the sensor panel to sense the amount of dirt on the floor at the
     * current location
     * 
     * @param x the x coordinate of the location to sense
     * @param y the y coordinate of the location to sense
     * @return the amount of dirt present at this location
     */
    public int useDirtSensor(int x, int y){
    	return dirtSensor.getSensorData(x, y);
    }
    
    
    

}
