package game;

import java.awt.Component;
import java.util.List;

import agents.Agent;
import field.Field;
import game.control.GameControllerServer;
import game.control.GameControllerServerListener;
import game.control.GameControllerSocket;
import game.control.HumanController;
import game.handle.AgentHandle;
import game.handle.HandleListener;

/**
 * A játék logikai szintű vezérlését megvalósító osztály
 */
public class Game implements GameControllerServerListener, HeartbeatListener, HandleListener {
    /**
     * Játéktárolóra való referencia
     */
    private final GameStorage gameStorage;
    /**
     * Térképre való referencia
     */
    private final Map map;

    /**
     * Referencia a játékvezérlési kiszolgálóra
     */
    private final GameControllerServer controllerServer;

    /**
     * Egy emberi játékvezérlő referenciája
     */
    private final HumanController humanController;

    /**
     * Játék osztály konstruktora
     *
     * @param agents - A jétékban részvevő ágensek
     * @param map    - A térkép amelyen a játék játszdik
     */
    public Game(List<AgentHandle> agents, Map map) {
        controllerServer = new GameControllerServer(this);
        humanController = new HumanController();

        gameStorage = new GameStorage(agents);

        this.map = map;

        placeAgents();
        setAgentControllers();
        Heartbeat.subscribe(gameStorage);
        Heartbeat.subscribe(this);
    }

    /**
     * Elindítja a felfüggesztett játékot
     */
    public void start() {
        register(gameStorage.getCurrent());
        Heartbeat.resume();
    }

    /**
     * Felfüggeszti a futó játékot
     */
    public void pause() {
        Heartbeat.pause();
        deregister(gameStorage.getCurrent());
    }

    /**
     * Vezérlő regisztrálása a játékban
     *
     * @param component - Regisztrálandó vezérlő
     */
    public void registerController(Component component) {
        component.addKeyListener(humanController);
    }

    /**
     * Jelenlegi térkép lekérése
     *
     * @return - Az aktuális térkép
     */
    public Map getMap() {
        return map;
    }

    /**
     * Játékoscsre megvalósítása
     *
     * @param agent - Az ágens aki jelezte a váltást
     */
    @Override
    public void onAgentChange(Agent agent) {
        Heartbeat.pause();
        AgentHandle handle = gameStorage.get(agent);

        if (handle == null) {
            return;
        }

        handle.onTurnEnd();
    }

    /**
     * Sima léptetés megvalósítása
     *
     * @param handle - Ágenskezelő referencia
     */
    @Override
    public void onRegularTurn(AgentHandle handle) {
        Heartbeat.pause();

        deregister(handle);
        gameStorage.update();
        register(gameStorage.getCurrent());

        Heartbeat.resume();
    }

    /**
     * Ágensválltó függvény
     * Feladata, hogy a soron következő ágensre adja át vezértlést
     */
    @Override
    public void onOutOfTime(AgentHandle handle) {
        Heartbeat.pause();

        deregister(handle);
        gameStorage.update();

        System.out.println("Timed out " + handle);

        if (isGameOver()) {
            endGame();
            return;
        }

        register(gameStorage.getCurrent());

        Heartbeat.resume();
    }

    /**
     * Ágens halálát lekezelő függvény
     *
     * @param handle - Ágenskezelő referenciája
     */
    @Override
    public void onAgentDeath(AgentHandle handle) {
        Heartbeat.pause();

        deregister(handle);
        gameStorage.update();

        System.out.println("Killed " + handle);

        if (isGameOver()) {
            endGame();
            return;
        }

        register(gameStorage.getCurrent());
        Heartbeat.resume();
    }

    /**
     * Időbeliség lekezelése a szívverésre
     *
     * @param deltaTime - mennyi idő telt el az utolsó szívütés óta
     */
    @Override
    public void onTick(long deltaTime) {
        //TODO:Spawn vacuum robots or do anything time related
    }

    /**
     * Kezdő elhelyező függvény
     * Feladata, hogy elhelyezze a játékosok ágenseit a pálya kezdőállásán
     */
    private void placeAgents() {
        List<AgentHandle> inPlay = gameStorage.getInPlay();
        List<Field> startingFields = map.findStartingPositions(inPlay.size());

        for (int i = 0; i < inPlay.size(); ++i) {
            Agent agent = inPlay.get(i).getAgent();
            Field startingField = agent.getField();

            if (startingField != null) {
                startingField.onExit();
            }

            startingFields.get(i).onEnter(agent);
        }
    }

    /**
     * Ágensvezérlők beállítása
     */
    private void setAgentControllers() {
        for (AgentHandle player : gameStorage) {
            Agent agent = player.getAgent();
            GameControllerSocket socket = controllerServer.createSocketForAgent(agent);
            humanController.addControllerSocket(socket);
        }
    }

    /**
     * Játék végének lekezelését végző függvény.
     */
    //TODO: Real game finishing logic
    private void endGame() {
        pause();
        Heartbeat.unsubscribe(gameStorage);
        Heartbeat.unsubscribe(this);
        System.out.println("Game finished");
    }

    /**
     * Beállítja az éppen aktuális Ágenst
     *
     * @param handle - Ágenskezelő
     */
    private void register(AgentHandle handle) {
        controllerServer.notifyControllerSocketOpened(handle.getAgent());
        handle.register(this);
    }

    /**
     * Lekapcsolja az éppen aktuális Ágenst
     *
     * @param handle - Ágenskezelő
     */
    private void deregister(AgentHandle handle) {
        controllerServer.notifyControllerSocketClosed(handle.getAgent());
        handle.deregister(this);
    }

    /**
     * Játék vége állapot lekérdezése
     *
     * @return - Jeáték vége állapot
     */
    private boolean isGameOver() {
        if (gameStorage.getPlayers().isEmpty()) {
            return true;
        }

        for (AgentHandle handle : gameStorage.getPlayers()) {
            if (!handle.isDisqualified()) {
                return false;
            }
        }

        return true;
    }
}
