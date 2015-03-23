import javax.swing.*;
import java.awt.*;

import field.Direction;
import game.Game;
import game.GameCreator;
import game.KeyDispatcher;
import game.Player;
import inspector.Inspector;
import test.skeleton.StaticTests;

/** 
 * Ez az osztály az alkalmazásnak a főosztálya
 * Feladaa a kezelőfelület valamint a játéklogika elindítás
 */
public class Main extends JFrame {
    /**
     * Program belépési függvénye
     * Ez a fügvény képezi az alkalmazásnak a belépési pontját melyet a JVM
     * hív meg amikor elindítjuk az alkalmazást
     * @param args - Paraméterek a program meghívásakor. Figyelmen kívül vannak hagyva
     */
    public static void main(String[] args) {
        final Main main = new Main();
        main.gameLoop();
    }


    /**
     * Játék létrehozása és elindítása
     * Feladata, hogy létrehozza a játéklogikát képviselő objektumot.
     */
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
