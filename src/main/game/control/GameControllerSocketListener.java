package game.control;

public interface GameControllerSocketListener {
    void socketOpened(GameControllerSocket sender);

    void socketClosed(GameControllerSocket sender);
}
