package entities;

import utils.GameBoardManager;
import utils.RowColCoord;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static utils.GameBoardManager.BOARD_HEIGHT;
import static utils.GameBoardManager.BOARD_WIDTH;

public class Rook extends Entity{
    public Rook(byte color, GameBoardManager gbm) {
        super(color, gbm);
    }

    @Override
    public LinkedList<RowColCoord> getPotMoves(RowColCoord curCoord) {
        LinkedList<RowColCoord> moves = new LinkedList<>();
        addMovesHelper(moves, curCoord, UP);
        addMovesHelper(moves, curCoord, DOWN);
        addMovesHelper(moves, curCoord, LEFT);
        addMovesHelper(moves, curCoord, RIGHT);
        return moves;
    }

    @Override
    public void move() {

    }

    @Override
    public void loadImg() {
        try {
            if (color == BLACK) {
                image = ImageIO.read(new File("res/black_rook.png"));
            } else {
                image = ImageIO.read(new File("res/white_rook.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
