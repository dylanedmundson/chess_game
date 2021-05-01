package entities;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Knight extends Entity{
    public Knight(byte color) {
        super(color);
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
}
