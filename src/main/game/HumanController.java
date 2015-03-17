package game;

import java.awt.event.KeyEvent;

import commands.AgentCommand;
import commands.executes.*;
import commands.queries.*;
import field.Direction;

/**
 * Emberi játékvezérlő osztály
 * Feladata, hogy a billentyűzetről jövő utasításokat továbbítson egy ágensnek
 * Az osztály tartalmaz a játéklogikára egy referenciát, mely majd szolgáltatja az éppen
 * Aktuális Ágenst melynek az utasításokat átadhatjuk
 */
public class HumanController extends AgentController {
    /**
     * Az Emberi játék vezérlő osztály konstruktora
     * @param game - Referencia a játéklogikára mely majd szolgáltatja az ágenst akinek a vezérlést küldjük
     */
    public HumanController(Game game) {
        super(game);
    }

    /**
     * Billentyűleütést kezelő függvény
     * Fogadja a billentyűleütést majd létrehoz belőlük egy ÁgensParancsot,
     * melyet aztán átad a useCommand függvénynek
     * @param e - A billentyűleütés esemény argumentumainak referenciája
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Change Direction
            case KeyEvent.VK_W:
                useCommand(new ChangeDirectionQuery(Direction.UP));
                break;
            case KeyEvent.VK_A:
                useCommand(new ChangeDirectionQuery(Direction.LEFT));
                break;
            case KeyEvent.VK_S:
                useCommand(new ChangeDirectionQuery(Direction.DOWN));
                break;
            case KeyEvent.VK_D:
                useCommand(new ChangeDirectionQuery(Direction.RIGHT));
                break;

            // Jump
            case KeyEvent.VK_SPACE:
                useCommand(new JumpQuery());
                game.onAgentChange();
                break;

            // Change Speed
            case KeyEvent.VK_UP:
                useCommand(new ChangeSpeedQuery(1));
                break;
            case KeyEvent.VK_DOWN:
                useCommand(new ChangeSpeedQuery(-1));
                break;

            // Place buffs
            case KeyEvent.VK_R:
                useCommand(new UseOilQuery());
                break;
            case KeyEvent.VK_F:
                useCommand(new UseStickyQuery());
                break;

            // Suicide
            case KeyEvent.VK_K:
                useCommand(new KillExecute());
                game.onAgentChange();
                break;
        }
    }

    /**
     * Kapott parancs továbítása
     * A billentyűleütésből származó utasítást továbbítja az ágensnek
     * @param command - Az utasításnak a referenciája
     */
    private void useCommand(AgentCommand command) {
        game.getCurrentAgent().accept(command);
        System.out.println(command.getResult());
    }
}
