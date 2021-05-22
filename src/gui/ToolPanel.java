package gui;

import utils.GameSaveFileScanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.GameBoard.LABEL_FONT;

public class ToolPanel extends JPanel {
    private final GameSaveFileScanner gsfs = new GameSaveFileScanner("res/game_save_data/newSave.txt");

    private JLabel instructionLabel;
    private JButton saveButton;
    private String instructionString;
    private JButton loadButton;

    private final int STR_LEN = 79;
    private final String[] INSTRUCTIONS = new String[]{"Player 1 Make Your Move", "Player 2 Make Your Move"};
    private int currInstructionIndex = 0;

    public ToolPanel(GameBoard gb) {
        instructionString = INSTRUCTIONS[currInstructionIndex];
        centerInstructionLabel();
        instructionLabel = new JLabel(instructionString);
        instructionLabel.setFont(LABEL_FONT);
        instructionLabel.setForeground(Color.WHITE);
        saveButton = new JButton("Save");
        saveButton.setFont(LABEL_FONT);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gsfs.writeGameData(gb.gameBoardManager);
            }
        });

        loadButton = new JButton("Load");
        loadButton.setFont(LABEL_FONT);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gsfs.readInData(gb.gameBoardManager);
            }
        });

        setLayout(new FlowLayout());
        add(saveButton);
        add(loadButton);
        add(instructionLabel);
        setBackground(GameBoard.BACKGROUND);
    }

    public void centerInstructionLabel() {
        int spacesNeeded = 0;
        spacesNeeded = STR_LEN - instructionString.length();
        for (int i = 0; i < spacesNeeded; i++) {
            instructionString += " ";
        }
    }

    public void nextInstruction() {
        currInstructionIndex++;
        if (currInstructionIndex >= INSTRUCTIONS.length) {
            currInstructionIndex = 0;
        }
        instructionString = INSTRUCTIONS[currInstructionIndex];
        centerInstructionLabel();
        instructionLabel.setText(instructionString);
    }

}
