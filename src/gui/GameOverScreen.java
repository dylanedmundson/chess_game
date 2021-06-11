package gui;

import utils.GameBoardManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GameOverScreen implements KeyListener {
    private BufferedImage image;
    private int[] pixels;
    private int width;
    private int height;
    private boolean isGameOver = false;
    private byte losingPlayer;
    private final float ALPHA_MIX = 0.8f;
    private int blinkCount;
    private boolean isShowing = true;
    private int choiceIndex = 0;

    public GameOverScreen(BufferedImage image) {
        this.image = image;
    }

    public void render() {
        Font font = new Font(GameBoard.LABEL_FONT.getFontName(), GameBoard.LABEL_FONT.getStyle(), 50);
        BufferedImage overlay = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = overlay.getGraphics();
        if (losingPlayer == GameBoardManager.PLAYER1) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString("Game Over!", 450, 400);
            g.drawString("Player 2 Wins", 450, 450);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString("Game Over!", 450, 400);
            g.drawString("Player 1 Wins", 450, 450);
        }
        if (isShowing && choiceIndex == 0) {
            g.setColor(Color.WHITE);
            g.drawString(">", 400, 500);
            g.drawString("reset", 450, 500);
            g.drawString("exit", 450, 550);
        } else if (isShowing && choiceIndex == 1) {
            g.setColor(Color.WHITE);
            g.drawString(">", 400, 550);
            g.drawString("reset", 450, 500);
            g.drawString("exit", 450, 550);
        } else {
            g.setColor(Color.WHITE);
            g.drawString("reset", 450, 500);
            g.drawString("exit", 450, 550);
        }
        int[] overlayPix = ((DataBufferInt)overlay.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < overlayPix.length; i++) {
            Color foreground = new Color(overlayPix[i], false);
            Color background = new Color(this.pixels[i], false);
            int red = (int)((foreground.getRed() * ALPHA_MIX) + (background.getRed() * (1 - ALPHA_MIX)));
            int green = (int)((foreground.getGreen() * ALPHA_MIX) + (background.getGreen() * (1 - ALPHA_MIX)));
            int blue = (int)((foreground.getBlue() * ALPHA_MIX) + (background.getBlue() * (1 - ALPHA_MIX)));
            pixels[i] = (new Color(red, blue, green)).getRGB();
        }
    }

    public void setGameOver(byte playersTurn) {
        this.pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        this.width = image.getWidth();
        this.height = image.getHeight();
        isGameOver = true;
        this.losingPlayer = playersTurn;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void update() {
        blinkCount++;
        if (blinkCount % 20 == 0) isShowing = !isShowing;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.choiceIndex++;
            if (choiceIndex > 1) {
                choiceIndex = 0;
            }
            System.out.println("typed");
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.choiceIndex--;
            if (choiceIndex < 0) {
                choiceIndex = 1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
