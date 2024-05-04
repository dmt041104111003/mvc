package controller;

import java.awt.event.KeyEvent;
import view.frame;
public class KeyboardEventHandler {
    private final logic game;
    private final frame mainFrame;
    public KeyboardEventHandler(logic game, frame mainFrame) {
        this.game = game;
        this.mainFrame = mainFrame;
    }
    public void handleKeyEvent(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:game.moveSide(0);mainFrame.updateCurrentBlock(false);break;
            case KeyEvent.VK_RIGHT:game.moveSide(1);mainFrame.updateCurrentBlock(false);break;
            case KeyEvent.VK_DOWN:game.setFastFall(true);break;
            case KeyEvent.VK_UP:game.rotateBlock();mainFrame.updateCurrentBlock(false);break;
            case KeyEvent.VK_H:mainFrame.holdBlock();break;
            case KeyEvent.VK_P:mainFrame.pauseGame();break;
            case KeyEvent.VK_SPACE:mainFrame.moveBlockDownFast();break;
            default:break;
        }
    }
    
}