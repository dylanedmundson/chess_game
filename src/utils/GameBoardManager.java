package utils;

import entities.*;
import gui.GameBoard;
import gui.TakenPiecesGUI;
import gui.ToolPanel;

import java.awt.*;
import java.util.LinkedList;

//TODO: save game state
//TODO: add sorting algorithm for sorting pieces taken on side
//TODO: add more game logic (checkmate, etc.) and instructions for that logic
public class GameBoardManager {
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;
    private static final Color HIGHLIGHT = new Color(255, 0, 0, 100);
    private static final int DEBOUNCE_CLICK = 200;
    private static final byte PLAYER1 = 0x0000;
    private static final byte PLAYER2 = 0x0001;

    private Entity[][] elements;
    private boolean[][] isHighlighted = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
    private boolean isClicked = false;
    private int xClick;
    private int yClick;
    private RowColCoord lasClickCoord;
    private LinkedList<RowColCoord> lastMoves;
    private long lastClickTime;
    private byte playersTurn;
    private GameBoard gb;

    private TakenPiecesGUI takenPiecesGUI;

    public GameBoardManager() {
        elements = new Entity[BOARD_HEIGHT][BOARD_WIDTH];
        elements[0] = initRoyalty(Entity.BLACK);
        elements[1] = initPawnRow(Entity.BLACK);
        elements[6] = initPawnRow(Entity.WHITE);
        elements[7] = initRoyalty(Entity.WHITE);

        lastClickTime = System.currentTimeMillis();
        playersTurn = PLAYER1;

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

        if (handleClick()) {
            switchPlayer();
        }
    }

    private void switchPlayer() {
        if (playersTurn == PLAYER1) {
            playersTurn = PLAYER2;
        } else {
            playersTurn = PLAYER1;
        }
        ((ToolPanel)gb.tools).nextInstruction();
    }

    private boolean handleClick() {
        if (isClicked) {
            long nextClickTime = System.currentTimeMillis();
            if (nextClickTime - lastClickTime > DEBOUNCE_CLICK) {
                lastClickTime = nextClickTime;
                RowColCoord rowColCoord = CoordinateConverter.getPoint(this.xClick, this.yClick);
                //click for making move
                //TODO: figure out weird bug and try to reproduce with queen double taking
                if (lastMoves != null && lastMovesContains(rowColCoord) && !lasClickCoord.equals(rowColCoord)) {
                    if (elements[rowColCoord.row][rowColCoord.col] != null && playersTurn == PLAYER1) {
                        takenPiecesGUI.addPieceP1(elements[rowColCoord.row][rowColCoord.col]);
                    } else if (elements[rowColCoord.row][rowColCoord.col] != null && playersTurn == PLAYER2){
                        takenPiecesGUI.addPieceP2(elements[rowColCoord.row][rowColCoord.col]);
                    }
                    elements[rowColCoord.row][rowColCoord.col] = elements[lasClickCoord.row][lasClickCoord.col];
                    elements[lasClickCoord.row][lasClickCoord.col] = null;
                    elements[rowColCoord.row][rowColCoord.col].move();
                    lasClickCoord = rowColCoord;
                    for (RowColCoord rc : lastMoves) {
                        isHighlighted[rc.row][rc.col] = false;
                    }
                    return true;
                }
                //click for highlighting possible moves
                if (rowColCoord != null && elements[rowColCoord.row][rowColCoord.col] != null &&
                        isPlayerPiece(rowColCoord)) {
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
        return false;
    }

    private boolean isPlayerPiece(RowColCoord rowColCoord) {
        if (playersTurn == PLAYER1) {
            return elements[rowColCoord.row][rowColCoord.col].getColor() == Entity.WHITE;
        } else {
            return elements[rowColCoord.row][rowColCoord.col].getColor() == Entity.BLACK;
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

    public void loadGameData(Entity[][] elements, byte playersTurn) {
        this.elements = elements;
        this.playersTurn = playersTurn;
    }

    public byte getPlayersTurn() {
        return playersTurn;
    }

    public void setGameBoard(GameBoard gb) {
        this.gb = gb;
    }

    public void setTakenPiecesGUI(TakenPiecesGUI takenPiecesGUI) {
        this.takenPiecesGUI = takenPiecesGUI;
    }
}
