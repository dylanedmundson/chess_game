package entities;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Pawn extends Entity {

    public Pawn(byte color) {
        super(color);
    }

    @Override
    public void move() {

    }

    @Override
    public void loadImg() {
        try {
            if (color == BLACK) {
                image = ImageIO.read(new File("res/black_pawn.png"));
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
