package edu.se459grp4.project.simulator.models;


import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import edu.se459grp4.project.simulator.types.Border;
import edu.se459grp4.project.simulator.types.Drawable;
import edu.se459grp4.project.simulator.types.Tile;


public class FloorTile implements Drawable{
    Border northBorder;
    Border southBorder;
    Border westBorder;
    Border eastBorder;
    Tile tileType;
    int dirtAmount;


    public FloorTile(Border northBorder, Border southBorder, Border westBorder, Border eastBorder,
                     Tile tileType, int dirtAmount) {
        this.northBorder = northBorder;
        this.southBorder = southBorder;
        this.westBorder = westBorder;
        this.eastBorder = eastBorder;
        this.tileType = tileType;
        this.dirtAmount = dirtAmount;
    }

    public Border getNorthBorder() {
        return northBorder;
    }

    public void setNorthBorder(Border northBorder) {
        this.northBorder = northBorder;
    }

    public Border getSouthBorder() {
        return southBorder;
    }

    public void setSouthBorder(Border southBorder) {
        this.southBorder = southBorder;
    }

    public Border getWestBorder() {
        return westBorder;
    }

    public void setWestBorder(Border westBorder) {
        this.westBorder = westBorder;
    }

    public Border getEastBorder() {
        return eastBorder;
    }

    public void setEastBorder(Border eastBorder) {
        this.eastBorder = eastBorder;
    }

    public int getDirtAmount() {
        return dirtAmount;
    }

    public void setDirtAmount(int dirtAmount) {
        this.dirtAmount = dirtAmount;
    }
    
    public Tile getTileType(){
    	return this.tileType;
    }
    
    
	public JComponent draw() {
		
		JComponent jc = new JLabel();
		jc.setLayout(new BorderLayout());
		
		switch(getTileType()){
		
			case BARE_FLOOR:
				jc.setBackground(Color.GREEN);
				break;
			case LOW_CARPET:
				jc.setBackground(Color.YELLOW);
				break;
			case HIGH_CARPET:
				jc.setBackground(Color.RED);
				break;
			default:
				break;
	
		}
		
		int topBorder = getNorthBorder() == Border.WALL ? 20 : 0;
		int rightBorder = getEastBorder() == Border.WALL ? 20 : 0;
		int bottomBorder = getSouthBorder() == Border.WALL ? 20 : 0;
		int leftBorder  = getWestBorder() == Border.WALL ? 20 : 0;
		
		jc.setBorder(new MatteBorder(topBorder, leftBorder, bottomBorder, rightBorder, Color.BLUE));
		
		return jc;
	}

   
}
