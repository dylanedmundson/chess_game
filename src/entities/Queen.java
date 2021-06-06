package entities;

import utils.GameBoardManager;
import utils.RowColCoord;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Queen extends Entity{
    public Queen(byte color, GameBoardManager gbm) {
        super(color, gbm);
    }

    @Override
    public LinkedList<RowColCoord> getPotMoves(RowColCoord curCoord) {
        LinkedList<RowColCoord> moves = new LinkedList<>();
        addMovesHelper(moves, curCoord, UP);
        addMovesHelper(moves, curCoord, DOWN);
        addMovesHelper(moves, curCoord, LEFT);
        addMovesHelper(moves, curCoord, RIGHT);
        addMovesHelper(moves, curCoord, UP_LEFT);
        addMovesHelper(moves, curCoord, UP_RIGHT);
        addMovesHelper(moves, curCoord, DOWN_LEFT);
        addMovesHelper(moves, curCoord, DOWN_RIGHT);
        return moves;
    }

    @Override
    public void move() {

    }


    @Override
    public void loadImg() {
        try {
            if (color == BLACK) {
                image = ImageIO.read(new File("res/black_queen.png"));
            } else {
                image = ImageIO.read(new File("res/white_queen.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRank() {
        return QUEEN_RANK;
    }
}
