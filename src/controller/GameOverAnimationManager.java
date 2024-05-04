package controller;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

public class GameOverAnimationManager {
    private JPanel[][] gameGrid;
    public GameOverAnimationManager(JPanel[][] gameGrid) {this.gameGrid = gameGrid;}
    public void gameOverAnimation() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameGrid[j][i].getBackground() != Color.WHITE) {
                    int green = ThreadLocalRandom.current().nextInt(150, 251);
                    int blue = ThreadLocalRandom.current().nextInt(85, 186);
                    gameGrid[j][i].setBackground(new Color(250, green, blue));
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ignored) {}
                    gameGrid[j][i].setBackground(Color.WHITE);
                    gameGrid[j][i].setBorder(null);
                    gameGrid[j][i].setOpaque(false);
                }
            }
        }
    }
}