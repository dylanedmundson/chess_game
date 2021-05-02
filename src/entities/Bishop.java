package entities;

import utils.RowColCoord;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Bishop extends Entity{
    public Bishop(byte color) {
        super(color);
    }

    @Override
    public LinkedList<RowColCoord> move(RowColCoord curCoord) {
        return null;
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
}
