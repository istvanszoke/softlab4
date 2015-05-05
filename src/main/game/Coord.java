package game;

public class Coord {
    private final int row;
    private final int col;

    public static int manhattan_distance(Coord lhs, Coord rhs) {
        Coord diff = new Coord(lhs.row - rhs.row, lhs.col - rhs.col);
        return Math.abs(diff.row) + Math.abs(diff.col);
    }

    public Coord(int row, int col) {
        this.row = row;
        this.col = col;

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
