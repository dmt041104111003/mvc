package view;


import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import controller.logic;
public class ScoreManager {

    public static void updateScoreLabel(JLabel scoreLabel, logic game) {
        scoreLabel.setText("Score: " + game.getScore());
    }

    public static void updateLevelLabel(JLabel levelLabel, logic game) {
        levelLabel.setText("Level: " + game.getLevel());
    }

    public static void updateLinesLabel(JLabel linesLabel, logic game) {
        linesLabel.setText("Lines: " + game.getTotalLines());
    }

    public static void initScorePanel(frame frameInstance) {
        JPanel scorePanel = new JPanel();
        scorePanel.setBounds(272, 10, 120, 35);
        scorePanel.setOpaque(false);
        scorePanel.setBorder(new LineBorder(new Color(173, 216, 230), 2));
        frameInstance.scoreLabel = new JLabel("Score: 0");
        frameInstance.scoreLabel.setFont(frameInstance.getPixelFont().deriveFont(14f));
        frameInstance.scoreLabel.setForeground(new Color(173, 216, 230));
        scorePanel.add(frameInstance.scoreLabel);
        frameInstance.gamePanel.add(scorePanel);
    }
}