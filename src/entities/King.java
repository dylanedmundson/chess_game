package entities;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class King extends Entity {

    public King(byte color) {
        super(color);
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
