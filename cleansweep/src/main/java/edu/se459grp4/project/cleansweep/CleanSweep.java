package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.cleansweep.systems.ControlSystem;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.cleansweep.types.PathStatus;
import edu.se459grp4.project.simulator.FloorSimulator;

public class CleanSweep {
    private FloorSimulator floorSimulator;
    private ControlSystem controlSystem;

    private boolean running = false;
    //define the location tile coordinate
    private int mx;
    private int my;


    public CleanSweep(FloorSimulator floorSimulator)
    {
        this.floorSimulator = floorSimulator;
        this.controlSystem = new ControlSystem();
    }

    //get the x coordinate of this sweep
    public int getX()
    {
        return mx;
    }
    
    //get the y coordinate of this sweep
    public int getY()
    {
        return my;
    }
    
    //start this sweep
    public void start()
    {
        controlSystem.start();
        //set the inital status.
        //suppose all tiles are dirty
        //and it suppose start from a charge station and power is full and the vacuum capacity is empty.
        //so if the vacuum capacity value is not zero then start will fail.

        running = true;
        while(running) {
            //CleanSweep logic performed here for each tile encountered
            stop();
        }
    }
    
    //stop this sweep
    public boolean stop()
    {
        controlSystem.stop();
        running = false;
        return true;
    }
    
    //Check if I cam move to the next tile in the specific direction
    private PathStatus checkMove(Direction direction)
    {
        switch(direction)
        {
            case LEFT:
                return controlSystem.checkMoveLeft();
            case RIGHT:
                return controlSystem.checkMoveRight();
            case UP:
                return controlSystem.checkMoveUp();
            case DOWN:
                return controlSystem.checkMoveDown();
            default:
                return null;
        }
    }
    
    //move this sweep with one step in a spicific directions
    public boolean moveOneStep(Direction currentDirection)
    {
        PathStatus pathStatus = checkMove(currentDirection);
        if(pathStatus == PathStatus.OPEN)
        {
            // TODO: Call appropriate Simulator method to commit to movement
            switch(currentDirection)
            {
                case LEFT:
                    mx = mx-1;
                    break;
                case RIGHT:
                    mx = mx+1;
                    break;
                case UP:
                    my = my+1;
                    break;
                case DOWN:
                    my = my-1;
                    break;
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args)
    {
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
