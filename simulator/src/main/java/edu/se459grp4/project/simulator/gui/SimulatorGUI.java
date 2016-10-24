package edu.se459grp4.project.simulator.gui;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.se459grp4.project.simulator.types.Drawable;


public class SimulatorGUI{

	private Drawable[][] floorplan;
	private JFrame frame;
	private Point cleanSweepPosition; //the GUI's version of the clean sweeps position to account for fact that (0,0) on GUI is top left corner
	private Point prevPosition; //the previous position of the clean sweep
	private JComponent[][] guiPanels; //array of references to the JComponents that populate the main frame
	
	public static final int WINDOW_HEIGHT = 1080;
	public static final int WINDOW_WIDTH = 750;
	public static final String WINDOW_TITLE = "Clean Sweep Simulation";
	
	public SimulatorGUI(Drawable[][] drawables){
		
		floorplan = drawables; //cloneArray(drawables);
		cleanSweepPosition = new Point(0, 0);
		prevPosition = cleanSweepPosition;
		guiPanels = new JComponent[drawables.length][drawables[0].length];
		
		frame = new JFrame(WINDOW_TITLE);
		frame.setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		frame.setLayout(new GridLayout(floorplan.length, floorplan[0].length));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		colorTiles(frame, floorplan);
		
	}
	
	public void start(){
		SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	    	  frame.setVisible(true);
	      }
	     });

	}
	
	/*
	 * clients call this method to update the GUI after any changes have been made so 
	 * that changes are reflected in the GUI
	 */
	public void refreshGUI(){
		
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  frame.revalidate();
		  		for (JComponent[] subarray : guiPanels){
		  			for (JComponent jc : subarray){
		  				jc.revalidate();
		  				jc.repaint();
		  			}
		  			
		  		}
		  		
		  		
		  		frame.repaint();
		      }
		     });
		
		
		
		
	}
	
	public void setCleanSweep(Point p){
		prevPosition.setLocation(cleanSweepPosition.getX(), cleanSweepPosition.getY()); //store the old location
		cleanSweepPosition.setLocation(p.getX(), p.getY()); //update location to be of the passed in point
		
		guiPanels[prevPosition.x][prevPosition.y] = floorplan[prevPosition.x][prevPosition.y].draw(); //draw the original floorplan drawing at old location
		JComponent panel = floorplan[cleanSweepPosition.x][cleanSweepPosition.y].draw();
		CleanSweepPanel csTile = new CleanSweepPanel(panel);
		panel.add(csTile);
		guiPanels[cleanSweepPosition.x][cleanSweepPosition.y] = panel;
		refreshGUI();
	}
	

	//do the initial population of the main frame with the tile panel drawings
	private void colorTiles(JFrame f, Drawable[][] fp){
		for (int y = fp.length-1; y >= 0; y--){
			for (int x = 0; x < fp[0].length; x++){
				JComponent panel = fp[x][y].draw();
				if (x == cleanSweepPosition.getX() && y == cleanSweepPosition.getY()){ //clean sweep initially starts at (0,0)
					//draw a clean sweep on the spot in the array of Drawables that designates where the clean sweep is
					CleanSweepPanel csPanel = new CleanSweepPanel(panel);
					panel.add(csPanel);

				}
				guiPanels[x][y] = panel;
				f.add(panel);
			}
		}
		
		
		
	}
	
	/*
	 * makes and returns a copy of a two dimensional array of Drawable objects
	 
	private static Drawable[][] cloneArray(Drawable[][] src) {
	    int length = src.length;
	    Drawable[][] target = new Drawable[length][src[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    return target;
	}
	
	*/
	

}
