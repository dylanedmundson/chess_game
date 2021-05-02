package entities;

import utils.RowColCoord;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class King extends Entity {

    public King(byte color) {
        super(color);
    }

    @Override
    public LinkedList<RowColCoord> move(RowColCoord curCoord) {
        return null;
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
