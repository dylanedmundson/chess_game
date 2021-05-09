package utils;

public class RowColCoord {
    public int row;
    public int col;

    public RowColCoord() {
        row = 0;
        col = 0;
    }

    public RowColCoord(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean equals(RowColCoord other) {
        if (other == null) return false;
        return other.row == this.row && other.col == this.col;
    }
}
