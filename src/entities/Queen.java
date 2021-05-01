package entities;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Queen extends Entity{
    public Queen(byte color) {
        super(color);
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
}
