package edu.se459grp4.project.simulator.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;



import edu.se459grp4.project.simulator.types.Drawable;


public class SimulatorGUI{

	private Drawable[][] floorplan;
	private JFrame frame;
	
	public static final int WINDOW_HEIGHT = 750;
	public static final int WINDOW_WIDTH = 750;
	public static final String WINDOW_TITLE = "Clean Sweep Simulation";
	
	public SimulatorGUI(Drawable[][] drawables){
		
		floorplan = cloneArray(drawables);
		frame = new JFrame(WINDOW_TITLE);
		frame.setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		frame.setLayout(new GridLayout(floorplan.length, floorplan[0].length));
		
		colorTiles(frame, floorplan);
		
	}
	
	public void start(){
		frame.setVisible(true);
		
	}
	
	
	private static void colorTiles(JFrame f, Drawable[][] fp){
		for (Drawable[] subarray : fp){
			for (Drawable d : subarray){
				f.add(d.draw());
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
