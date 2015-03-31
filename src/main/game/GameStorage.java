package game;

import java.util.*;
import java.util.Map;

import agents.Agent;
import game.handle.AgentHandle;

public class GameStorage implements Iterable<AgentHandle>, HeartbeatListener {
    private final List<AgentHandle> all;
    private final List<AgentHandle> inPlay;
    private final List<AgentHandle> players;

    private final Map<Agent, AgentHandle> agentMapping;

    private AgentHandle current;

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

    public synchronized AgentHandle get(Agent agent) {
        return agentMapping.get(agent);
    }

    public synchronized AgentHandle getCurrent() {
        return current;
    }

    public synchronized List<AgentHandle> getInPlay() {
        return inPlay;
    }

    public synchronized List<AgentHandle> getPlayers() {
        return players;
    }

    @Override
    public Iterator<AgentHandle> iterator() {
        return all.iterator();
    }

    @Override
    public void onTick(long deltaTime) {
        AgentHandle current = getCurrent();
        current.setTimeRemaining(current.getTimeRemaining() - deltaTime);
    }

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

    private void disqualify() {
        Iterator<AgentHandle> i = inPlay.iterator();
        while (i.hasNext()) {
            AgentHandle next = i.next();
            if (next != current && next.isDisqualified()) {
                i.remove();
            }
        }
    }

    private void advance() {
        int currentIndex = inPlay.indexOf(current);
        current = inPlay.get((currentIndex + 1) % inPlay.size());
    }
}
