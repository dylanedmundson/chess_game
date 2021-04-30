package gui;

import utils.GameBoardManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GameBoard extends JFrame implements ActionListener {
    private static final String TITLE = "Chess v.1.0";
    public static final int WIDTH = 900;
    public static final int HEIGHT = 900;
    public static final int BOARD_START = 50;
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;
    public static final int SQUARE_SIZE = 100;

    //colors
    private static final Color BACKGROUND = new Color(0, 128, 0, 255);
    private static final Color BOARD_LIGHT = new Color(207, 185, 125, 255);
    private static final Color BOARD_DARK = new Color(101, 67, 33, 255);

    private BufferedImage image;
    private Graphics dbg;
    private GameBoardManager gameBoardManager;

    public GameBoard() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        dbg = image.getGraphics();
        gameBoardManager = new GameBoardManager();
    }

    public void init() {
        setTitle(TITLE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        //draw background color
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        //draw board
        int counter = 0;
        for (int y = BOARD_START; y < BOARD_START + BOARD_HEIGHT; y += 100) {
            for (int x = BOARD_START; x < BOARD_START + BOARD_WIDTH; x += 100) {
                if (counter % 2 == 0) {
                    g.setColor(BOARD_LIGHT);
                } else {
                    g.setColor(BOARD_DARK);
                }
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
                counter++;
            }
            counter++;
        }

        //draw pieces
        gameBoardManager.render(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintComponents(dbg);
        g.drawImage(image, 0, 0,null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new GameBoard().init();
    }
}
