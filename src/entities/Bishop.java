package entities;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bishop extends Entity{
    public Bishop(byte color) {
        super(color);
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
}
