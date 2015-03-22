package field;

import java.util.ArrayList;
import java.util.HashMap;

import agents.Agent;
import agents.Speed;
import buff.Buff;
/**
 * A p�ly�t alkot� mez�t�pusok k�z�s �sosz�lya.
 * Egyik f� feladata annak a cell�nak a megkeres�se, amelyre egy Robot ugorhat.
 */
public abstract class Field implements FieldElement {
    /*
     * A cell�n �ll� Agent.
     */
    protected Agent agent;
    /*
     * A cella a c�lt�l val� t�vols�ga.
     */
    protected final int distanceFromGoal;
    /*
     * A cell�n l�v� akt�v buffok t�rol�ja.
     */
    protected final ArrayList<Buff> buffs;
    /* 
     * A cella szomsz�dos cell�inak a t�rol�ja (max. 4).
     */
    protected final HashMap<Direction, Field> neighbours;

    /*
     * Konstruktor.
     * @param distanceFromGoal - A cella c�lt�l val� l�p�senk�nti t�vols�ga.
     */
    public Field(int distanceFromGoal) {
        buffs = new ArrayList<Buff>();
        neighbours = new HashMap<Direction, Field>();
        this.distanceFromGoal = distanceFromGoal;
    }
    
    /*
     * Hozz�adja a mez�h�z a megfelel� szomsz�dot a megfelel� ir�nyban.
     * @param direction - Ir�ny.
     * @param field - Szomsz�d to be.
     */
    public void addNeighbour(Direction direction, Field field) {
        neighbours.put(direction, field);
    }
    
    /*
     * Agent cell�ra l�p.
     * Lekezeli azt az estet, amikor egy Agent a cell�ra l�p: be�ll�tja a megfelel� refernci�kat
     * (mind az Agentben, mint �nmag�ban). Ezen fel�l megh�vja a r�l�p� Agent accept(AgentVisitor)
     * met�dus�t az �sszes, mez�n tal�lhat� Buffal.
     * @param agent - A cell�ra l�p� Agent.
     */
    public void onEnter(Agent agent) {
        for (Buff b : buffs) {
            agent.accept(b);
        }

        agent.setField(this);
        this.agent = agent;
    }
    
    /*
     * Agent a cell�r�l ell�p.
     * Lekezeli azt az esetet, amikor egy Agent elhagyja a mez�t.
     */
    public void onExit() {
        if (agent == null) {
            return;
        }

        buffs.clear();

        agent.setField(null);
        this.agent = null;
    }
    
    /*
     * Visszadja a distanceFromGoal-t.
     * @return - A c�lt�l val� t�vols�g.
     */
    public int getDistanceFromGoal() {
        return distanceFromGoal;
    }

    /*
     * Buff lehelyez�s.
     * Lehelyezi a cell�ra a param�ter�ben kapott buffot.
     * @param buff - A lehelyezett buff.
     */
    public void placeBuff(Buff buff) {
        buffs.add(buff);
    }

    /*
     * Kisz�molja, hogy adott sebess�g vektorral melyik m�sik Fieldre lehet ugrani.
     */
    public Displacement getDisplacement(Speed speed) {
        return new Displacement(this, searchGoal(speed));
    }

    /*
     * �ress�g vizsg�lat
     * Visszadja, hogy �res-e azaz nem �ll Agent az adott cell�n.
     * @return - A cella �ress�ge.
     */
    public boolean isEmpty() {
        return agent == null;
    }

    /*
     * Ir�ny keres�s.
     * Adott sebess�gn�l rekurz�v megkeresi, hogy melyik az Agent, melyik ir�nyba kell elmozduljon.
     * @return - A keresett ir�nyba lev� szomsz�d.
     */
    protected Field searchGoal(Speed speed) {
        if (speed.getMagnitude() == 0) {
            return this;
        }

        Speed newSpeed = speed.clone();
        newSpeed.setMagnitude(newSpeed.getMagnitude() - 1);
        return neighbours.get(speed.getDirection()).searchGoal(newSpeed);
    }
}
