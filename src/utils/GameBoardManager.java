package utils;

import entities.Entity;
import entities.Pawn;
import entities.King;
import gui.GameBoard;

import java.awt.*;

public class GameBoardManager {
    private Entity[][] elements;

    public GameBoardManager() {
        elements = new Entity[8][8];
        //int counter = 0;
        for (int row = 0; row < elements.length; row++) {
            for (int col = 0; col < elements[row].length; col++) {
                if (row == 0) {
                    elements[row][6] = new King(Entity.BLACK);
                } else if (row == 7){
                    elements[row][6] = new King(Entity.WHITE);
                }
                if (row == 1) {
                    elements[1][col] = new Pawn(Entity.BLACK);
                } else if (row == 6){
                    elements[6][col] = new Pawn(Entity.WHITE);
                }
                //counter++;
            }
            //counter++;
            //TODO: EDITS HERE
        }
    }

    public void render(Graphics g) {
        for (int row = 0; row < elements.length; row++) {
            for (int col = 0; col < elements[row].length; col++) {
                int x = col * GameBoard.SQUARE_SIZE + GameBoard.BOARD_START +
                        ((GameBoard.SQUARE_SIZE - elements[1][col].getImage().getWidth()) / 2);
                int y = row * GameBoard.SQUARE_SIZE + GameBoard.BOARD_START +
                        ((GameBoard.SQUARE_SIZE - elements[6][col].getImage().getHeight()) / 2);
                if (row == 1) {
                    g.drawImage(elements[1][col].getImage(), x, y, null);
                } else if (row == 6){
                    g.drawImage(elements[6][col].getImage(), x, y, null);
                }
            }
        }
    }
}
