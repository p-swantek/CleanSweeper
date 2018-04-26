package edu.se459grp4.project.gui.drawables;

import java.awt.Graphics;

@FunctionalInterface
public interface Drawable {
	
	/**
	 * Have the various GUI elements handle how they should be drawn
	 * 
	 * @param g the Graphics on which to draw
	 * @param area the size the of the drawing
	 * @see Graphics
	 */
	void draw(Graphics g, int area);

}
