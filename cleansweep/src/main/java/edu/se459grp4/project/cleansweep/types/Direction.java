package edu.se459grp4.project.cleansweep.types;

import java.util.HashMap;
import java.util.Map;

//define the direction that this sweep can move
public enum Direction {
    UP("UP"), LEFT("LEFT"), DOWN("DOWN"), RIGHT("RIGHT");

    private String directionText;

    private static Map<String, Direction> directionMap = new HashMap<String, Direction>();

    static {
        for (Direction directionEnum : Direction.values()) {
            directionMap.put(directionEnum.directionText, directionEnum);
        }
    }

    private Direction(String direction) { directionText = direction; }

    public static Direction valueOf(String directionText) {
        return directionMap.get(directionText);
    }
}
