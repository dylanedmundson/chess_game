package entities;

import utils.GameBoardManager;
import utils.RowColCoord;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import static utils.GameBoardManager.BOARD_HEIGHT;
import static utils.GameBoardManager.BOARD_WIDTH;

public class Knight extends Entity{
    public Knight(byte color, GameBoardManager gbm) {
        super(color, gbm);
    }

    @Override
    public LinkedList<RowColCoord> getPotMoves(RowColCoord curCoord) {
        LinkedList<RowColCoord> moves = new LinkedList<>();
        //Right half
        moves.add(new RowColCoord(curCoord.row - 2, curCoord.col + 1));
        moves.add(new RowColCoord(curCoord.row - 1, curCoord.col + 2));
        moves.add(new RowColCoord(curCoord.row + 1, curCoord.col + 2));
        moves.add(new RowColCoord(curCoord.row + 2, curCoord.col + 1));

        //left half
        moves.add(new RowColCoord(curCoord.row - 2, curCoord.col - 1));
        moves.add(new RowColCoord(curCoord.row - 1, curCoord.col - 2));
        moves.add(new RowColCoord(curCoord.row + 1, curCoord.col - 2));
        moves.add(new RowColCoord(curCoord.row + 2, curCoord.col - 1));
        for (int i = 0; i < moves.size(); i++) {
            int row = moves.get(i).row;
            int col = moves.get(i).col;
            if (row >= BOARD_HEIGHT || row < 0 || col >= BOARD_WIDTH || col < 0 ||
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
                image = ImageIO.read(new File("res/black_knight.png"));
            } else {
                image = ImageIO.read(new File("res/white_knight.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getRank() {
        return 4;
    }
}
