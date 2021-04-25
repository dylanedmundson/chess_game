package utils;

import entities.Entity;
import entities.Pawn;
import gui.GameBoard;

import java.awt.*;

public class GameBoardManager {
    private Entity[][] elements;

    public GameBoardManager() {
        elements = new Entity[8][8];
        int counter = 0;
        for (int row = 0; row < elements.length; row++) {
            for (int col = 0; col < elements[row].length; col++) {
                if (counter % 2 == 0) {
                    elements[row][col] = new Pawn(Entity.BLACK);
                } else {
                    elements[row][col] = new Pawn(Entity.WHITE);
                }
                counter++;
            }
            counter++;
        }
    }

    public void render(Graphics g) {
        for (int row = 0; row < elements.length; row++) {
            for (int col = 0; col < elements[row].length; col++) {
                int x = col * GameBoard.SQUARE_SIZE + GameBoard.BOARD_START +
                        ((GameBoard.SQUARE_SIZE - elements[row][col].getImage().getWidth()) / 2);
                int y = row * GameBoard.SQUARE_SIZE + GameBoard.BOARD_START +
                        ((GameBoard.SQUARE_SIZE - elements[row][col].getImage().getHeight()) / 2);;
                g.drawImage(elements[row][col].getImage(), x, y, null);
            }
        }
    }
}
