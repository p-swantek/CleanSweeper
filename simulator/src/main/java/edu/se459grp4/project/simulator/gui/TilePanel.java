package edu.se459grp4.project.simulator.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.se459grp4.project.simulator.models.FloorTile;

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
		
		File path = null;
		switch(t.getTileType()){
		
			case BARE_FLOOR:
				path = new File("src/main/resources/bare_floor.jpg");
				break;
			case LOW_CARPET:
				path = new File("src/main/resources/low_pile.jpg");
				break;
			case HIGH_CARPET:
				path = new File("src/main/resources/hi_pile.jpg");
				break;
			case STAIRS:
				path = new File("src/main/resources/stairs.jpg");
				break;
			case CHARGING_STATION:
				path = new File("src/main/resources/charging_station.jpg");
				break;

		}
		
		try {
			img = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 5, 5, null);
		if (dirtAmount > 0){
			g.drawString("DIRT: "+dirtAmount, 10, 20);
		}
		
		
		
//		int topBorder = getNorthBorder() == Border.WALL ? 20 : 0;
//		int rightBorder = getEastBorder() == Border.WALL ? 20 : 0;
//		int bottomBorder = getSouthBorder() == Border.WALL ? 20 : 0;
//		int leftBorder  = getWestBorder() == Border.WALL ? 20 : 0;
//		
//		jc.setLayout(new BorderLayout());
//		jc.setBorder(new MatteBorder(topBorder, leftBorder, bottomBorder, rightBorder, Color.BLACK));
		
		
	}
	
	
	

}
