package game.control;

/**
 * Ezt az interfészt megvalósítva tudnak kliensek a csatlakozó
 * nyitás és zárási eseményeire feliratkozni
 */
public interface GameControllerSocketListener {
    /**
     * Nyitási esemény jelzésének fogadása
     * @param sender - A csatlakozó referenciája amelyik küldte a jelzést
     */
    void socketOpened(GameControllerSocket sender);

    /**
     * Zárási esemény jelzésének fogaddása
     * @param sender - A csatlakozó referenciája amelyik a jelzést küldte
     */
    void socketClosed(GameControllerSocket sender);
}
