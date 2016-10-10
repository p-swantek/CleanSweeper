package edu.se459grp4.project.simulator.util;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import edu.se459grp4.project.simulator.models.FloorTile;



@SuppressWarnings("serial")
public class TileDisplayPanel extends JPanel{
	
	private FloorTile tile;
	
	public TileDisplayPanel(FloorTile floorTile){
		
		tile = floorTile;
	
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		switch(tile.getTileType()){
		
			case BARE_FLOOR:
				g.setColor(Color.GREEN);
				break;
			case LOW_CARPET:
				g.setColor(Color.YELLOW);
				break;
			case HIGH_CARPET:
				g.setColor(Color.RED);
				break;
			default:
				break;
			
		}
		
		
	}
	
	
	

}
