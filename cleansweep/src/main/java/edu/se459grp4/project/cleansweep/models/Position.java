package edu.se459grp4.project.cleansweep.models;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Position getCoordinate(int x, int y) {
        return new Position(x, y);
    }

    public static Position getRelativePosition(Position position, Position directionalPosition) {
        int x = position.getX();
        int y = position.getY();
        int adjustedX = x + directionalPosition.getX();
        int adjustedY = y + directionalPosition.getY();
        return new Position(adjustedX, adjustedY);
    }

    @Override
    public boolean equals(Object obj) {
        Position other = (Position) obj;
        if(this.getX() == other.getX() && this.getY() == other.getY()) {
          return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + getX();
        hash = hash * 31 + getY();

        return hash;
    }
}
