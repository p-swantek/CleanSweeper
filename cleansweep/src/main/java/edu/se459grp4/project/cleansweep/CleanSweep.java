package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.cleansweep.systems.ControlSystem;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.cleansweep.types.PathStatus;

public class CleanSweep {
    //define the location tile coordinate
    private int mx;
    private int my;

    //each sweep has got a powerful control system
    private ControlSystem mControlSystem= new ControlSystem();

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
    public boolean start()
    {
         mControlSystem.start();
         //set the inital status.
         //suppose all tiles are dirty
         //and it suppose start from a charge station and power is full and the vacuum capacity is empty.
         //so if the vacuum capacity value is not zero then start will fail.
         
         return true;
    }
    
    //stop this sweep
    public boolean stop()
    {
          mControlSystem.stop();
          return true;
    }
    
    //Check if I cam move to the next tile in the specific direction
    private PathStatus checkMove(Direction direction)
    {
        switch(direction)
        {
            case LEFT:
                return mControlSystem.checkMoveLeft();
            case RIGHT:
                return mControlSystem.checkMoveRight();
            case UP:
                return mControlSystem.checkMoveUp();
            case DOWN:
                return mControlSystem.checkMoveDown();
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

}
