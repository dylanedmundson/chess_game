package utils;

import entities.*;
import gui.TakenPiecesGUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
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
        //TODO: debug file deletion
        return this.inputFile.delete();
    }

    public static void main(String[] args) {
        // try {
        //     GameSaveFileScanner s = new GameSaveFileScanner("res/white_rook.png");
        //     System.out.println("Fail: improper extension test");
        //     return;
        // } catch (IllegalArgumentException e) {
        //     //pass if throws illegal argument exception
        // }
        //
        // //test new file is made
        // GameSaveFileScanner s2 = new GameSaveFileScanner("res/game_save_data/newSave.txt");
        // File newFile = new File("res/game_save_data/newSave.txt");
        // if (!newFile.exists()) {
        //     System.out.println("Fail: make new file test");
        //     return;
        // } else {
        //     s2.deleteFile();
        // }
        //
        // GameBoardManager gbm = new GameBoardManager();
        // GameSaveFileScanner scanner = new GameSaveFileScanner("res/game_save_data/newSave.txt");
        // scanner.writeGameData(gbm);
        // GameBoardManager gbm2 = scanner.readInData();
        //
        // //TODO: write more tests for reading and writing
        //
        // System.out.println("All tests passed");
    }
}
