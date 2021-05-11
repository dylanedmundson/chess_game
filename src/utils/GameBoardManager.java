package utils;

import entities.*;
import gui.GameBoard;

import java.awt.*;
import java.util.LinkedList;

//TODO: implement turn base
//TODO: save game state
public class GameBoardManager {
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;
    private static final Color HIGHLIGHT = new Color(255, 0, 0, 100);
    private static final int DEBOUNCE_CLICK = 200;

    private Entity[][] elements;
    private boolean[][] isHighlighted = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
    private boolean isClicked = false;
    private int xClick;
    private int yClick;
    private RowColCoord lasClickCoord;
    private LinkedList<RowColCoord> lastMoves;
    private long lastClickTime;

    public GameBoardManager() {

        elements = new Entity[BOARD_HEIGHT][BOARD_WIDTH];
        elements[0] = initRoyalty(Entity.BLACK);
        elements[1] = initPawnRow(Entity.BLACK);
        elements[6] = initPawnRow(Entity.WHITE);
        elements[7] = initRoyalty(Entity.WHITE);

        lastClickTime = System.currentTimeMillis();

    }

    private Entity[] initRoyalty(byte color) {
        Entity[] royalty = new Entity[] {
                new Rook(color, this),
                new Knight(color, this),
                new Bishop(color, this),
                new Queen(color, this),
                new King(color, this),
                new Bishop(color, this),
                new Knight(color, this),
                new Rook(color, this)
        };
        return royalty;
    }

    private Entity[] initPawnRow(byte color) {
        Entity[] pawns = new Entity[BOARD_WIDTH];
        for (int i = 0; i < 8; i++) {
            pawns[i] = new Pawn(color, this);
        }
        return pawns;
    }

    public void render(Graphics g) {
        for (int row = 0; row < elements.length; row++) {
            for (int col = 0; col < elements[row].length; col++) {
                if (elements[row][col] != null) {
                    int x = col * GameBoard.SQUARE_SIZE + GameBoard.BOARD_START +
                            ((GameBoard.SQUARE_SIZE - elements[row][col].getImage().getWidth()) / 2);
                    int y = row * GameBoard.SQUARE_SIZE + GameBoard.BOARD_START +
                            ((GameBoard.SQUARE_SIZE - elements[row][col].getImage().getHeight()) / 2);
                    g.drawImage(elements[row][col].getImage(), x, y, null);
                }
                if (isHighlighted[row][col]) {
                    int x = col * GameBoard.SQUARE_SIZE + GameBoard.BOARD_START;
                    int y = row * GameBoard.SQUARE_SIZE + GameBoard.BOARD_START;
                    g.setColor(HIGHLIGHT);
                    g.fillRect(x, y, GameBoard.SQUARE_SIZE, GameBoard.SQUARE_SIZE);
                }
            }
        }
    }

    private boolean lastMovesContains(RowColCoord coord) {
        for(RowColCoord rc : lastMoves) {
            if (rc.equals(coord)) {
                return true;
            }
        }
        return false;
    }

    public void tick() {
        if (isClicked) {
            long nextClickTime = System.currentTimeMillis();
            if (nextClickTime - lastClickTime > DEBOUNCE_CLICK) {
                lastClickTime = nextClickTime;
                RowColCoord rowColCoord = CoordinateConverter.getPoint(this.xClick, this.yClick);
                if (lastMoves != null && lastMovesContains(rowColCoord) && !lasClickCoord.equals(rowColCoord)) {
                    elements[rowColCoord.row][rowColCoord.col] = elements[lasClickCoord.row][lasClickCoord.col];
                    elements[lasClickCoord.row][lasClickCoord.col] = null;
                    elements[rowColCoord.row][rowColCoord.col].move();
                    lasClickCoord = rowColCoord;
                    for (RowColCoord rc : lastMoves) {
                        isHighlighted[rc.row][rc.col] = false;
                    }
                    return;
                }
                if (rowColCoord != null && elements[rowColCoord.row][rowColCoord.col] != null) {
                    if (lasClickCoord != null) {
                        if (!lasClickCoord.equals(rowColCoord)) {
                            for (RowColCoord rc : lastMoves) {
                                isHighlighted[rc.row][rc.col] = false;
                            }
                        }
                    }
                    if (rowColCoord != null) {
                        LinkedList<RowColCoord> moves = elements[rowColCoord.row][rowColCoord.col].getPotMoves(rowColCoord);
                        if (moves == null) {
                            moves = new LinkedList<>();
                        }
                        moves.add(rowColCoord);
                        for (RowColCoord rc : moves) {
                            isHighlighted[rc.row][rc.col] = true;
                        }
                        lastMoves = moves;
                    }
                    lasClickCoord = rowColCoord;
                }
            }
        }
    }

    public void press(int x, int y) {
        isClicked = true;
        xClick = x;
        yClick = y;
    }

    public void release() {
        isClicked = false;
    }

    public Entity getEntityAt(int row, int col) {
        if (row < 0 || row >= GameBoardManager.BOARD_HEIGHT || col < 0 || col >= GameBoardManager.BOARD_WIDTH) {
            return null;
        } else {
            return elements[row][col];
        }
    }
}
