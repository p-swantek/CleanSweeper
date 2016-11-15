
package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.PathStatus;
import edu.se459grp4.project.simulator.types.TileStatus;


public class SurfaceSensor {
    public TileStatus GetSensorData(int x,int y)
    {
        return Simulator.getInstance().ProvideSurfaceSensorData(x, y);
    }
}
