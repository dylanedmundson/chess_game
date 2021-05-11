package utils;

import static gui.GameBoard.*;


public class CoordinateConverter {
    public static RowColCoord getPoint(int x, int y) {
        if (x < BOARD_START || x >= BOARD_START + BOARD_WIDTH ||
                y < BOARD_START || y >= BOARD_START + BOARD_HEIGHT) {
            return null;
        } else {
            x -= BOARD_START;
            y -= BOARD_START;
            RowColCoord rowColCoord = new RowColCoord();
            rowColCoord.col = (x / SQUARE_SIZE) % (BOARD_WIDTH / SQUARE_SIZE);
            rowColCoord.row = (y / SQUARE_SIZE) % (BOARD_HEIGHT / SQUARE_SIZE);
            return rowColCoord;
        }
    }
}
