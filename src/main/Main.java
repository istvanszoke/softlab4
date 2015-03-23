import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import field.Direction;
import game.Game;
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

    static final String[] menu = {
            "Robot ugras tesztelese",
            "Robot olaj elhelyezes tesztelese",
            "Robot ragacs elhelyezes tesztelese",
            "Robot sebessegvaltoztatas tesztelese",
            "Robot iranyvaltoztatas tesztelese",
            "Robot palyarol leugras tesztelese",
            "Robot olajra lep tesztelese",
            "Robot ragacsra lep tesztelese",
            "Jatekos valtas",
            "Kilepes"
    };

    static void clearConsole() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 150; ++i) {
            b.append(String.format("%n"));
        }
        System.out.print(b);
    }

    static int showMenu() {
        clearConsole();
        for (int i = 0; i < menu.length; ++i) {
            System.out.println(String.format("%02d. %s", i + 1, menu[i]));
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        int ret = -1;

        while (ret <= 0) {
            try {
                System.out.print(String.format("%nFuttatando teszteset szama: "));
                input = br.readLine();
                ret = Integer.parseInt(input);
            } catch (Exception e) {
                ret = 0;
            }
        }

        return ret;
    }

    static void runTest(int index) {
        Game test = StaticTests.testGenerateNewTestGame();
        Inspector.setEnabled(true);
        System.out.println(menu[index - 1]);
        switch (index) {
            case 1:
                System.out.println(assertBoolean(StaticTests.testRobotJump(test)));
                break;
            case 2:
                System.out.println(assertBoolean(StaticTests.testRobotUseOil(test)));
                break;
            case 3:
                System.out.println(assertBoolean(StaticTests.testRobotUseSticky(test)));
                break;
            case 4:
                System.out.println(assertBoolean(StaticTests.testRobotChangeSpeed(test, 1)));
                break;
            case 5:
                System.out.println(assertBoolean(StaticTests.testRobotChangeDirection(test, Direction.LEFT)));
                break;
            case 6:
                System.out.println(assertBoolean(StaticTests.testRobotFallOff(test, Direction.DOWN)));
                break;
            case 7:
                System.out.println(assertBoolean(StaticTests.testRobotOilField(test)));
                break;
            case 8:
                System.out.println(assertBoolean(StaticTests.testRobotStickyField(test)));
                break;
            case 9:
                System.out.println(assertBoolean(StaticTests.testChangePlayer(test)));
                break;
        }
        System.out.println();
        Inspector.setEnabled(false);
    }

    private void gameLoop() {
        int menuCode = showMenu();

        while (menuCode != menu.length) {
            runTest(menuCode);
            System.out.println("Nyomj Entert a folytatashoz");

            try {
                System.in.read();
            } catch (IOException ignored) {}

            menuCode = showMenu();
        }
    }

    static String assertBoolean(boolean input)
    {
        return input ? "Sikeres" : "Sikertelen";
    }
}
