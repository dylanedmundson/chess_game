package utils;

public class RowColCoord {
    public int row;
    public int col;

    public boolean equals(RowColCoord other) {
        if (other == null) return false;
        return other.row == this.row && other.col == this.col;
    }
}
