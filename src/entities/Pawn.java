package entities;


import sun.awt.image.ImageWatched;
import utils.GameBoardManager;
import utils.RowColCoord;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Pawn extends Entity {

    private boolean isFirstMove = true;
    public Pawn(byte color, GameBoardManager gbm) {
        super(color, gbm);
    }

    @Override
    public LinkedList<RowColCoord> getPotMoves(RowColCoord curCoord) {
        LinkedList<RowColCoord> moves = new LinkedList<>();
        addAttacks(curCoord, moves);
        if (isFirstMove) {
            if (color == BLACK) { //down moves
                if (curCoord.row + 1 < GameBoardManager.BOARD_HEIGHT) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row + 1;
                    coord.col = curCoord.col;
                    if (gbm.getEntityAt(coord.row, coord.col) != null) {
                        return moves;
                    }
                    moves.add(coord);
                }
                if (curCoord.row + 2 < GameBoardManager.BOARD_HEIGHT) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row + 2;
                    coord.col = curCoord.col;
                    if (gbm.getEntityAt(coord.row, coord.col) != null) {
                        return moves;
                    }
                    moves.add(coord);
                }
            } else { //up moves
                if (curCoord.row - 1 >= 0) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row - 1;
                    coord.col = curCoord.col;
                    if (gbm.getEntityAt(coord.row, coord.col) != null) {
                        return moves;
                    }
                    moves.add(coord);
                }
                if (curCoord.row - 2 >= 0) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row - 2;
                    coord.col = curCoord.col;
                    if (gbm.getEntityAt(coord.row, coord.col) != null) {
                        return moves;
                    }
                    moves.add(coord);
                }
            }
        } else {
            if (color == BLACK) { //down moves
                if (curCoord.row + 1 < GameBoardManager.BOARD_HEIGHT) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row + 1;
                    coord.col = curCoord.col;
                    if (gbm.getEntityAt(coord.row, coord.col) != null) {
                        return moves;
                    }
                    moves.add(coord);
                }
            } else { //up moves
                if (curCoord.row - 1 >= 0) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row - 1;
                    coord.col = curCoord.col;
                    if (gbm.getEntityAt(coord.row, coord.col) != null) {
                        return moves;
                    }
                    moves.add(coord);
                }
            }
        }
        return moves;
    }

    private void addAttacks(RowColCoord curCoord, LinkedList<RowColCoord> moves) {
        RowColCoord attackL = new RowColCoord();
        RowColCoord attackR = new RowColCoord();
        if (color == Entity.WHITE) {
            attackL.row = curCoord.row - 1;
            attackR.row = curCoord.row - 1;
        } else {
            attackL.row = curCoord.row + 1;
            attackR.row = curCoord.row + 1;
        }
        attackL.col = curCoord.col - 1;
        attackR.col = curCoord.col + 1;
        if (gbm.getEntityAt(attackL.row, attackL.col) != null &&
                gbm.getEntityAt(attackL.row, attackL.col).color != color) {
            moves.add(attackL);
        }
        if (gbm.getEntityAt(attackR.row, attackR.col) != null &&
                gbm.getEntityAt(attackR.row, attackR.col).color != color) {
            moves.add(attackR);
        }
    }

    @Override
    public void move() {
        if (isFirstMove) {
            isFirstMove = false;
        }
    }


    @Override
    public void loadImg() {
        try {
            if (color == BLACK) {
                image= ImageIO.read(new File("res/black_pawn.png"));
            } else {
                image = ImageIO.read(new File("res/white_pawn.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //resize image
        // BufferedImage resizedImg = new BufferedImage(GameBoard.SQUARE_SIZE, GameBoard.SQUARE_SIZE,
        //         BufferedImage.TYPE_INT_ARGB);
        // Graphics2D g2 = resizedImg.createGraphics();
        //
        // g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // g2.drawImage(image, 0, 0, GameBoard.SQUARE_SIZE, GameBoard.SQUARE_SIZE, null);
        // g2.dispose();
        //
        // image = resizedImg;
    }

    @Override
    protected int getRank() {
        return PAWN_RANK;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }
}
