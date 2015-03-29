package game.handle;

import java.util.*;
import java.util.function.Predicate;

import agents.Agent;
import game.HeartbeatListener;
import util.Functional;

public class HandleStore implements Iterable<AgentHandle>, HeartbeatListener {
    private final List<AgentHandle> inPlay;
    private final List<AgentHandle> disqualified;
    private final List<AgentHandle> combined;
    private final Map<Agent, AgentHandle> agentMapping;

    private AgentHandle current;

    private static final Predicate<AgentHandle> disqualifiedFilter = new Predicate<AgentHandle>() {
        @Override
        public boolean test(AgentHandle agentHandle) {
            return agentHandle.isDisqualified();
        }
    };

    private static final Predicate<AgentHandle> cleanupFilter = new Predicate<AgentHandle>() {
        @Override
        public boolean test(AgentHandle agentHandle) {
            return !agentHandle.isPlayer() && agentHandle.isDisqualified();
        }
    };

    public HandleStore(Collection<AgentHandle> handles) {
        combined = new ArrayList<AgentHandle>(handles);
        inPlay = new ArrayList<AgentHandle>();
        disqualified = new ArrayList<AgentHandle>();
        agentMapping = new HashMap<Agent, AgentHandle>();

        for (AgentHandle handle : combined) {
            agentMapping.put(handle.getAgent(), handle);
            getCorrectList(handle).add(handle);
        }

        current = inPlay.get(0);
    }

    public synchronized void update() {
        inPlay.removeIf(cleanupFilter);
        combined.removeIf(cleanupFilter);

        disqualified.addAll(Functional.filter(inPlay, disqualifiedFilter));
        inPlay.removeIf(disqualifiedFilter);
        System.out.println("In play:" + inPlay.size());
    }

    public synchronized void add(AgentHandle handle) {
        combined.add(handle);
        agentMapping.put(handle.getAgent(), handle);
        getCorrectList(handle).add(handle);
    }

    public synchronized AgentHandle get(Agent agent) {
        return agentMapping.get(agent);
    }

    public synchronized AgentHandle getCurrent() {
        return current;
    }

    public synchronized boolean canAdvance() {
        return !inPlay.isEmpty();
    }


    public synchronized void advance() {
        int currentIndex = inPlay.indexOf(current);
        current = inPlay.get((currentIndex + 1) % inPlay.size());
    }

    public synchronized void remove(AgentHandle handle) {
        if (handle == current) {
            advance();
        }

        combined.remove(handle);
        agentMapping.remove(handle.getAgent());
        getCorrectList(handle).remove(handle);
    }

    public synchronized List<AgentHandle> getInPlay() {
        return inPlay;
    }

    public synchronized List<AgentHandle> getDisqualified() {
        return disqualified;
    }

    private List<AgentHandle> getCorrectList(AgentHandle handle) {
        if (handle.isDisqualified()) {
            return disqualified;
        } else {
            return inPlay;
        }
    }

    @Override
    public Iterator<AgentHandle> iterator() {
        return combined.iterator();
    }

    @Override
    public void onTick(long deltaTime) {
        AgentHandle current = getCurrent();
        current.setTimeRemaining(current.getTimeRemaining() - deltaTime);
    }
}
