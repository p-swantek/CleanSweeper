package edu.se459grp4.project.cleansweep.models;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO: implement means to uniquely identify position object in Map
        return false;
    }

    @Override
    public int hashCode() {
        // TODO: implement means to uniquely identify position object in Map
        return 0;
    }
}
