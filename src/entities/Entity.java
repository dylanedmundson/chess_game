package entities;

import utils.GameBoardManager;
import utils.RowColCoord;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static utils.GameBoardManager.BOARD_HEIGHT;
import static utils.GameBoardManager.BOARD_WIDTH;

/**
 * GameObject that holds state of each piece and specialized functionality of piece type
 */
public abstract class Entity implements Comparable<Entity> {
    public static final byte BLACK = 0x0001;
    public static final byte WHITE = 0x0000;

    protected static final byte LEFT = 0x0000;
    protected static final byte RIGHT = 0x0001;
    protected static final byte UP = 0x0002;
    protected static final byte DOWN = 0x0003;
    protected static final byte UP_LEFT = 0x0004;
    protected static final byte UP_RIGHT = 0x0005;
    protected static final byte DOWN_LEFT = 0x0006;
    protected static final byte DOWN_RIGHT = 0x0007;

    public static final int KING_RANK = 1;
    public static final int QUEEN_RANK = 2;
    public static final int ROOK_RANK = 3;
    public static final int KNIGHT_RANK = 4;
    public static final int BISHOP_RANK = 5;
    public static final int PAWN_RANK = 6;

    protected boolean isAlive;
    protected BufferedImage image;
    protected byte color;
    protected GameBoardManager gbm;

    /**
     * initializes entity with given color and GameBoardManager reference
     * @param color of entity WHITE or BLACK
     * @param gbm reference to GamgeBoardManager
     */
    public Entity(byte color, GameBoardManager gbm) {
        if (color != BLACK && color != WHITE) {
            throw new IllegalArgumentException("color must be black (0) or white (1)");
        }
        isAlive = true;
        this.color = color;
        this.gbm = gbm;
        loadImg();
    }

    /**
     * @return entities image
     */
    public BufferedImage getImage() { return image; }

    public byte getColor() {
        return this.color;
    }

    /**
     * gets possible moves for pieces
     * @param curCoord the current position of the entity
     * @return list of RowColCoord's of possible moves
     */
    public abstract LinkedList<RowColCoord> getPotMoves(RowColCoord curCoord);

    /**
     * handles any state changes when a move is made
     */
    public abstract void move();

    /**
     * loads the image of the entity
     */
    public abstract void loadImg();

    /**
     * a helper method used for generating straight and diagonal moves
     * @param moves list of moves, that generated moves will be added to
     * @param curCoord entity's current RowColCoord
     * @param dir direction of movement (UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT)
     */
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

    /**
     * @return rank associated with entity (for sorting in TakenPiecesGUI)
     */
    public abstract int getRank();

    @Override
    public int compareTo(Entity o) {
        return this.getRank() - o.getRank(); //returns pos if this rank is higher than others
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(this.getClass()) && ((Entity)obj).color == this.color;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
