package utils;

import entities.*;
import gui.GameBoard;
import gui.TakenPiecesGUI;
import gui.ToolPanel;
import sun.plugin2.util.ColorUtil;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GameBoardManager {
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;
    private static final Color HIGHLIGHT = new Color(255, 0, 0, 100);
    private static final int DEBOUNCE_CLICK = 200;
    public static final byte PLAYER1 = 0x0000;
    public static final byte PLAYER2 = 0x0001;

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
        if (checkForCheck()) {
            if (isCheckMate()) {
                gameOver();
            }
        }
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
                    lastMoves.clear();
                    lasClickCoord = null;
                    return true;
                }
                //click for highlighting possible moves
                //handles highlighting squares
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
                        LinkedList<RowColCoord> movesTemp = elements[rowColCoord.row][rowColCoord.col].getPotMoves(rowColCoord);
                        LinkedList<RowColCoord> moves = new LinkedList<>();
                        moves.add(rowColCoord);
                        for (int i = 0; i < movesTemp.size(); i++) {
                            RowColCoord mv = movesTemp.get(i);
                            if (preventsCheck(mv, rowColCoord)) {
                                moves.add(movesTemp.get(i));
                            }
                        }
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

    private void gameOver() {
        gb.gameOverScreen.setGameOver(playersTurn);
    }

    private boolean isCheckMate() {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                RowColCoord click = new RowColCoord(row, col);
                Entity e = getEntityAt(click);
                if (e != null) {
                    List<RowColCoord> moves = e.getPotMoves(click);
                    for (RowColCoord mv : moves) {
                        if (e.getColor() == playersTurn && preventsCheck(mv, click)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean preventsCheck(RowColCoord mv, RowColCoord click) {
        Entity mvHolder = elements[mv.row][mv.col];
        Entity clickHolder = elements[click.row][click.col];
        elements[mv.row][mv.col] = elements[click.row][click.col];
        elements[click.row][click.col] = null;
        boolean val =  !checkForCheck();
        elements[click.row][click.col] = clickHolder;
        elements[mv.row][mv.col] = mvHolder;
        return val;
    }

    private boolean checkForCheck() {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                RowColCoord rc = new RowColCoord(row, col);
                Entity e = getEntityAt(rc);
                if (e != null) {
                    if (playersTurn == PLAYER1) {
                        if (e.getColor() != Entity.WHITE) {
                            List<RowColCoord> moves = elements[row][col].getPotMoves(new RowColCoord(row, col));
                            for (RowColCoord rowColCoord : moves) {
                                Entity nextE = getEntityAt(rowColCoord);
                                if (nextE != null && nextE.getRank() == Entity.KING_RANK && nextE.getColor() == Entity.WHITE) {
                                    //do something
                                    return true;
                                }
                            }
                        }
                    } else {
                        if (e.getColor() != Entity.BLACK) {
                            List<RowColCoord> moves = elements[row][col].getPotMoves(new RowColCoord(row, col));
                            for (RowColCoord rowColCoord : moves) {
                                Entity nextE = getEntityAt(rowColCoord);
                                if (nextE != null && nextE.getRank() == Entity.KING_RANK && nextE.getColor() == Entity.BLACK) {
                                    //do something
                                    return true;
                                }
                            }
                        }
                    }
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

    public Entity getEntityAt(RowColCoord rowColCoord) {
        return getEntityAt(rowColCoord.row, rowColCoord.col);
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

    public String serialize() {
        String results = "";
        results += playersTurn + "\n";
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                Entity e = getEntityAt(row, col);
                String eName;
                if (e == null) {
                    eName = "n";
                } else {
                    eName = getEntityAt(row, col).getClass().getName();
                    eName += "=" + e.getColor();
                    if (e.getClass().getName().equals("entities.Pawn")) {
                        eName += "=" + ((Pawn)e).isFirstMove();
                    }
                }
                results += eName;
                if (col != BOARD_WIDTH - 1) {
                    results += ",";
                }
            }
            results += "\n";
        }
        return results + "__EOF\n";
    }

    public void deserialize(Scanner input) {
        Entity[][] entities = new Entity[GameBoardManager.BOARD_HEIGHT][GameBoardManager.BOARD_WIDTH];
        byte playersTurn = Byte.parseByte(input.nextLine());
        int row = 0;
        String nextLine;
        while (!(nextLine = input.nextLine()).equals("__EOF")) {
            String[] splitLine = nextLine.split(",");
            for (int col = 0; col < splitLine.length; col++) {
                if (splitLine[col].equals("n")) {
                    entities[row][col] = null;
                } else {
                    String[] splitItem = splitLine[col].split("=");
                    if (splitItem.length > 2) {
                        if (splitItem[0].equals("entities.Pawn")) {
                            byte color = Byte.parseByte(splitItem[1]);
                            entities[row][col] = createEntity(splitItem[0], color, this);
                            ((Pawn) entities[row][col]).setFirstMove(Boolean.parseBoolean(splitItem[2]));
                        }
                    } else {
                        byte color = Byte.parseByte(splitItem[1]);
                        entities[row][col] = createEntity(splitItem[0], color, this);
                    }
                }
            }
            row++;
        }
        loadGameData(entities, playersTurn);
    }

    private Entity createEntity(String className, byte color, GameBoardManager gbm) {
        if (className.equals("entities.Rook")) {
            return new Rook(color, gbm);
        } else if (className.equals("entities.Knight")) {
            return new Knight(color, gbm);
        } else if (className.equals("entities.Bishop")) {
            return new Bishop(color, gbm);
        } else if (className.equals("entities.Queen")) {
            return new Queen(color, gbm);
        } else if (className.equals("entities.King")) {
            return new King(color, gbm);
        } else if (className.equals("entities.Pawn")) {
            return new Pawn(color, gbm);
        } else {
            return null;
        }
    }
}
