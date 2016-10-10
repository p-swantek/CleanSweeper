package edu.se459grp4.project.simulator;

import java.awt.GridLayout;

import javax.swing.JPanel;


import edu.se459grp4.project.simulator.types.Drawable;


public class SimulatorGUI{

	private Drawable[][] floorplan;
	private JPanel panel;
	
	public SimulatorGUI(Drawable[][] drawables){
		
		floorplan = cloneArray(drawables);
		panel = new JPanel(new GridLayout(drawables.length, drawables[0].length));
		
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
