package game;

public class BadMapSizeException extends Exception {
    private final int maxSize;
    private final int numPlayers;

    public BadMapSizeException(int maxSize, int numPlayers) {
        this.maxSize = maxSize;
        this.numPlayers = numPlayers;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
