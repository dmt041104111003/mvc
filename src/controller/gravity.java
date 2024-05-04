package controller;

import view.frame;

public class gravity extends Thread {
    private final frame gameFrame;
    private boolean isRunning;
    private static final int MAX_LEVEL = 0;

    public gravity(frame gameFrame) {
        this.gameFrame = gameFrame;
        this.isRunning = false;
    }
    public void startGame() {
        isRunning = true;
        this.start();
    }
    public void stopGame() {isRunning = false;}
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}

            if (!gameFrame.getGame().isTouchingBottomOrBlock()) {
                gameFrame.getGame().updatePreviousBlockPos();
                gameFrame.getGame().moveBlockDown();
                gameFrame.updateCurrentBlock(false);
                try {
                    for (int i = 0; i < gameFrame.getGame().getFallDelay() / 50 && !gameFrame.getGame().isFastFall(); i++) 
                        Thread.sleep(50);
                    if (gameFrame.getGame().isFastFall()) Thread.sleep(50);
                } catch (InterruptedException ignored) {}
            } else {
                gameFrame.getGame().addCurrentToSetBlock();
                gameFrame.removeRows(gameFrame.getGame().checkForFullRows());
                gameFrame.updateScoreLabel();
                gameFrame.updateLevelLabel();
                gameFrame.updateLinesLabel();
                gameFrame.initFrameBackground();

                if (gameFrame.getGame().getLevel() == MAX_LEVEL && !gameFrame.isSelectMode()) {
                    gameFrame.showWinGamePanel();
                    break;
                } else if (!gameFrame.getGame().checkIfGameOver()) {
                    gameFrame.getGame().nextBlock();
                    gameFrame.getGame().setHeldThisTurn(false);
                    gameFrame.updateQueue();
                    gameFrame.updateCurrentBlock(true);
                } else {
                    if (gameFrame.isSelectMode()) {
                        gameFrame.showSelectOverPanel();
                    } else {
                        gameFrame.gameOverAnimation();
                        gameFrame.showGameOverMessage();
                        gameFrame.getGame().checkIfHighScore();
                    }
                    break;
                }
            }
        }
    }
}