package edu.se459grp4.project.simulator.types;

import javax.swing.JComponent;

/**
 * Determines how entities in the simulation are drawn on the GUI 
 * 
 * @author Peter Swantek
 *
 */
public interface Drawable {
	
	/**
	 * Determines the visual representation in the GUI
	 * 
	 * @return a JComponent representing the visualization of this
	 */
	JComponent draw();

}
