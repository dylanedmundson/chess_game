package gui;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class InputAdapater implements MouseInputListener {
    public MouseClickHelper mouseClickHelper;

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

    public InputAdapater() {
        mouseClickHelper = new MouseClickHelper();
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        mouseClickHelper.x = x;
        mouseClickHelper.y = y;
        mouseClickHelper.isPressed = false;
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
}
