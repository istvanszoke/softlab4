package game.control;

/**
 * Created by nyari on 2015.03.27..
 */
public interface GameControllerSocketListener {
    void socketOpened(GameControllerSocket sender);

    void socketClosed(GameControllerSocket sender);
}
