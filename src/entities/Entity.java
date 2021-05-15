package entities;

import utils.GameBoardManager;
import utils.RowColCoord;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static utils.GameBoardManager.BOARD_HEIGHT;
import static utils.GameBoardManager.BOARD_WIDTH;

public abstract class Entity {
    public static final byte BLACK = 0x0000;
    public static final byte WHITE = 0x0001;

    protected static final byte LEFT = 0x0000;
    protected static final byte RIGHT = 0x0001;
    protected static final byte UP = 0x0002;
    protected static final byte DOWN = 0x0003;
    protected static final byte UP_LEFT = 0x0004;
    protected static final byte UP_RIGHT = 0x0005;
    protected static final byte DOWN_LEFT = 0x0006;
    protected static final byte DOWN_RIGHT = 0x0007;

    protected boolean isAlive;
    protected BufferedImage image;
    protected byte color;
    protected GameBoardManager gbm;

    public Entity(byte color, GameBoardManager gbm) {
        if (color != BLACK && color != WHITE) {
            throw new IllegalArgumentException("color must be black (0) or white (1)");
        }
        isAlive = true;
        this.color = color;
        this.gbm = gbm;
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

    public byte getColor() {
        return this.color;
    }

    /**
     * cartesian coordinate system, up is -y down is +y left -x right +x, move one space = 1
     */
    public abstract LinkedList<RowColCoord> getPotMoves(RowColCoord curCoord);
    public abstract void move();
    public abstract void loadImg();

    protected void addMovesHelper(LinkedList<RowColCoord> moves, RowColCoord curCoord, byte dir) {

        if (dir == UP) {
            for (int row = curCoord.row - 1; row >= 0; row--) {
                Entity next = gbm.getEntityAt(row, curCoord.col);
                if (next != null) {
                    if (next.color != color) {
                        moves.add(new RowColCoord(row, curCoord.col));
                    }
                    return;
                }
                moves.add(new RowColCoord(row, curCoord.col));
            }
        } else if (dir == DOWN) {
            for (int row = curCoord.row + 1; row < BOARD_HEIGHT; row++) {
                Entity next = gbm.getEntityAt(row, curCoord.col);
                if (next != null) {
                    if (next.color != color) {
                        moves.add(new RowColCoord(row, curCoord.col));
                    }
                    return;
                }
                moves.add(new RowColCoord(row, curCoord.col));
            }
        } else if (dir == RIGHT) {
            for (int col = curCoord.col + 1; col < BOARD_WIDTH; col++) {
                Entity next = gbm.getEntityAt(curCoord.row, col);
                if (next != null) {
                    if (next.color != color) {
                        moves.add(new RowColCoord(curCoord.row, col));
                    }
                    return;
                }
                moves.add(new RowColCoord(curCoord.row, col));
            }
        } else if (dir == LEFT) {
            for (int col = curCoord.col - 1; col >= 0; col--) {
                Entity next = gbm.getEntityAt(curCoord.row, col);
                if (next != null) {
                    if (next.color != color) {
                        moves.add(new RowColCoord(curCoord.row, col));
                    }
                    return;
                }
                moves.add(new RowColCoord(curCoord.row, col));
            }
        } else if (dir == UP_LEFT) {
            for (int row = curCoord.row - 1, col = curCoord.col - 1;
                 row >= 0 && col >= 0; row--, col--) {
                Entity next = gbm.getEntityAt(row, col);
                if (next != null) {
                    if (next.color != color) {
                        moves.add(new RowColCoord(row, col));
                    }
                    return;
                }
                moves.add(new RowColCoord(row, col));
            }
        } else if (dir == DOWN_LEFT) {
            for (int row = curCoord.row + 1, col = curCoord.col - 1;
                 row < BOARD_HEIGHT && col >= 0; row++, col--) {
                Entity next = gbm.getEntityAt(row, col);
                if (next != null) {
                    if (next.color != color) {
                        moves.add(new RowColCoord(row, col));
                    }
                    return;
                }
                moves.add(new RowColCoord(row, col));
            }
        } else if (dir == UP_RIGHT) {
            for (int row = curCoord.row - 1, col = curCoord.col + 1;
                 row >= 0 && col < BOARD_WIDTH; row--, col++) {
                Entity next = gbm.getEntityAt(row, col);
                if (next != null) {
                    if (next.color != color) {
                        moves.add(new RowColCoord(row, col));
                    }
                    return;
                }
                moves.add(new RowColCoord(row, col));
            }

        } else if (dir == DOWN_RIGHT) {
            for (int row = curCoord.row + 1, col = curCoord.col + 1;
                 row < BOARD_HEIGHT && col < BOARD_WIDTH; row++, col++) {
                Entity next = gbm.getEntityAt(row, col);
                if (next != null) {
                    if (next.color != color) {
                        moves.add(new RowColCoord(row, col));
                    }
                    return;
                }
                moves.add(new RowColCoord(row, col));
            }
        } else {
            throw new IllegalArgumentException("Improper input for move dir");
        }
    }
}
