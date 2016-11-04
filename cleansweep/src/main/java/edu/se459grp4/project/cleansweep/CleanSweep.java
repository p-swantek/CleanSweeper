package edu.se459grp4.project.cleansweep;

import java.io.IOException;

import edu.se459grp4.project.cleansweep.environment.FloorEnvironment;
import edu.se459grp4.project.cleansweep.managers.NavigationManager;
import edu.se459grp4.project.cleansweep.managers.PowerManager;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.models.Position;
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
    private FloorEnvironment floorEnvironment;
    private DirtSensor dirtSensor;
    private FloorSensor floorSensor;
    private boolean running = false;
    private final int LOOP_MS = 1000;

    public CleanSweep(FloorSimulator floorSimulator) {
        this.floorSimulator = floorSimulator;
        this.powerManager = PowerManager.getInstance();
        this.floorEnvironment = new FloorEnvironment(2000, 2000);
        this.navigationManager = new NavigationManager(floorSimulator, floorEnvironment, powerManager);
        this.navigationSensorSystem = new NavigationSensorSystem(floorSimulator);
        this.dirtSensor = new DirtSensor(floorSimulator);
        this.floorSensor = new FloorSensor(floorSimulator);
    }
    
    //start this sweep
    public void start() throws IOException {
        running = true;
        FloorUnit previousFloorUnit = null;
        FloorUnit currentFloorUnit = null;
        Position currentPosition = null;
        Direction nextDirection = null;
        while(running) {
            if(currentFloorUnit == null) {
                currentFloorUnit = new FloorUnit();
            }
            else {
                previousFloorUnit = currentFloorUnit;
                currentFloorUnit = new FloorUnit();
            }
            floorEnvironment.addFloorUnit(currentFloorUnit);
            navigationSensorSystem.update(currentFloorUnit);
            floorSensor.update(currentFloorUnit);
            dirtSensor.update(currentFloorUnit);
            floorEnvironment.addFloorUnit(currentFloorUnit);
            powerManager.updatePower(previousFloorUnit, currentFloorUnit);
            nextDirection = navigationManager.move(currentFloorUnit);
            if(nextDirection!=null)
            floorEnvironment.updatePosition(nextDirection);

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

    public static void main(String[] args) throws IOException {
        String fileLocation = null;
        if(args.length > 0) {
            fileLocation = args[0];
        }
        // Uses default floor plan file if none provided
        FloorSimulator floorSimulator = new FloorSimulator(0, 0, fileLocation); //start at (0,0)
        CleanSweep cleanSweep = new CleanSweep(floorSimulator);
        cleanSweep.start();
    }

}
