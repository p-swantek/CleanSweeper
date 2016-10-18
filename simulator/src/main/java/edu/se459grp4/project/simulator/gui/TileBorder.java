package edu.se459grp4.project.simulator.gui;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class TileBorder implements Border{
	
	private int topBorder;
	private int bottomBorder;
	private int leftBorder;
	private int rightBorder;
	
	
	public TileBorder(int top, int bottom, int left, int right){
		topBorder = top;
		bottomBorder = bottom;
		leftBorder = left;
		rightBorder = right;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Insets insets = getBorderInsets(c);   
		g.fill3DRect(0, 0, width-insets.right, insets.top, true); 
	    g.fill3DRect(0, insets.top, insets.left, height-insets.top, true);   
	    g.fill3DRect(insets.left, height-insets.bottom, width-insets.left, insets.bottom, true);   
	    g.fill3DRect(width-insets.right, 0, insets.right, height-insets.bottom, true);   
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(topBorder, leftBorder, bottomBorder, rightBorder);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

}
