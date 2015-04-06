package game;

/**
 * Rendszerszintű szívverésnek a figyeléséhez szükséges interfész
 */
public interface HeartbeatListener {
    void onTick(long deltaTime);
}
