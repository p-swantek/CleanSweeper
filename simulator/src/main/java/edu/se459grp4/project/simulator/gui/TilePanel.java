package edu.se459grp4.project.simulator.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.se459grp4.project.simulator.models.FloorTile;
import edu.se459grp4.project.simulator.types.Border;

@SuppressWarnings("serial")
public class TilePanel extends JPanel{
	
	private FloorTile floortile;
	private int dirtAmount;
	private Image img;
	
	
	public TilePanel(FloorTile t){
		floortile = t;
		dirtAmount = t.getDirtAmount();
		setImage(floortile);
	}
	
	private void setImage(FloorTile t){
		InputStream inputStream = null;
		switch(t.getTileType()){
			case BARE_FLOOR:
				inputStream = getClass().getResourceAsStream("/bare_floor.jpg");
				break;
			case LOW_CARPET:
				inputStream = getClass().getResourceAsStream("/low_pile.jpg");
				break;
			case HIGH_CARPET:
				inputStream = getClass().getResourceAsStream("/hi_pile.jpg");
				break;
			case STAIRS:
				inputStream = getClass().getResourceAsStream("/stairs.jpg");
				break;
			case CHARGING_STATION:
				inputStream = getClass().getResourceAsStream("/charging_station.jpg");
				break;
		}
		
		try {
			img = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		
		if (dirtAmount > 0){
			g.drawString("DIRT: "+dirtAmount, 10, 20);
		}
		
		int topBorder = floortile.getNorthBorder() == Border.WALL ? 10 : floortile.getNorthBorder() == Border.CLOSED_DOOR ? 5 : 0;
		int rightBorder = floortile.getEastBorder() == Border.WALL ? 10 : floortile.getEastBorder() == Border.CLOSED_DOOR ? 5 : 0;
		int bottomBorder = floortile.getSouthBorder() == Border.WALL ? 10 : floortile.getSouthBorder() == Border.CLOSED_DOOR ? 5 : 0;
		int leftBorder  = floortile.getWestBorder() == Border.WALL ? 10 : floortile.getWestBorder() == Border.CLOSED_DOOR ? 5 : 0;

		setBorder(new TileBorder(topBorder, bottomBorder, leftBorder, rightBorder));
	}
	
	
	

}
