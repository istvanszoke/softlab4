import javax.swing.*;
import java.awt.*;

import field.Direction;
import game.Game;
import game.GameCreator;
import game.KeyDispatcher;
import game.Player;
import inspector.Inspector;
import test.skeleton.StaticTests;

public class Main extends JFrame {
    public static void main(String[] args) {
        final Main main = new Main();
        main.gameLoop();
    }

    private void gameLoop() {
        Game test = null;
        System.out.println("Robot ugras tesztelese:");
            test = StaticTests.testGenerateNewTestGame();
            Inspector.setEnabled(true);
            System.out.println(assertBoolean(StaticTests.testRobotJump(test)));
            Inspector.setEnabled(false);
        System.out.println("Robot olaj elhelyezes tesztelese:");
            test = StaticTests.testGenerateNewTestGame();
            Inspector.setEnabled(true);
            System.out.println(assertBoolean(StaticTests.testRobotUseOil(test)));
            Inspector.setEnabled(false);
        System.out.println("Robot ragacs elhelyezes tesztelese:");
            test = StaticTests.testGenerateNewTestGame();
            Inspector.setEnabled(true);
            System.out.println(assertBoolean(StaticTests.testRobotUseSticky(test)));
            Inspector.setEnabled(false);
        System.out.println("Robot sebessegvaltoztatas tesztelese:");
            test = StaticTests.testGenerateNewTestGame();
            Inspector.setEnabled(true);
            System.out.println(assertBoolean(StaticTests.testRobotChangeSpeed(test, 1)));
            Inspector.setEnabled(false);
        System.out.println("Robot iranyvaltoztatas tesztelese:");
            test = StaticTests.testGenerateNewTestGame();
            Inspector.setEnabled(true);
            System.out.println(assertBoolean(StaticTests.testRobotChangeDirection(test, Direction.LEFT)));
            Inspector.setEnabled(false);
        System.out.println("Robot palyarol leugras tesztelese:");
            test = StaticTests.testGenerateNewTestGame();
            Inspector.setEnabled(true);
            System.out.println(assertBoolean(StaticTests.testRobotFallOff(test, Direction.DOWN)));
            Inspector.setEnabled(false);
        System.out.println("Robot olajra lep tesztelese:");
            test = StaticTests.testGenerateNewTestGame();
            Inspector.setEnabled(true);
            System.out.println(assertBoolean(StaticTests.testRobotOilField(test)));
            Inspector.setEnabled(false);
        System.out.println("Robot ragacsra lep tesztelese:");
            test = StaticTests.testGenerateNewTestGame();
            Inspector.setEnabled(true);
            System.out.println(assertBoolean(StaticTests.testRobotStickyField(test)));
            Inspector.setEnabled(false);
        System.out.println("Jatekos valtas:");
            test = StaticTests.testGenerateNewTestGame();
            Inspector.setEnabled(true);
            System.out.println(assertBoolean(StaticTests.testChangePlayer(test)));
            Inspector.setEnabled(false);
    }

    static String assertBoolean(boolean input)
    {
        return input ? "Sikeres" : "Sikertelen";
    }
}
