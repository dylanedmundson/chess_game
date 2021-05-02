package gui;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import utils.GameBoardManager;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class InputAdapater implements MouseInputListener {
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
}
