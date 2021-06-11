package entities;

import utils.GameBoardManager;
import utils.RowColCoord;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * @see Entity
 */
public class Bishop extends Entity{
    public Bishop(byte color, GameBoardManager gbm) {
        super(color, gbm);
    }

    @Override
    public LinkedList<RowColCoord> getPotMoves(RowColCoord curCoord) {
        LinkedList<RowColCoord> moves = new LinkedList<>();
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
    public void loadImg(){
        try {
            if (color == BLACK) {
                image = ImageIO.read(new File("res/black_bishop.png"));
            } else {
                image = ImageIO.read(new File("res/white_bishop.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRank() {
        return BISHOP_RANK;
    }
}
