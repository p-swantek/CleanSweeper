package edu.se459grp4.project.cleansweep.sensors;


/**
 * 
 * Factory providing methods that will build the sensor panel portion of the clean sweep
 * 
 * @author Peter Swantek
 * @version 1.8
 *
 */
public class SensorPanelFactory {
	
	private SensorPanelFactory(){}
	
	/**
	 * Generate a new panel of sensors
	 * 
	 * @return a new SensorPanel
	 * @see SensorPanel
	 */
	public static SensorPanel buildPanel(){
		return new SensorPanel();
	}

}
