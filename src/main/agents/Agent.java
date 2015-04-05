package agents;

import field.Direction;
import field.Field;

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
        speed = new Speed(Direction.UP, 0);
        isDead = false;
        currentLap = 1;
    }

    /**
     * speed getter függvénye.
     *
     * @return - A jelenlegi Speed klónját adja vissza.
     */
    public Speed getSpeed() {
        return speed.clone();
    }

    /**
     * speed setter függvénye.
     *
     * @param speed - A beállításra kerülő sebesség.
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * field getter függvénye.
     *
     * @return - field
     */
    public Field getField() {
        return field;
    }

    /**
     * field setter függvénye.
     *
     * @param field - A beállításra kerülő Field.
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * isDead getter függvénye.
     *
     * @return - isDead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Megöli az ágenst.
     */
    public void kill() {
        this.isDead = true;
    }

    /**
     * currentLap getter függvénye.
     *
     * @return - currentLap
     */
    public int getLap() {
        return currentLap;
    }

    /**
     * Kör növelés.
     * Növeli az ágens eddig megtett köreinek számát.
     */
    public void incrementLap() {
        this.currentLap++;
    }

    public abstract boolean onCauseCollision(Agent agent);

    public abstract Agent collide(Agent agent);
}
