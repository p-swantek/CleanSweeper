package edu.se459grp4.project.cleansweep.models;

import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.simulator.types.Border;
import edu.se459grp4.project.simulator.types.Tile;

import java.util.Map;

/**
 * Represents a Floor square and it's properties
 */
public class FloorUnit {
	Border northBorder;
	Border southBorder;
	Border westBorder;
	Border eastBorder;
	Tile tileType;
	boolean dirtPresent;
	int dirtAmount;
	Position position;

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

	public Tile getTileType() {
		return tileType;
	}

	public void setTileType(Tile tileType) {
		this.tileType = tileType;
	}

	public boolean isDirtPresent() {
		return dirtPresent;
	}

	public void setDirtPresent(boolean dirtPresent) {
		this.dirtPresent = dirtPresent;
	}

	public int getDirtAmount(){
		return this.dirtAmount;
	}
	public void setDirtAmount(int dirt){
		this.dirtAmount= dirt;
	}
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setPosition(int x, int y) {
		this.position = new Position(x, y);
	}

	/**
	 * returns the Border type for a given edge of the tile the clean sweep is on
	 *
	 * @param direction enum representing the directional side of the tile
	 * @return the Border type on a particular edge of the tile
	 */
	public Border getBorder(Direction direction) {
		switch(direction) {
		case UP:
			return getNorthBorder();
		case DOWN:
			return getSouthBorder();
		case LEFT:
			return getWestBorder();
		case RIGHT:
			return getEastBorder();
		default:
			return null;
		}
	}
}
