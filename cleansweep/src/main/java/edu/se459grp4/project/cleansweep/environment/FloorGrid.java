package edu.se459grp4.project.cleansweep.environment;

import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.models.Position;

// Wrapper around 2D array to simulate 0,0 center to allow for -x and -y
public class FloorGrid {
    private FloorUnit[][] floorUnits;
    private final int centerX;
    private final int centerY;

    // Can be used to limit range of iteration
    private int minXCoordinate;
    private int minYCoordinate;
    private int maxXCoordinate;
    private int maxYCoordinate;

    public FloorGrid(int maxXRange, int maxYRange) {
        this.centerX = maxXRange / 2;
        this.centerY = maxYRange / 2;
        floorUnits = new FloorUnit[maxXRange][maxYRange];
    }

    public FloorUnit getCoordinate(int x, int y) {
        int adjustedX = centerX + x;
        int adjustedY = centerY + y;
        return floorUnits[adjustedX][adjustedY];
    }

    public FloorUnit getCoordinate(Position position) {
        return getCoordinate(position.getX(), position.getY());
    }

    public FloorUnit getRelativeCoordinate(int x, int y, int directionalX, int directionalY) {
        int adjustedX = centerX + x;
        int adjustedY = centerY + y;
        adjustedX = adjustedX + directionalX;
        adjustedY = adjustedY + directionalY;
        return floorUnits[adjustedX][adjustedY];
    }

    public FloorUnit getRelativeCoordinate(Position position, Position directionalPosition) {
        return getRelativeCoordinate(position.getX(), position.getY(),
                directionalPosition.getX(), directionalPosition.getY());
    }

    public void add(FloorUnit floorUnit, int x, int y) {
        int adjustedX = centerX + x;
        int adjustedY = centerY + y;
        minXCoordinate = (adjustedX < minXCoordinate) ? adjustedX : minXCoordinate;
        minYCoordinate = (adjustedY < minYCoordinate) ? adjustedY : minYCoordinate;
        maxXCoordinate = (adjustedX > maxXCoordinate) ? adjustedX : maxXCoordinate;
        maxYCoordinate = (adjustedY > maxYCoordinate) ? adjustedY : maxYCoordinate;
        floorUnits[adjustedX][adjustedY] = floorUnit;
    }

    public void add(FloorUnit floorUnit, Position position) {
        add(floorUnit, position.getX(), position.getY());
    }

    public int getMinXCoordinate() {
        return minXCoordinate;
    }

    public int getMinYCoordinate() {
        return minYCoordinate;
    }

    public int getMaxXCoordinate() {
        return maxXCoordinate;
    }

    public int getMaxYCoordinate() {
        return maxYCoordinate;
    }
}
