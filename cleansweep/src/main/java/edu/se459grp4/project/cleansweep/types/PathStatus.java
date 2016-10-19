package edu.se459grp4.project.cleansweep.types;

import java.util.HashMap;
import java.util.Map;

public enum PathStatus {
    UNKNOWN(0), OPEN(1), OBSTACLE(2), STAIR(3);

    private int pathStatusNum;

    private static Map<Integer, PathStatus> pathStatusMap = new HashMap<Integer, PathStatus>();

    static {
        for (PathStatus pathStatusEnum : PathStatus.values()) {
            pathStatusMap.put(pathStatusEnum.pathStatusNum, pathStatusEnum);
        }
    }

    private PathStatus(int pathStatus) { pathStatusNum = pathStatus; }

    public static PathStatus valueOf(int pathStatusNum) {
        return pathStatusMap.get(pathStatusNum);
    }
}

