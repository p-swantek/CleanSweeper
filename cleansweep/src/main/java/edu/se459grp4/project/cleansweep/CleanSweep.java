package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.cleansweep.managers.NavigationManager;
import edu.se459grp4.project.cleansweep.managers.PowerManager;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.sensors.DirtSensor;
import edu.se459grp4.project.cleansweep.sensors.FloorSensor;
import edu.se459grp4.project.cleansweep.sensors.NavigationSensorSystem;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.FloorSimulator;

public class CleanSweep {
    private FloorSimulator floorSimulator;
    private PowerManager powerManager;
    private NavigationManager navigationManager;
    private NavigationSensorSystem navigationSensorSystem;
    private Environment environment;
    private DirtSensor dirtSensor;
    private FloorSensor floorSensor;
    private boolean running = false;
    private final int LOOP_MS = 1000;

    public CleanSweep(FloorSimulator floorSimulator) {
        this.floorSimulator = floorSimulator;
        this.powerManager = new PowerManager(100, 100, 30);
        this.environment = new Environment();
        this.navigationManager = new NavigationManager(floorSimulator, environment, powerManager);
        this.navigationSensorSystem = new NavigationSensorSystem(floorSimulator);
        this.dirtSensor = new DirtSensor(floorSimulator);
        this.floorSensor = new FloorSensor(floorSimulator);
    }
    
    //start this sweep
    public void start() {
        running = true;
        FloorUnit previousFloorUnit = null;
        FloorUnit currentFloorUnit = null;
        Direction nextDirection = null;
        while(running) {
            if(currentFloorUnit == null) {
                currentFloorUnit = new FloorUnit();
            }
            else {
                previousFloorUnit = currentFloorUnit;
                currentFloorUnit = new FloorUnit();
            }

            navigationSensorSystem.update(currentFloorUnit);
            floorSensor.update(currentFloorUnit);
            dirtSensor.update(currentFloorUnit);
            powerManager.updatePower(previousFloorUnit, currentFloorUnit);
            environment.addFloorUnit(currentFloorUnit);
            nextDirection = navigationManager.move();
            environment.updatePosition(nextDirection);

            try {
                Thread.sleep(LOOP_MS);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    //stop this sweep
    public boolean stop() {
        running = false;
        return true;
    }

    public static void main(String[] args) {
        String fileLocation = null;
        if(args.length > 0) {
            fileLocation = args[0];
        }
        // Uses default floor plan file if none provided
        FloorSimulator floorSimulator = new FloorSimulator(0, 1, fileLocation);
        CleanSweep cleanSweep = new CleanSweep(floorSimulator);
        cleanSweep.start();
    }

}
