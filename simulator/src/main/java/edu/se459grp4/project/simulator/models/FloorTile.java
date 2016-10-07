package edu.se459grp4.project.simulator.models;

import edu.se459grp4.project.simulator.types.Border;
import edu.se459grp4.project.simulator.types.Tile;

public class FloorTile {
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
}
