package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolPanel extends JPanel {
    private JLabel instructionLabel;
    private JButton saveButton;
    private String instructionString;

    private final Font LABEL_FONT = new Font("sarif", Font.BOLD, 24);
    private final int STR_LEN = 79;
    private final String[] INSTRUCTIONS = new String[]{"Player 1 Make Your Move", "Player 2 Make Your Move"};
    private int currInstructionIndex = 0;

    public ToolPanel() {
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
                //TODO: implement save on click
            }
        });

        setLayout(new FlowLayout());
        add(saveButton);
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
