package entities;

import utils.GameBoardManager;
import utils.RowColCoord;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class King extends Entity {

    public King(byte color, GameBoardManager gbm) {
        super(color, gbm);
    }

    @Override
    public LinkedList<RowColCoord> getPotMoves(RowColCoord curCoord) {
        LinkedList<RowColCoord> moves = new LinkedList<>();
        moves.add(new RowColCoord(curCoord.row + 1, curCoord.col));
        moves.add(new RowColCoord(curCoord.row + 1, curCoord.col + 1));
        moves.add(new RowColCoord(curCoord.row + 1, curCoord.col - 1));
        moves.add(new RowColCoord(curCoord.row - 1, curCoord.col));
        moves.add(new RowColCoord(curCoord.row - 1, curCoord.col + 1));
        moves.add(new RowColCoord(curCoord.row - 1, curCoord.col - 1));
        moves.add(new RowColCoord(curCoord.row - 1, curCoord.col));
        moves.add(new RowColCoord(curCoord.row, curCoord.col - 1));
        moves.add(new RowColCoord(curCoord.row, curCoord.col + 1));
        for (int i = 0; i < moves.size(); i++) {
            int row = moves.get(i).row;
            int col = moves.get(i).col;
            if (row >= gbm.BOARD_HEIGHT || row < 0 || col >= gbm.BOARD_WIDTH || col < 0 ||
                    (gbm.getEntityAt(row, col) != null &&
                    gbm.getEntityAt(row, col).color == color)) {
                moves.remove(i);
                i--;
            }
        }
        return moves;
    }

    @Override
    public void move() {

    }

    @Override
    public void loadImg() {
        try {
            if (color == BLACK) {
                image = ImageIO.read(new File("res/black_king.png"));
            } else {
                image = ImageIO.read(new File("res/white_king.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
