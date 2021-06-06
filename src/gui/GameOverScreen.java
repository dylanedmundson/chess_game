package gui;

import javafx.scene.layout.BorderWidths;
import utils.GameBoardManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GameOverScreen {
    private BufferedImage image;
    private int[] pixels;
    private int width;
    private int height;
    private boolean isGameOver = false;
    private byte losingPlayer;
    private final float ALPHA_MIX = 0.8f;

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
}
