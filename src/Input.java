import java.awt.*;
import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    //keyboard array
    private boolean[] keys = new boolean[256];
    private boolean[] keysLast = new boolean[256];
    //mouse array
    private boolean[] mButtons = new boolean[5];
    private boolean[] mButtonsLast = new boolean[5];

    private int mouseX, mouseY;
    private int scroll;

    private double scale;

    public Input(Canvas canvas, double scale) {
        this.scale = scale;
        mouseX = 0;
        mouseY = 0;
        scroll = 0;
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
    }
    public void update() {
        scroll = 0;
        for (int i = 0; i < 256; i++) {
            keysLast[i] = keys[i];
        }
        for (int i = 0; i < 5; i++) {
            mButtonsLast[i] = mButtons[i];
        }
    }
    public boolean isKey(int keyCode) {return keys[keyCode];}
    public boolean isKeyUp(int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }
    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }
    public boolean isMouseButton(int mButton) {
        return mButtons[mButton];
    }
    public boolean isMouseButtonUp(int mButton) {
        return !mButtons[mButton] && mButtonsLast[mButton];
    }
    public boolean isMouseButtonDown(int mButton) {
        return mButtons[mButton] && !mButtonsLast[mButton];
    }
    //TODO: Missing some checkers

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //TODO
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //TODO
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mButtons[mouseEvent.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mButtons[mouseEvent.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        //TODO
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        //TODO
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX = (int)(mouseEvent.getX() / scale);
        mouseY = (int)(mouseEvent.getY() / scale);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = (int)(mouseEvent.getX() / scale);
        mouseY = (int)(mouseEvent.getY() / scale);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        scroll = mouseWheelEvent.getWheelRotation();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}
