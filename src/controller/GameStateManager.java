package controller;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import view.ImageManager;

public class GameStateManager {
    private JPanel[][] gameGrid;
    private JLabel holdImgLabel;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel linesLabel;
    private JLabel[] queuePicLabels;
    private logic game;

    public GameStateManager(JPanel[][] gameGrid, JLabel holdImgLabel, JLabel scoreLabel, JLabel levelLabel, JLabel linesLabel, JLabel[] queuePicLabels, logic game) {
        this.gameGrid = gameGrid;
        this.holdImgLabel = holdImgLabel;
        this.scoreLabel = scoreLabel;
        this.levelLabel = levelLabel;
        this.linesLabel = linesLabel;
        this.queuePicLabels = queuePicLabels;
        this.game = game;
    }

    public void resetAllComponents() {
        game.resetGame();
        updateHoldImage();
        updateQueue();
        updateScoreLabel();
        updateLinesLabel();
        updateLevelLabel();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                gameGrid[j][i].setBackground(Color.WHITE);
                gameGrid[j][i].setBorder(null);
                gameGrid[j][i].setOpaque(false);
            }
        }
    }

    private void updateHoldImage() {ImageManager.updateHoldImage(holdImgLabel, game);}
    private void updateScoreLabel() {ScoreManager.updateScoreLabel(scoreLabel, game);}
    private void updateLevelLabel() {ScoreManager.updateLevelLabel(levelLabel, game);}
    private void updateLinesLabel() {ScoreManager.updateLinesLabel(linesLabel, game);}
    public void updateQueue() {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < 3; i++) {
                int blockType = game.getBlockQueue()[i];
                ImageIcon icon = ImageManager.getBlockIcon(blockType);
                switch (blockType) {
                    case 0 -> queuePicLabels[i].setBounds(10, 22 + (i * 60), 100, 25);
                    case 1, 2, 4, 5, 6 -> queuePicLabels[i].setBounds(22, 10 + (i * 60), 75, 50);
                    case 3 -> queuePicLabels[i].setBounds(35, 10 + (i * 60), 50, 50);
                }
                queuePicLabels[i].setIcon(icon);
            }
        });
    }

}