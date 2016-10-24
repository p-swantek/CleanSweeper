package edu.se459grp4.project.simulator.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 * Will represent the tile that currently contains 
 * the clean sweep. Handles the drawing of the clean sweep on this tile
 * 
 * @author Peter Swantek
 *
 */
@SuppressWarnings("serial")
public class CleanSweepPanel extends JPanel{

	private JComponent parent;
	
	public CleanSweepPanel(JComponent jc) {
		super();
		parent = jc;
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		parent.paint(g);
		Graphics2D g2 = (Graphics2D) g;
//
		int x = 0;
		int y = 0;
//		
		g2.setPaint(Color.red);
		g2.fillRect(x, y, 50, 50);
		
	}
	
	
	
	
	

}
