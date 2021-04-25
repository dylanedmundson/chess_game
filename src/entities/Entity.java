package entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {
    public static final byte BLACK = 0x0000;
    public static final byte WHITE = 0x0001;

    protected boolean isAlive;
    protected BufferedImage image;
    protected byte color;

    //TODO: create rest of pieces and overide loadImg()

    public Entity(byte color) {
        if (color != BLACK && color != WHITE) {
            throw new IllegalArgumentException("color must be black (0) or white (1)");
        }
        isAlive = true;
        this.color = color;
        try {
            loadImg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void collision(Entity other) {
        other.kill();
    }
    public void kill() {
        isAlive = false;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public BufferedImage getImage() {
        return image;
    }

    public abstract void move();
    public abstract void loadImg() throws IOException;
}
