package gui;

import entities.*;
import utils.ArraySorter;
import utils.GameBoardManager;

import java.awt.*;
import java.util.Scanner;

public class TakenPiecesGUI {
    private final int MAX_PIECES = 16;
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
        player1Takes = new Entity[MAX_PIECES];
        player2Takes = new Entity[MAX_PIECES];
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

    public String serialize() {
        //print p1 taken pieces
        String results = "";
        results += player1TakeIndex + "\n";
        if (player1TakeIndex == 0) {
            results += "\n";
        } else {
            for (int i = 0; i < player1TakeIndex; i++) {
                Entity e = player1Takes[i];
                results += e.getClass().getName() + "=" + e.getColor();
                if (i != player1TakeIndex - 1) {
                    results += ",";
                }
            }
            results += "\n";
        }

        //print p2 taken pieces
        results += player2TakeIndex + "\n";
        if (player2TakeIndex == 0) {
            results += "\n";
        } else {
            for (int i = 0; i < player2TakeIndex; i++) {
                Entity e = player2Takes[i];
                results += e.getClass().getName() + "=" + e.getColor();
                if (i != player2TakeIndex - 1) {
                    results += ",";
                }
            }
            results += "\n";
        }

        return results + "__EOF\n";
    }

    public void deserialize(Scanner input) {
        player1Takes = new Entity[MAX_PIECES];
        player1TakeIndex = Integer.parseInt(input.nextLine());
        String[] splitLine = input.nextLine().split(",");
        for (int i = 0; i < splitLine.length; i++) {
            String[] splitItem = splitLine[i].split("=");
            if (splitItem.length >= 2) {
                byte color = Byte.parseByte(splitItem[1]);
                player1Takes[i] = createEntity(splitItem[0], color, null);
            }
        }

        player2Takes = new Entity[MAX_PIECES];
        player2TakeIndex = Integer.parseInt(input.nextLine());
        splitLine = input.nextLine().split(",");
        for (int i = 0; i < splitLine.length; i++) {
            String[] splitItem = splitLine[i].split("=");
            if (splitItem.length >= 2) {
                byte color = Byte.parseByte(splitItem[1]);
                player2Takes[i] = createEntity(splitItem[0], color, null);
            }
        }
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
