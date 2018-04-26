package edu.se459grp4.project.gui.drawables;

import edu.se459grp4.project.cleansweep.CleanSweep;
import edu.se459grp4.project.simulator.models.Door;
import edu.se459grp4.project.simulator.models.Tile;
import edu.se459grp4.project.simulator.models.Wall;

/**
 * Factory methods that create various drawable items that can be drawn on the gui
 * 
 * @author Peter Swantek
 * @version 1.8
 *
 */
public class DrawableFactory {
	
	private DrawableFactory(){} //don't instantiate
	
	/**
	 * Get the drawable version of a floor tile
	 * 
	 * @param tile the tile to represent
	 * @return a graphical version of the tile
	 */
	public static Drawable makeTile(Tile tile){
		return new JTile(tile);
	}
	
	/**
	 * Get the drawable version of a wall
	 * 
	 * @param wall the wall to represent
	 * @return a graphical version of the wall
	 */
	public static Drawable makeWall(Wall wall){
		return new JWall(wall);
	}
	
	/**
	 * Get the drawable version of a door
	 * 
	 * @param door the door to represent
	 * @return a graphical version of the door
	 */
	public static Drawable makeDoor(Door door){
		return new JDoor(door);
	}
	
	/**
	 * Get the drawable version of a Clean Sweep
	 * 
	 * @param cs the Clean Sweep to represent
	 * @return a graphical version of the clean sweep
	 */
	public static Drawable makeCleanSweep(CleanSweep cs){
		return new JCleanSweep(cs);
	}

}
