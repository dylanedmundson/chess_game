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
    public Pawn(byte color) {
        super(color);
    }

    @Override
    public LinkedList<RowColCoord> move(RowColCoord curCoord) {
        LinkedList<RowColCoord> moves = new LinkedList<>();
        if (isFirstMove) {
            if (color == BLACK) { //down moves
                if (curCoord.row + 1 < GameBoardManager.BOARD_HEIGHT) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row + 1;
                    coord.col = curCoord.col;
                    moves.add(coord);
                }
                if (curCoord.row + 2 < GameBoardManager.BOARD_HEIGHT) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row + 2;
                    coord.col = curCoord.col;
                    moves.add(coord);
                }
            } else { //up moves
                if (curCoord.row - 1 >= 0) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row - 1;
                    coord.col = curCoord.col;
                    moves.add(coord);
                }
                if (curCoord.row - 2 >= 0) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row - 2;
                    coord.col = curCoord.col;
                    moves.add(coord);
                }
            }
            isFirstMove = false;
        } else {
            if (color == BLACK) { //down moves
                if (curCoord.row + 1 < GameBoardManager.BOARD_HEIGHT) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row + 1;
                    coord.col = curCoord.col;
                    moves.add(coord);
                }
            } else { //up moves
                if (curCoord.row - 1 >= 0) {
                    RowColCoord coord = new RowColCoord();
                    coord.row = curCoord.row - 1;
                    coord.col = curCoord.col;
                    moves.add(coord);
                }
            }
        }
        return moves;
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
}
