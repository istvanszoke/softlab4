import agents.Agent;
import agents.Robot;
import agents.Speed;
import commands.AgentCommand;
import commands.queries.JumpQuery;
import field.Direction;
import field.EmptyFieldCell;
import field.Field;
import field.FieldCell;

/**
 * This is the Main class of the project.
 * Its purpose for the moment is to do absolutely nothing but greet us
 * in a wildly sarcastic manner
 */
public class Main {

    public static void main(String[] args) {
        Field down = new FieldCell(0);
        Field up = new FieldCell(1);
        Field empty = new EmptyFieldCell(0);

        down.addNeighbour(Direction.UP, up);
        up.addNeighbour(Direction.DOWN, down);

        up.addNeighbour(Direction.UP, empty);
        down.addNeighbour(Direction.DOWN, empty);

        Agent a = new Robot();
        a.setSpeed(new Speed(Direction.UP, 1));

        down.onEnter(a);

        AgentCommand c = new JumpQuery();
        a.accept(c);
        System.out.println(c.getResult().toString());
        System.out.println(a.isDead());
    }
}
