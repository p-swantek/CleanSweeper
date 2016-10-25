package edu.se459grp4.project.cleansweep.models;

import edu.se459grp4.project.simulator.types.Border;
import edu.se459grp4.project.simulator.types.Tile;

import java.util.Map;

public class FloorUnit {
    Border northBorder;
    Border southBorder;
    Border westBorder;
    Border eastBorder;
    Tile tileType;
    boolean dirtPresent;

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
}
