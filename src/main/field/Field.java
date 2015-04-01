package field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import agents.Agent;
import agents.Speed;
import buff.Buff;
import buff.BuffListener;

/**
 * A pályát alkotó mezőtípusok közös ősoszálya.
 * Egyik fő feladata annak a cellának a megkeresése, amelyre egy Robot ugorhat.
 */
public abstract class Field implements FieldElement, BuffListener {

    /**
     * A cella a céltól való távolsága.
     */
    protected final int distanceFromGoal;

    /**
     * A cellán lévő aktív buffok tárolója.
     */
    protected final ArrayList<Buff> buffs;

    /**
     * A cella szomszédos celláinak a tárolója (max. 4).
     */
    protected final Map<Direction, Field> neighbours;

    /**
     * A cellán álló Agent.
     */
    protected Agent agent;

    /**
     * Konstruktor.
     *
     * @param distanceFromGoal - A cella céltól való lépésenkénti távolsága.
     */
    public Field(int distanceFromGoal) {
        buffs = new ArrayList<Buff>();
        neighbours = new HashMap<Direction, Field>();
        this.distanceFromGoal = distanceFromGoal;
    }

    /**
     * Hozzáadja a mezőhöz a megfelelő szomszédot a megfelelő irányban.
     *
     * @param direction - Irány.
     * @param field     - Szomszéd to be.
     */
    public void addNeighbour(Direction direction, Field field) {
        neighbours.put(direction, field);
    }

    /**
     * Agent cellára lép.
     * Lekezeli azt az estet, amikor egy Agent a cellára lép: beállítja a megfelelő refernciákat
     * (mind az Agentben, mint önmagában). Ezen felül meghívja a rálépő Agent accept(AgentVisitor)
     * metódusát az összes, mezőn található Buffal.
     *
     * @param agent - A cellára lépő Agent.
     */
    public void onEnter(Agent agent) {
        if (agent != null && this.agent != null && agent != this.agent) {
            agent = this.agent.collide(agent);
        }

        if (agent == null) {
            return;
        }

        for (Buff b : buffs) {
            agent.accept(b);
        }

        agent.setField(this);
        this.agent = agent;
    }

    /**
     * Agent a celláról ellép.
     * Lekezeli azt az esetet, amikor egy Agent elhagyja a mezőt.
     */
    public void onExit() {
        if (agent == null) {
            return;
        }

        buffs.clear();

        agent.setField(null);
        this.agent = null;
    }

    /**
     * Visszadja a distanceFromGoal-t.
     *
     * @return - A céltól való távolság.
     */
    public int getDistanceFromGoal() {
        return distanceFromGoal;
    }

    /**
     * Buff lehelyezés.
     * Lehelyezi a cellára a paraméterében kapott buffot.
     *
     * @param buff - A lehelyezett buff.
     */
    public void placeBuff(Buff buff) {
        buff.subscribe(this);
        buffs.add(buff);
    }

    /**
     * Kiszámolja, hogy adott sebesség vektorral melyik másik Fieldre lehet ugrani.
     */
    public Displacement getDisplacement(Speed speed) {
        return new Displacement(this, searchGoal(speed));
    }

    /**
     * Üresség vizsgálat
     * Visszadja, hogy üres-e azaz nem áll Agent az adott cellán.
     *
     * @return - A cella üressége.
     */
    public boolean isEmpty() {
        return agent == null;
    }

    @Override
    public void onRemove(Buff buff) {
        buff.unsubscribe(this);
        buffs.remove(buff);
    }

    protected Field searchGoal(Speed speed) {
        if (speed.getMagnitude() == 0) {
            return this;
        }

        Speed newSpeed = speed.clone();
        newSpeed.setMagnitude(newSpeed.getMagnitude() - 1);
        return neighbours.get(speed.getDirection()).searchGoal(newSpeed);
    }
}
