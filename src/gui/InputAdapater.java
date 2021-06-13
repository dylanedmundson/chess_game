package gui;

import utils.GameBoardManager;

import javax.swing.event.MouseInputListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class InputAdapater implements MouseInputListener, KeyListener {
    public MouseClickHelper mouseClickHelper;
    private GameBoardManager gm;

    public class MouseClickHelper {
        private int x;
        private int y;
        private boolean isPressed = false;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean isPressed() {
            return isPressed;
        }
    }

    public InputAdapater(GameBoardManager gm) {
        mouseClickHelper = new MouseClickHelper();
        this.gm = gm;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        mouseClickHelper.x = x;
        mouseClickHelper.y = y;
        mouseClickHelper.isPressed = true;
        gm.press(x, y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        mouseClickHelper.x = x;
        mouseClickHelper.y = y;
        mouseClickHelper.isPressed = false;
        gm.release();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public static Key UP_KEY = new Key();
    public static Key DOWN_KEY = new Key();
    public static Key CONFIRM_KEY = new Key();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e.getKeyCode(), false);
    }


    public void toggle(int event, boolean isPressed) {
        if (event == KeyEvent.VK_UP || event == KeyEvent.VK_W) {
            UP_KEY.toggle(isPressed);
        } else if (event == KeyEvent.VK_DOWN || event == KeyEvent.VK_S) {
            DOWN_KEY.toggle(isPressed);
        } else if (event == KeyEvent.VK_ENTER || event == KeyEvent.VK_SPACE) {
            CONFIRM_KEY.toggle(isPressed);
        }
    }

    public static class Key {
        private boolean isPressed = false;
        public void toggle(boolean isPressed) {
            this.isPressed = isPressed;
        }

        public boolean isPressed() {
            return isPressed;
        }
    }
}
