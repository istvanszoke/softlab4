package game;

import agents.Agent;
import field.Field;
import game.control.GameControllerServer;
import game.control.GameControllerSocket;
import inspector.Inspector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class Game implements ControllerListener {
    private final Timer timer;
    private boolean isPaused;
    private final int roundTime;

    private final ArrayList<Player> players;
    private final ArrayList<Player> disqualified;
    private Player currentPlayer;

    private final Map map;
    private final GameControllerServer controllerServer;
    private final ArrayList<HumanController> humanControllers;

    public Game(ArrayList<Player> players, Map map, int roundTime) {
        Inspector.call("Game.Game(ArrayList<Player>, Map, int)");
        controllerServer = new GameControllerServer(this);
        humanControllers = new ArrayList<HumanController>();
        timer = new Timer(true);
        isPaused = true;
        this.roundTime = roundTime;

        this.players = players;
        disqualified = new ArrayList<Player>();
        currentPlayer = this.players.get(0);

        this.map = map;

        placeAgents();
        setupTimer();
        setAgentControllers();
        Inspector.ret("Game.Game");
    }

    public void start() {
        isPaused = false;
    }

    public void pause() {
        isPaused = true;
    }

    public void reset() {
        players.addAll(disqualified);
        disqualified.clear();
        for (Player player : players) {
            player.setTimeRemaining(roundTime);
        }
        placeAgents();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getDisqualified() {
        return disqualified;
    }

    public void registerController(Component component) {
        for (HumanController controller : humanControllers) {
            component.addKeyListener(controller);
        }
    }

    public Agent getCurrentAgent() {
        Inspector.call("Game.getCurrentAgent():Agent");
        Agent tmp = getCurrentPlayer().getAgent();
        Inspector.ret("Game.getCurrentAgent()");
        return tmp;
    }

    public Map getMap() {
        return map;
    }

    @Override
    public void onAgentChange() {
        Inspector.call("Game.getAgentChange()");

        controllerServer.notifyControllerSocketClosed(getCurrentAgent());

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
        controllerServer.notifyControllerSocketOpened(getCurrentAgent());

        Inspector.ret("Game.getAgentChange");
    }

    private synchronized Player getCurrentPlayer() {
        Inspector.call("Game.getCurrentPlayer():Player");
        Inspector.ret("Game.getCurrentPlayer");
        return currentPlayer;
    }

    private synchronized void setCurrentPlayer(Player player) {
        Inspector.call("Game.setCurrentPlayer(Player)");
        currentPlayer = player;
        Inspector.ret("Game.setCurrentPlayer");
    }

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

    private void setAgentControllers() {
        for (Player player : players) {
            Agent agent = player.getAgent();
            GameControllerSocket socket;
            socket = controllerServer.createSocketForAgent(agent);
            HumanController newhomosapiensinterface = new HumanController(socket);
            humanControllers.add(newhomosapiensinterface);
        }
    }

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
