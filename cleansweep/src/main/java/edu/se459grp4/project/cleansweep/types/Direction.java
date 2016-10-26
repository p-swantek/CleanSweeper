package edu.se459grp4.project.cleansweep.types;

import edu.se459grp4.project.cleansweep.models.Position;

import java.util.HashMap;
import java.util.Map;

//define the direction that this sweep can move
public enum Direction {
    UP("UP", new Position(0,1)),
    LEFT("LEFT", new Position(-1,0)),
    DOWN("DOWN", new Position(0,-1)),
    RIGHT("RIGHT", new Position(1,0));

    private String value;
    private Position position;

    private Direction(String value, Position position) {
        this.value = value;
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public Position getPosition() {
        return position;
    }
}
