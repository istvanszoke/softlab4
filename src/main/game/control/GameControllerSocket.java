package game.control;

import commands.AgentCommand;

/**
 * Általános csatlakozó interfész egy játékvezérlőnek
 */
public interface GameControllerSocket {
    /**
     * Csatlakozó nyitottsági állapotának lekérdezése
     *
     * @return - Az állapot
     */
    boolean isOpen();

    /**
     * Kör továbbadásának jelzése a kiszolgálónak
     *
     * @return - Az jelzés fogadásának sikeressége
     */
    boolean sendEndTurn();

    /**
     * Ágensnek küldött utasítás továbbadása a kiszolgálónak
     *
     * @param command - Az elküldött utasítás
     * @return - A küldés fogadásának sikeressége
     */
    boolean sendAgentCommand(AgentCommand command);

    /**
     * A kliens ami birtokolja a csatlakozót ezen keresztül tud feliratkozni a nyitási és zárási eseményekre
     *
     * @param client - A feliratkozó kliens
     */
    void enableStateNotification(GameControllerSocketListener client);

    /**
     * A kliens ami birtokolja a csatlakozoót ezen keresztül tud leiratkozni a nyitási és zárási eseményekről
     */
    void disableStateNotification();
}
