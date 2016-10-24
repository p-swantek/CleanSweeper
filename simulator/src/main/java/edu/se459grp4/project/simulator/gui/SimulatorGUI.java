package edu.se459grp4.project.simulator.gui;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.se459grp4.project.simulator.types.Drawable;


public class SimulatorGUI{

	private Drawable[][] floorplan;
	private JFrame frame;
	private Point cleanSweepPosition; //the GUI's version of the clean sweeps position to account for fact that (0,0) on GUI is top left corner
	
	public static final int WINDOW_HEIGHT = 1080;
	public static final int WINDOW_WIDTH = 750;
	public static final String WINDOW_TITLE = "Clean Sweep Simulation";
	
	public SimulatorGUI(Drawable[][] drawables){
		
		floorplan = cloneArray(drawables);
		cleanSweepPosition = new Point(0, 0);
		
		frame = new JFrame(WINDOW_TITLE);
		frame.setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		frame.setLayout(new GridLayout(floorplan.length, floorplan[0].length));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		colorTiles(frame, floorplan);
		
	}
	
	public void start(){
		frame.setVisible(true);
		
	}
	
	/*
	 * clients call this method to update the GUI after any changes have been made so 
	 * that changes are reflected in the GUI
	 */
	public void refreshGUI(){
		frame.validate();
		frame.repaint();
	}
	
	public void setCleanSweep(Point p){
		cleanSweepPosition.setLocation(p.getX(), p.getY());
	}
	

	
	private static void colorTiles(JFrame f, Drawable[][] fp){
//		for (Drawable[] subarray : fp){
//			for (Drawable d : subarray){
//				f.add(d.draw());
//			}
//		}
		
		for (int y = fp.length-1; y >= 0; y--){
			for (int x = 0; x < fp[0].length; x++){
				JComponent panel = fp[x][y].draw();
				if (x == 0 && y == 0){
					//draw a clean sweep on the spot in the array of Drawables that designates where the clean sweep is
					
					JLabel label = new JLabel("CS", JLabel.CENTER);
					panel.add(label);
				}
				
				
				f.add(panel);
			}
		}
		
		
		
	}
	
	/*
	 * makes and returns a copy of a two dimensional array of Drawable objects
	 */
	private static Drawable[][] cloneArray(Drawable[][] src) {
	    int length = src.length;
	    Drawable[][] target = new Drawable[length][src[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    return target;
	}
	

}
