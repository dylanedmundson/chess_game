package utils;

import entities.*;
import gui.GameBoard;
import gui.InputAdapater;

import java.awt.*;

public class GameBoardManager {
    private static final int BOARD_WIDTH = 8;
    private static final int BOARD_HEIGHT = 8;

    private Entity[][] elements;
    private InputAdapater.MouseClickHelper mouseClickHelper = GameBoard.INPUT_ADAPATER.mouseClickHelper;

    public GameBoardManager() {

        elements = new Entity[BOARD_HEIGHT][BOARD_WIDTH];
        elements[0] = initRoyalty(Entity.BLACK);
        elements[1] = initPawnRow(Entity.BLACK);
        elements[6] = initPawnRow(Entity.WHITE);
        elements[7] = initRoyalty(Entity.WHITE);
    }

    private Entity[] initRoyalty(byte color) {
        Entity[] royalty = new Entity[] {
                new Rook(color),
                new Knight(color),
                new Bishop(color),
                new Queen(color),
                new King(color),
                new Bishop(color),
                new Knight(color),
                new Rook(color)
        };
        return royalty;
    }

    private Entity[] initPawnRow(byte color) {
        Entity[] pawns = new Entity[BOARD_WIDTH];
        for (int i = 0; i < 8; i++) {
            pawns[i] = new Pawn(color);
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
            }
        }
    }

    public void tick() {
        if (mouseClickHelper.isPressed()) {
            int x = mouseClickHelper.getX();
            int y = mouseClickHelper.getY();
            System.out.println("pressed");
        }
        if (!mouseClickHelper.isPressed()) {
            int x = mouseClickHelper.getX();
            int y = mouseClickHelper.getY();
            System.out.println("released");
        }
    }
}
