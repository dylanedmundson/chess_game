package utils;

import entities.*;
import gui.TakenPiecesGUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Scanner;

public class GameSaveFileScanner {

    private File inputFile;

    /**
     *
     * @param path where we want to save the file
     */
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

    /**
     * reads data into a Game board manager
     */
    public void readInData(GameBoardManager gbm, TakenPiecesGUI tpgui) {
        try {
            Scanner input = new Scanner(inputFile);
            gbm.deserialize(input);
            tpgui.deserialize(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes data to save file
     * @param gbm GameBoardManage whos state is being saved
     */
    public void writeGameData(GameBoardManager gbm, TakenPiecesGUI tpgui) {
        try {
            PrintStream output = new PrintStream(inputFile);
            output.print(gbm.serialize());
            output.print(tpgui.serialize());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteFile() {
        return this.inputFile.delete();
    }


    /**
     * Test Harness
     */
    private static final int PASS = 0;
    private static final int FAIL = 1;
    private static final Random RAND = new Random();
    private static final int ITERATIONS = 10000;

    public static void main(String[] args) {
        System.out.println("Initiating Tests:");
        System.out.print("Testing ");
        if (testConstructor() == FAIL) {
            return;
        }
        for (int i = 0; i < ITERATIONS; i++) {
            if (testReadAndWrite() == FAIL) {
                return;
            } else {
                if (i % (ITERATIONS / 10) == (ITERATIONS / (ITERATIONS / 10))) {
                    System.out.print(".");
                }
            }
        }
        System.out.println("\nAll tests passed");
    }

    public static int testConstructor() {
        try {
            GameSaveFileScanner s = new GameSaveFileScanner("res/white_rook.png");
            System.out.println("Fail: improper extension test");
            return FAIL;
        } catch (IllegalArgumentException e) {
            //pass if throws illegal argument exception
        }

        //test new file is made
        GameSaveFileScanner s2 = new GameSaveFileScanner("res/game_save_data/testSave.txt");
        File newFile = new File("res/game_save_data/testSave.txt");
        if (!newFile.exists()) {
            System.out.println("Fail: make new file test");
            return FAIL;
        } else {
            s2.deleteFile();
            if (newFile.exists()) {
                System.out.println("Fail: failed to delete file '" + newFile.getName() + "'");
                return FAIL;
            }
        }
        if (s2.inputFile == null) {
            System.out.println("Fail: inputFile field == null");
            return FAIL;
        }
        if (!s2.inputFile.getName().equals("testSave.txt")) {
            System.out.println("Fail: inputFile file name == '" + s2.inputFile.getName() + "'\n\texpeted: 'testSave.txt'");
            return FAIL;
        }
        return PASS;
    }
    //TODO: write more specific test for test case tpgui no entries for player1Takes but entries
    // for player2Takes broke int deserialize array out of bounds expception

    public static int testReadAndWrite() {
        //alterations to gbm
        try {
            GameBoardManager gbm = new GameBoardManager();
            TakenPiecesGUI takenPiecesGUI =  new TakenPiecesGUI(0, 0, 100, 100);
            gbm.setTakenPiecesGUI(takenPiecesGUI);
            //Alter players turn
            Field playerTurnField = gbm.getClass().getDeclaredField("playersTurn");
            playerTurnField.setAccessible(true);
            playerTurnField.setByte(gbm, (byte) RAND.nextInt(2));

            //Alter elements
            Field entityField = gbm.getClass().getDeclaredField("elements");
            entityField.setAccessible(true);
            Entity[][] elements = (Entity[][]) entityField.get(gbm);
            for (int i = 0; i < 20; i++) {
                int col = RAND.nextInt(8);
                int row = RAND.nextInt(8);
                int col2 = RAND.nextInt(8);
                int row2 = RAND.nextInt(8);

                Entity e = elements[col][row];
                elements[row][col] = elements[col2][row2];
                elements[row2][col2] = e;
            }

            //Alter TakenPiecesGui
            for (int i = 0; i < 10; i++) {
                int player = RAND.nextInt(2);
                int col = RAND.nextInt(8);
                int row = RAND.nextInt(8);
                int count = 0;
                while (elements[row][col] == null && count < 10) {
                    col = RAND.nextInt(8);
                    row = RAND.nextInt(8);
                    count++;
                }
                if (elements[row][col] != null) {
                    if (player == 0) {
                        takenPiecesGUI.addPieceP1(elements[row][col]);
                    } else {
                        takenPiecesGUI.addPieceP2(elements[row][col]);
                    }
                    elements[row][col] = null;
                }
            }
            entityField.set(gbm, elements);

            //save changes
            GameSaveFileScanner gameSaveFileScanner = new GameSaveFileScanner("res/game_save_data/test.txt");
            gameSaveFileScanner.writeGameData(gbm, takenPiecesGUI);

            GameBoardManager gbm2 = new GameBoardManager();
            TakenPiecesGUI takenPiecesGUI2 = new TakenPiecesGUI(0, 0, 100, 100);
            gameSaveFileScanner.readInData(gbm2, takenPiecesGUI2);
            gbm2.setTakenPiecesGUI(takenPiecesGUI2);

            // test gameboard manager
            if (testGameBoardManagerFields(gbm, gbm2) == FAIL) {
                return FAIL;
            }

            gameSaveFileScanner.deleteFile();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return PASS;
    }

    private static int testGameBoardManagerFields(GameBoardManager gbm1, GameBoardManager gbm2) {
        // check player turn is correct
        try {
            Field playersTurn = gbm1.getClass().getDeclaredField("playersTurn");
            playersTurn.setAccessible(true);
            byte playerTurn1 = playersTurn.getByte(gbm1);
            byte playerTurn2 = playersTurn.getByte(gbm2);
            if (playerTurn1 != playerTurn2) {
                System.out.println("Fail: at player turn");
                return FAIL;
            }

            //test taken pieces gui fields initiated correctly
            Field takenPiecesGui = gbm1.getClass().getDeclaredField("takenPiecesGUI");
            takenPiecesGui.setAccessible(true);
            TakenPiecesGUI tpg1 = (TakenPiecesGUI) takenPiecesGui.get(gbm1);
            TakenPiecesGUI tpg2 = (TakenPiecesGUI) takenPiecesGui.get(gbm2);
            if (tpg1 != null && tpg2 != null) {
                if (testTakenPiecesGUIFields(tpg1, tpg2) == FAIL) {
                    return FAIL;
                }
            } else {
                if ((tpg1 == null && tpg2 != null) || (tpg1 != null && tpg2 == null)) {
                    System.out.println("Fail: at game board manager tpgComparison");
                    return FAIL;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //check all entity elements
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Entity e1 = gbm1.getEntityAt(i, j);
                Entity e2 = gbm2.getEntityAt(i, j);
                if (e1 != null && e2 != null) {
                    if (!e1.equals(e2)) {
                        System.out.println("Fail: at game board manager Entity comparison");
                        return FAIL;
                    }
                    if (e1.getClass().getName().equals("entities.Pawn")) {
                        if (((Pawn) e1).isFirstMove() != ((Pawn)e2).isFirstMove()) {
                            System.out.println("Fail: at game board manager Entity comparison");
                            return FAIL;
                        }
                    }
                } else {
                    if ((e1 == null && e2 != null) || (e1 != null && e2 == null)) {
                        System.out.println("Fail: at game board manager Entity comparison");
                        return FAIL;
                    }
                }
            }
        }
        return PASS;
    }

    public static int testTakenPiecesGUIFields(TakenPiecesGUI takenPiecesGUI, TakenPiecesGUI takenPiecesGUI2) {
        //test for player take index
        try {
            Field player1TakeIndexField = takenPiecesGUI2.getClass().getDeclaredField("player1TakeIndex");
            player1TakeIndexField.setAccessible(true);

            Field player2TakeIndexField = takenPiecesGUI2.getClass().getDeclaredField("player2TakeIndex");
            player2TakeIndexField.setAccessible(true);
            try {
                int player1TakeIndex1 = player1TakeIndexField.getInt(takenPiecesGUI);
                int player2TakeIndex1 = player2TakeIndexField.getInt(takenPiecesGUI);
                int player1TakeIndex2 = player1TakeIndexField.getInt(takenPiecesGUI2);
                int player2TakeIndex2 = player2TakeIndexField.getInt(takenPiecesGUI2);
                if (player1TakeIndex1 != player1TakeIndex2 || player2TakeIndex1 != player2TakeIndex2) {
                    System.out.println("Fail: at player 1 and 2 take index");
                    return FAIL;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // test for palyer take fields
        try {
            Field player1TakesField = takenPiecesGUI2.getClass().getDeclaredField("player1Takes");
            player1TakesField.setAccessible(true);
            Field player2TakesField = takenPiecesGUI2.getClass().getDeclaredField("player2Takes");
            player2TakesField.setAccessible(true);
            try {
                Entity[] player1Takes1 = (Entity[]) player1TakesField.get(takenPiecesGUI);
                Entity[] player1Takes2 = (Entity[]) player1TakesField.get(takenPiecesGUI2);
                for (int i = 0; i < player1Takes1.length; i++) {
                    if (player1Takes1[i] != null && player1Takes2[i] != null) {
                        if (!player1Takes1[i].equals(player1Takes2[i])) {
                            System.out.println("Fail: TakenPiecesGui error in reading data");
                            return FAIL;
                        }
                    } else {
                        if ((player1Takes1[i] == null && player1Takes2[i] != null) ||
                                (player1Takes1[i] != null && player1Takes2[i] == null)) {
                            System.out.println("Fail: TakenPiecesGui error in reading data");
                            return FAIL;
                        }
                    }
                }
                Entity[] player2Takes1 = (Entity[]) player2TakesField.get(takenPiecesGUI);
                Entity[] player2Takes2 = (Entity[]) player2TakesField.get(takenPiecesGUI2);
                for (int i = 0; i < player2Takes1.length; i++) {
                    if (player2Takes1[i] != null && player2Takes2[i] != null) {
                        if (!player2Takes1[i].equals(player2Takes2[i])) {
                            System.out.println("Fail: TakenPiecesGui error in reading data");
                            return FAIL;
                        }
                    } else {
                        if ((player2Takes1[i] == null && player2Takes2[i] != null) ||
                                (player2Takes1[i] != null && player2Takes2[i] == null)) {
                            System.out.println("Fail: TakenPiecesGui error in reading data");
                            return FAIL;
                        }
                    }
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return PASS;
    }
}
