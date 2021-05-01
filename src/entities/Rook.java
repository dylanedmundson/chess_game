package entities;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Rook extends Entity{
    public Rook(byte color) {
        super(color);
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