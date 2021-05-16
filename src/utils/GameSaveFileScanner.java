package utils;

import entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class GameSaveFileScanner {

    private File inputFile;

    public GameSaveFileScanner(String path) {
        if (!path.endsWith(".txt")) {
            throw new IllegalArgumentException();
        }

        inputFile = new File(path);
        if (!inputFile.exists()) {
            try {
                inputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public GameBoardManager readInData() {
        try {
            Scanner input = new Scanner(inputFile);
            GameBoardManager gbm =  new GameBoardManager();
            Entity[][] entities = new Entity[GameBoardManager.BOARD_HEIGHT][GameBoardManager.BOARD_WIDTH];
            byte playersTurn = Byte.parseByte(input.nextLine());
            int row = 0;
            while (input.hasNextLine()) {
                String[] splitLine = input.nextLine().split(",");
                for (int col = 0; col < splitLine.length; col++) {
                    if (splitLine[col].equals("n")) {
                        entities[row][col] = null;
                    } else {
                        String[] splitItem = splitLine[col].split("=");
                        if (splitItem.length > 2) {
                            if (splitItem[0].equals("entities.Pawn")) {
                                byte color = Byte.parseByte(splitItem[1]);
                                entities[row][col] = createEntity(splitItem[0], color, gbm);
                                ((Pawn) entities[row][col]).setFirstMove(Boolean.parseBoolean(splitItem[2]));
                            }
                        } else {
                            byte color = Byte.parseByte(splitItem[1]);
                            entities[row][col] = createEntity(splitItem[0], color, gbm);
                        }
                    }
                }
                row++;
            }
            gbm.loadGameData(entities, playersTurn);
            return gbm;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
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

    public void writeGameData(GameBoardManager gbm) {
        try {
            PrintStream output = new PrintStream(inputFile);
            output.println(gbm.getPlayersTurn());
            for (int row = 0; row < gbm.BOARD_HEIGHT; row++) {
                for (int col = 0; col < gbm.BOARD_WIDTH; col++) {
                    Entity e = gbm.getEntityAt(row, col);
                    String eName;
                    if (e == null) {
                        eName = "n";
                    } else {
                        eName = gbm.getEntityAt(row, col).getClass().getName();
                        eName += "=" + e.getColor();
                        if (e.getClass().getName().equals("entities.Pawn")) {
                            eName += "=" + ((Pawn)e).isFirstMove();
                        }
                    }
                    output.print(eName);
                    if (col != gbm.BOARD_WIDTH - 1) {
                        output.print(",");
                    }
                }
                output.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteFile() {
        //TODO: debug file deletion
        return this.inputFile.delete();
    }

    public static void main(String[] args) {
        try {
            GameSaveFileScanner s = new GameSaveFileScanner("res/white_rook.png");
            System.out.println("Fail: improper extension test");
            return;
        } catch (IllegalArgumentException e) {
            //pass if throws illegal argument exception
        }

        //test new file is made
        GameSaveFileScanner s2 = new GameSaveFileScanner("res/game_save_data/newSave.txt");
        File newFile = new File("res/game_save_data/newSave.txt");
        if (!newFile.exists()) {
            System.out.println("Fail: make new file test");
            return;
        } else {
            s2.deleteFile();
        }

        GameBoardManager gbm = new GameBoardManager();
        GameSaveFileScanner scanner = new GameSaveFileScanner("res/game_save_data/newSave.txt");
        scanner.writeGameData(gbm);
        GameBoardManager gbm2 = scanner.readInData();

        //TODO: write more tests for reading and writing

        System.out.println("All tests passed");
    }
}
