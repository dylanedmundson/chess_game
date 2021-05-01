package entities;

import gui.InputAdapater;

import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {
    public static final byte BLACK = 0x0000;
    public static final byte WHITE = 0x0001;

    protected boolean isAlive;
    protected BufferedImage image;
    protected byte color;
    protected int row;
    protected int col;

    //TODO: create rest of pieces and override loadImg()

    public Entity(byte color) {
        if (color != BLACK && color != WHITE) {
            throw new IllegalArgumentException("color must be black (0) or white (1)");
        }
        isAlive = true;
        this.color = color;
        this.row = row;
        this.col = col;
        loadImg();
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

    public BufferedImage getImage() { return image; }

    /**
     * cartesian coordinate system, up is -y down is +y left -x right +x, move one space = 1
     */
    public abstract void move();
    public abstract void loadImg();
}
