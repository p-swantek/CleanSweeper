package edu.se459grp4.project.cleansweep.navigation;

import edu.se459grp4.project.cleansweep.environment.Environment;
import edu.se459grp4.project.cleansweep.models.FloorUnit;
import edu.se459grp4.project.cleansweep.models.Position;
import edu.se459grp4.project.cleansweep.models.Tuple;
import edu.se459grp4.project.cleansweep.types.Direction;

import java.util.Stack;

public class BasicNavigator extends Navigator {
    Stack<Tuple<Position, FloorUnit>> movementStack;

    public BasicNavigator(Environment environment) {
        super(environment);
        this.movementStack = new Stack<>();
    }

    public Direction movementDirection(FloorUnit currentFloorUnit) {
        // TODO: choose movement direction, send to floor simulator, and return chosen direction
        movementStack.push(new Tuple<>(environment.getCurrentPosition(), currentFloorUnit));
        return null;
    }
}
