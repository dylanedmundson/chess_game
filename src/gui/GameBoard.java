package gui;

import utils.GameBoardManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

//TODO: add save button
//TODO: load button (load last save for v1)
//TODO: add reset button
public class GameBoard extends JPanel implements ActionListener {
    public static final int ANIMATION_DELAY = 10;
    private final String TITLE = "Chess v.1.0";
    public static final int WIDTH = 1400;
    public static final int HEIGHT = 1000;
    public static final int BOARD_START = 100;
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;
    public static final int SQUARE_SIZE = 100;
    public static final Font LABEL_FONT = new Font("sarif", Font.BOLD, 24);

    //colors
    public static final Color BACKGROUND = new Color(0, 128, 0, 255);
    private final Color BOARD_LIGHT = new Color(207, 185, 125, 255);
    private final Color BOARD_DARK = new Color(101, 67, 33, 255);

    private BufferedImage image;
    private Graphics dbg;
    public GameBoardManager gameBoardManager;
    private Timer timer;
    public InputAdapater inputAdapter;
    private JFrame frame;
    public JPanel tools;
    private TakenPiecesGUI takenPiecesGUI;

    public GameBoard() {
        takenPiecesGUI = new TakenPiecesGUI(BOARD_WIDTH + BOARD_START + 50/2, BOARD_START,
                WIDTH - (BOARD_WIDTH + BOARD_START + 50/2) - 50/2, HEIGHT - 2 * BOARD_START);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        dbg = image.getGraphics();
        gameBoardManager = new GameBoardManager();
        gameBoardManager.setGameBoard(this);
        gameBoardManager.setTakenPiecesGUI(takenPiecesGUI);
        inputAdapter = new InputAdapater(gameBoardManager);
    }

    public void init() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle(TITLE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tools = new ToolPanel(this);
        add(tools, BorderLayout.NORTH);

        timer = new Timer(ANIMATION_DELAY, this);
        timer.start();
        addMouseListener(inputAdapter);
        frame.add(this);
    }

    @Override
    public void paintComponents(Graphics g) {
        //draw background color
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponents(g);

        //draw board
        int counter = 0;
        g.setFont(LABEL_FONT);
        for (int y = BOARD_START; y < BOARD_START + BOARD_HEIGHT; y += 100) {
            g.setColor(Color.WHITE);
            // g.getFontMetrics().getHeight(); get font height for centering
            int val = (8 - ((y - BOARD_START) / 100));
            g.drawString("" + (8 - ((y - BOARD_START) / 100)), BOARD_START - 20, y + 60);
            for (int x = BOARD_START; x < BOARD_START + BOARD_WIDTH; x += 100) {
                if (y == BOARD_START) {
                    g.setColor(Color.WHITE);
                    g.drawString("" + (char)('a' + ((x - BOARD_START) / 100)), x + 40, y - 10);
                }
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

        //draw takenPieces
        takenPiecesGUI.render(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintComponents(dbg);
        g.drawImage(image, 0, 0,null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameBoardManager.tick();
        repaint();
    }


    public static void main(String[] args) {
        new GameBoard().init();
    }
}
