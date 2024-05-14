package controller;

import view.frame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class gravity implements Runnable {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final frame gameFrame;
    private volatile boolean isRunning;

    public gravity(frame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public void startGame() {
        isRunning = true;
        executorService.execute(this);
    }

    public void stopGame() {
        isRunning = false;
    }

    @Override
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
                        Thread.sleep(15);
                    if (gameFrame.getGame().isFastFall()) Thread.sleep(15);
                } catch (InterruptedException ignored) {}
            } else {
                gameFrame.getGame().addCurrentToSetBlock();
                gameFrame.removeRows(gameFrame.getGame().checkForFullRows());
                
                gameFrame.updateScoreLabel();
                gameFrame.updateLevelLabel();
                gameFrame.updateLinesLabel();
                gameFrame.initFrameBackground();

                if (gameFrame.getGame().getLevel() == gameFrame.MAX() && !gameFrame.isSelectMode()) {
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