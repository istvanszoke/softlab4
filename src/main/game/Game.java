package game;

import java.awt.*;
import java.util.*;

import agents.Agent;
import field.Field;
import inspector.Inspector;

/**
 * Játéklogika, ez az osztály felelős a játéklogika megvalósításáért
 * Ide tartozik a idővezérlés, ágensek állásának helyzete és
 * a pálya kezlése is
 */
public class Game implements ControllerListener {
    /**
     * Szívverést biztosító Timer
     */
    private final Timer timer;
    /**
     * Timer működését felfüggesztő jelzés mellyel felfüggeszteni lehet a játékot
     */
    private boolean isPaused;
    /**
     * Köridőnek a meghatározása
     */
    private final int roundTime;
    /**
     * A játékban részvevő játékosokat tároló tömb
     */
    private final ArrayList<Player> players;
    /**
     * A játékból már kiesett vagy kizárt játékosokat tároló tömb
     */
    private final ArrayList<Player> disqualified;
    /**
     * Az éppen aktuálisan soron lévő játékosnak a referenciája
     */
    private Player currentPlayer;

    /**
     * Referencia a térképre
     */
    private final Map map;
    /**
     * Referencia az Ágens vezérlőre ami az ágenseket irányítja
     */
    private final AgentController controller;


    /**
     * Játéklogika osztálynak a konstruktora
     * Feladata a működéshez feltétlenül előre meghatározandó paraméterek és referenciák
     * átvétele melyek szükségesek az osztály működésének biztosításához
     * @param players - Játékosoknak a tömbje akik részt vesznek a játékbban
     * @param map - A pályának a referenciája amelyen a játékosok játszanak
     * @param roundTime - A köridíő, hogy egy játékosnak mennyi ideje van
     */
    public Game(ArrayList<Player> players, Map map, int roundTime) {
        Inspector.call("Game.Game(ArrayList<Player>, Map, int)");
        timer = new Timer();
        isPaused = true;
        this.roundTime = roundTime;

        this.players = players;
        disqualified = new ArrayList<Player>();
        currentPlayer = this.players.get(0);

        this.map = map;
        controller = new HumanController(this);

        placeAgents();
        setupTimer();
        Inspector.ret("Game.Game");
    }

    /**
     * Elindítja a felfüggesztett játékot
     */
    public void start() {
        isPaused = false;
    }

    /**
     * Felfüggeszti a futó játékot
     */
    public void pause() {
        isPaused = true;
    }

    /**
     * Visszaállítja a játékmenetet az alapállapotba
     */
    public void reset() {
        players.addAll(disqualified);
        disqualified.clear();
        for (Player player : players) {
            player.setTimeRemaining(roundTime);
        }
        placeAgents();
    }

    /**
     * Az játékban résztvevő játékosokat visszaadó függvény
     * @return - A résztvevő játékosok tömbjét visszadó függvény
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * A játékból már kizárt vagy kiesett játékosokat visszaadó függvény
     * @return - A már kizárt vagy kiesett játékosoknak a tömbje
     */
    public ArrayList<Player> getDisqualified() {
        return disqualified;
    }

    /**
     * Egy újab ágensvezérlőt felvevő függvény
     * @param component - A felvet ágensvezérlő
     */
    public void registerController(Component component) {
        component.addKeyListener(controller);
    }

    /**
     * Aktuális ágenst lekérő függvény
     * Visszaadja, hogy éppen melyik Ágens van a soron
     * @return
     */
    public Agent getCurrentAgent() {
        Inspector.call("Game.getCurrentAgent():Agent");
        Agent tmp = getCurrentPlayer().getAgent();
        Inspector.ret("Game.getCurrentAgent()");
        return tmp;
    }

    /**
     * Az éppen aktuális Pálya referenciáját lekérő függvény
     * @return - Az aktuális pályának a referenciája
     */
    public Map getMap() {
        return map;
    }

    /**
     * Ágensválltó függvény
     * Feladata, hogy a soron következő ágensre adja át vezértlést
     */
    @Override
    public void onAgentChange() {
        Inspector.call("Game.getAgentChange()");

        int currentIndex = players.indexOf(currentPlayer);

        if (getCurrentPlayer().isOutOfTime()) {
            players.remove(getCurrentPlayer());
            disqualified.add(getCurrentPlayer());
            currentIndex -= 1;
        }

        if (players.isEmpty()) {
            pause();
            Inspector.ret("Game.getAgentChange");
            return;
        }

        setCurrentPlayer(players.get((currentIndex + 1) % players.size()));
        Inspector.ret("Game.getAgentChange");
    }

    /**
     * Aktuális játékost lekérő függvény
     * Lekérhető általa, hogy melyik játékos van éppen most soron
     * @return - Az aktuális játékosnak a referenciája
     */
    private synchronized Player getCurrentPlayer() {
        Inspector.call("Game.getCurrentPlayer():Player");
        Inspector.ret("Game.getCurrentPlayer");
        return currentPlayer;
    }

    /**
     * Az aktuális játékost változtató függvény
     * Így ez a függvény alkalmas az aktuális játékos cseréjére
     * @param player - Az új aktuális játékosnak a referenciája
     */
    private synchronized void setCurrentPlayer(Player player) {
        Inspector.call("Game.setCurrentPlayer(Player)");
        currentPlayer = player;
        Inspector.ret("Game.setCurrentPlayer");
    }

    /**
     * Kezdő elhelyező függvény
     * Feladata, hogy elhelyezze a játékosok ágenseit a pálya kezdőállásán
     */
    private void placeAgents() {
        Collection<Field> startingFields = map.findStartingPositions(players.size());
        Field[] fields = startingFields.toArray(new Field[startingFields.size()]);

        for (int i = 0; i < players.size(); ++i) {
            Agent agent = players.get(i).getAgent();
            Field startingField = agent.getField();

            if (startingField != null) {
                startingField.onExit();
            }

            fields[i].onEnter(agent);
        }
    }

    /**
     * Szíverést konfiguráló függvény
     * Beállítja a timert, hogy megfelelő időközönként kezelje a játékvan lefolyó eseményeket
     * Az üteme jelen esetben 100 ms
     */
    private void setupTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isPaused) {
                    return;
                }

                currentPlayer.setTimeRemaining(currentPlayer.getTimeRemaining() - 100);

                if (currentPlayer.isOutOfTime()) {
                    System.out.println("Time out: " + currentPlayer);
                    onAgentChange();
                }
            }
        }, 0, 100);
    }
}
