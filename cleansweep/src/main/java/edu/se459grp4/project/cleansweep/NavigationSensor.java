
package edu.se459grp4.project.cleansweep;

//import edu.se459grp4.project.simulator.SensorSimulator;

import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;


public class NavigationSensor  {
    private Direction direction;

    public NavigationSensor( Direction direction) {
        this.direction = direction;
    }

    public PathStatus GetSensorData(int x,int y)
    {
        return Simulator.getInstance().ProvideDirectionSensroData(direction, x, y);
    }
   
}