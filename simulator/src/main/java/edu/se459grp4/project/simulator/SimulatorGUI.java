package edu.se459grp4.project.simulator;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


import edu.se459grp4.project.simulator.types.Drawable;


public class SimulatorGUI{

	private Drawable[][] floorplan;
	private JFrame frame;
	
	public SimulatorGUI(Drawable[][] drawables){
		
		floorplan = cloneArray(drawables);
		frame = new JFrame("Simulation");
		frame.setSize(500, 500);
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
