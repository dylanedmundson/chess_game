package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    public static Key up = new Key();
    public static Key down = new Key();

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
            up.toggle(isPressed);
        } else if (event == KeyEvent.VK_DOWN || event == KeyEvent.VK_S) {
            down.toggle(isPressed);
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
