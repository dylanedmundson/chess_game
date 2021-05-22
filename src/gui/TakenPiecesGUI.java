package gui;

import entities.Entity;
import utils.ArraySorter;

import java.awt.*;

//TODO: add to save and load functionallity
public class TakenPiecesGUI {
    private Entity[] player1Takes;
    private int player1TakeIndex = 0;
    private Entity[] player2Takes;
    private int player2TakeIndex = 0;

    private int width;
    private int height;
    private int x; //coords of lop L
    private int y;
    private int imageScale;

    public TakenPiecesGUI(int x, int y, int width, int height) {
        player1Takes = new Entity[16];
        player2Takes = new Entity[16];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        imageScale = width / 8;
    }

    public void addPieceP1(Entity e) {
        player1Takes[player1TakeIndex] = e;
        player1TakeIndex++;
        ArraySorter.qSort(player1Takes, player1TakeIndex);
    }

    public void addPieceP2(Entity e) {
        player2Takes[player2TakeIndex] = e;
        player2TakeIndex++;
        ArraySorter.qSort(player2Takes, player2TakeIndex);
    }

    public void render(Graphics g) {
        g.setColor(new Color(75, 104, 23));
        g.fillRect(x, y, width, height);
        //render top rows
        int i = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 8; col++) {
                if (player2Takes[i] != null) {
                    g.drawImage(player2Takes[i].getImage(), col * imageScale + x, row * imageScale + y,
                            imageScale, imageScale, null);
                }
                i++;
            }
        }

        i = 0;
        //render bot rows
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 8; col++) {
                if (player1Takes[i] != null) {
                    g.drawImage(player1Takes[i].getImage(), col * imageScale + x,
                            row * imageScale + y + height - 2 * imageScale,
                            imageScale, imageScale, null);
                }
                i++;
            }
        }
    }
}
