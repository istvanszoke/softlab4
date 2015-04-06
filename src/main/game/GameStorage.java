package game;

import java.util.*;
import java.util.Map;

import agents.Agent;
import game.handle.AgentHandle;

/**
 * Játéklogikához szükséges adatok összefogásaa és biztonságos kezelését kezelő
 * játéktároló
 */
public class GameStorage implements Iterable<AgentHandle>, HeartbeatListener {
    /**
     * Az összes ágenskezelő
     */
    private final List<AgentHandle> all;
    /**
     * Játékban lévő ágenskezelős
     */
    private final List<AgentHandle> inPlay;
    /**
     * Játékosok ágenskezelői
     */
    private final List<AgentHandle> players;

    /**
     * Ágens ágenskezelő hozzárendelése
     */
    private final Map<Agent, AgentHandle> agentMapping;

    /**
     * Aktuálisan működésben lévő ágenskezelő
     */
    private AgentHandle current;

    /**
     * Játéktároló konstruktora
     * @param handles - Ágenskezelők melyeket kezelünk
     */
    public GameStorage(Collection<AgentHandle> handles) {
        all = new ArrayList<AgentHandle>();
        inPlay = new ArrayList<AgentHandle>();
        players = new ArrayList<AgentHandle>();

        agentMapping = new HashMap<Agent, AgentHandle>();

        for (AgentHandle handle : handles) {
            add(handle);
        }

        current = inPlay.get(0);
    }

    /**
     * Tároló állapotának frissítése
     */
    public synchronized void update() {
        AgentHandle previous = current;
        cleanup();
        disqualify();
        advance();

        if (previous.isDisqualified()) {
            inPlay.remove(previous);

            if (!previous.isPlayer()) {
                all.remove(previous);
                agentMapping.remove(previous.getAgent());
            }
        }

        System.out.println("In play:" + inPlay.size());
    }

    /**
     * Ágenskezelő hozzáadása a meglévőkhöz
     * @param handle - Új ágenskezelő
     */
    public synchronized void add(AgentHandle handle) {
        all.add(handle);
        agentMapping.put(handle.getAgent(), handle);

        if (!handle.isDisqualified()) {
            inPlay.add(handle);
        }
        if (handle.isPlayer()) {
            players.add(handle);
        }
    }

    /**
     * Egy adott Ágenshez tartotó ágenskezelő lekérdezése
     * @param agent - A kulcs Ágens referencia
     * @return - Az eredményként szolgáltatott ágenskezelő
     */
    public synchronized AgentHandle get(Agent agent) {
        return agentMapping.get(agent);
    }

    /**
     * Aktuális ágenskezelő lekérdezése
     * @return - Aktuális ágenskezelő
     */
    public synchronized AgentHandle getCurrent() {
        return current;
    }

    /**
     * Játékban lévő ágenskezelők lekérése
     * @return - Játékban lévő ágenskezelő
     */
    public synchronized List<AgentHandle> getInPlay() {
        return inPlay;
    }

    /**
     * Játékosokhoz lévő ágenskezelők lekérdezése
     * @return - Játékosokhoz tartozó ágenskezelők
     */
    public synchronized List<AgentHandle> getPlayers() {
        return players;
    }

    /**
     * Az összes ágenskezelő iterálása
     * @return
     */
    @Override
    public Iterator<AgentHandle> iterator() {
        return all.iterator();
    }

    /**
     * Idő telésének lekezelése
     * @param deltaTime - Legutolsó meghívás óta eltelt idő
     */
    @Override
    public void onTick(long deltaTime) {
        AgentHandle current = getCurrent();
        current.setTimeRemaining(current.getTimeRemaining() - deltaTime);
    }

    /**
     * Lekezeli az érvényen kívül került Ágenskezelőket
     */
    private void cleanup() {
        Iterator<AgentHandle> i = all.iterator();
        while (i.hasNext()) {
            AgentHandle next = i.next();
            if (next != current && next.isDisqualified() && !next.isPlayer()) {
                inPlay.remove(next);
                agentMapping.remove(next.getAgent());
                i.remove();
            }
        }
    }

    /**
     * Kizárja a kiesett játékosokat
     */
    private void disqualify() {
        Iterator<AgentHandle> i = inPlay.iterator();
        while (i.hasNext()) {
            AgentHandle next = i.next();
            if (next != current && next.isDisqualified()) {
                i.remove();
            }
        }
    }

    /**
     * Léptet a következő játékosra
     */
    private void advance() {
        int currentIndex = inPlay.indexOf(current);
        current = inPlay.get((currentIndex + 1) % inPlay.size());
    }
}
