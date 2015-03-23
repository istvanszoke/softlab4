package agents;

import field.Direction;
import field.Field;
import inspector.Inspector;

/**
 * A pályán lévő ágensek reprezentációja.
 * A pályán lévő összes ágens közös viselkedését valósítja meg: tárolja azoknak a sebességét, azt a Fieldet,
 * amelyen éppen áll. Ezen felül számon tartja azt is, hogy az Agent élő vagy halott állapotban van-e, illetve
 * hogy elfogyott-e már az adott Agentre osztott idő.
 */
public abstract class Agent implements AgentElement {
    /**
     * Az ágens sebessége.
     */
    protected Speed speed;
    /**
     * Az ágenst tartalmazó Field.
     */
    protected Field field;
    /**
     * Az ágens létformája.
     */
    protected boolean isDead;
    /**
     * Az ágens jelenleg hányadik körben van.
     */
    protected int currentLap;

    /**
     * Konstruktor.
     * Az alapértelmezett sebességvektor felfele mutat.
     */
    public Agent() {
        Inspector.call("Agent.Agent()");
        speed = new Speed(Direction.UP, 0);
        isDead = false;
        currentLap = 1;
        Inspector.ret("Agent.Agent");
    }
    
    /**
     * speed getter függvénye.
     * @return - A jelenlegi Speed klónját adja vissza.
     */
    public Speed getSpeed() {
        Inspector.call("Agent.getSpeed():Speed");
        Speed tmp = speed.clone();
        Inspector.ret("Agent.getSpeed");
        return tmp;
    }
    /**
     * speed setter függvénye.
     * @param speed - A beállításra kerülő sebesség.
     */
    public void setSpeed(Speed speed) {
        Inspector.call("Agent.setSpeed(Speed)");
        this.speed = speed;
        Inspector.ret("Agent.setSpeed");
    }
    
    /**
     * field getter függvénye.
     * @return - field
     */
    public Field getField() {
        Inspector.call("Agent.getField():Field");
        Inspector.ret("Agent.getField");
        return field;
    }

    /**
     * field setter függvénye.
     * @param field - A beállításra kerülő Field.
     */
    public void setField(Field field) {
        Inspector.call("Agent.setField(Field)");
        Inspector.ret("Agent.setField");
        this.field = field;
    }

    /**
    * isDead getter függvénye.
    * @return - isDead
    */
    public boolean isDead() {
        Inspector.call("Agent.isDead():boolean");
        Inspector.ret("Agent.isDead");
        return isDead;
    }

    /**
     * Megöli az ágenst.
     */
    public void kill() {
        Inspector.call("Agent.kill()");
        this.isDead = true;
        Inspector.ret("Agent.kill");
    }

    /**
     * currentLap getter függvénye.
     * @return - currentLap
     */
    public int getLap()
    {
        Inspector.call("Agent.getLap()");
        Inspector.ret("Agent.getLap");
        return currentLap;
    }
    
    /**
     * Kör növelés.
     * Növeli az ágens eddig megtett köreinek számát.
     */
    public void incrementLap() {
        Inspector.call("Agent.incrementLap()");
        this.currentLap++;
        Inspector.ret("Agent.incrementLap");
    }
}
