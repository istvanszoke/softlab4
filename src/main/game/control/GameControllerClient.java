package game.control;

/**
 * Created by nyari on 2015.03.27..
 */
public abstract class GameControllerClient
{
    protected GameControllerSocket socket;

    public GameControllerClient(GameControllerSocket socket)
    {
        if (socket != null)
            this.socket = socket;
        else
            throw new NullPointerException();
    }

    void socketOpened()
    {

    }

    void socketClosed()
    {
        
    }
}
